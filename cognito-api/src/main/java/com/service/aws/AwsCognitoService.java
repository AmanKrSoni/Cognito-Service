package com.service.aws;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.pojo.request.UserSignInRequest;
import com.pojo.request.UserSignUpRequest;
import com.pojo.response.UserSignInResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AwsCognitoService {

    @Value(value = "${aws.cognito.userPoolId}")
    private String userPoolId;
    @Value(value = "${aws.cognito.clientId}")
    private String clientId;

    private static final String EMAIL = "email";
    private static final String EMAIL_VERIFIED = "email_verified";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private static final String NEW_PASSWORD = "NEW_PASSWORD";
    private static final String NEW_PASSWORD_REQUIRED = "NEW_PASSWORD_REQUIRED";

    @Autowired
    private AWSCognitoIdentityProvider cognitoClient;


    public void signUp(final UserSignUpRequest request){
        try {
            AttributeType email = new AttributeType().withName(EMAIL).withValue(request.getEmail());
            AttributeType emailVerified = new AttributeType().withName(EMAIL_VERIFIED).withValue(Boolean.TRUE.toString());

            AdminCreateUserRequest userRequest = new AdminCreateUserRequest()
                    .withUserPoolId(userPoolId).withUsername(request.getUsername())
                    .withTemporaryPassword(request.getPassword())
                    .withUserAttributes(email, emailVerified)
                    .withMessageAction(MessageActionType.SUPPRESS)
                    .withDesiredDeliveryMediums(DeliveryMediumType.EMAIL);

            AdminCreateUserResult userResult = cognitoClient.adminCreateUser(userRequest);

            log.info("userResult : {}",userResult);
            log.info("User " + userResult.getUser().getUsername()
                    + " is created. Status: " + userResult.getUser().getUserStatus());

            changePassword(request.getUsername(), request.getPassword());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public UserSignInResponse signIn(final UserSignInRequest request){
        Map<String,String> reqMap = new HashMap<>();
        reqMap.put(USERNAME, request.getUsername());
        reqMap.put(PASSWORD, request.getPassword());

        UserSignInResponse userDetails = UserSignInResponse.builder().build();

        AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest();
        authRequest.withAuthFlow(AuthFlowType.ADMIN_NO_SRP_AUTH)
                .withClientId(clientId).withUserPoolId(userPoolId).withAuthParameters(reqMap);
        try {
            AdminInitiateAuthResult authResult = cognitoClient.adminInitiateAuth(authRequest);
            AuthenticationResultType authenticationResultType = null;
            if(StringUtils.hasText(authResult.getChallengeName())){
                log.info("challenge name : {}",authResult.getChallengeName());

                if(authResult.getChallengeName().contentEquals(NEW_PASSWORD_REQUIRED)){
                    if(!StringUtils.hasLength(request.getPassword())){
                        log.info("User must change password : {}", authResult.getChallengeName());
                    }else {
                        Map<String, String> challengeResponse = new HashMap<>();
                        challengeResponse.put(USERNAME, request.getUsername());
                        challengeResponse.put(PASSWORD, request.getPassword());
                        challengeResponse.put(NEW_PASSWORD, request.getNewPassword());

                        AdminRespondToAuthChallengeRequest authChallengeRequest = new AdminRespondToAuthChallengeRequest()
                                .withChallengeName(ChallengeNameType.NEW_PASSWORD_REQUIRED)
                                .withClientId(clientId).withUserPoolId(userPoolId)
                                .withChallengeResponses(challengeResponse).withSession(authResult.getSession());

                        AdminRespondToAuthChallengeResult authChallengeResult = cognitoClient.adminRespondToAuthChallenge(authChallengeRequest);
                        authenticationResultType = authChallengeResult.getAuthenticationResult();
                        log.info("access_token : {}", authenticationResultType.getAccessToken());
                        log.info("id_token : {}", authenticationResultType.getIdToken());
                        log.info("refresh_token : {}", authenticationResultType.getRefreshToken());
                        log.info("expires_in : {}", authenticationResultType.getExpiresIn());
                        log.info("token_type : {}", authenticationResultType.getTokenType());
                        userDetails = buildUserSignInResponse(authenticationResultType);
                    }
                } else {
                    throw new RuntimeException("User has other challenge " + authResult.getChallengeName());
                }
            }else {
                log.info("user has no challenge...");
                authenticationResultType = authResult.getAuthenticationResult();
                log.info("access_token : {}", authenticationResultType.getAccessToken());
                log.info("id_token : {}", authenticationResultType.getIdToken());
                log.info("refresh_token : {}", authenticationResultType.getRefreshToken());
                log.info("expires_in : {}", authenticationResultType.getExpiresIn());
                log.info("token_type : {}", authenticationResultType.getTokenType());

                 userDetails = buildUserSignInResponse(authenticationResultType);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            cognitoClient.shutdown();
        }
        return userDetails;
    }

    private UserSignInResponse buildUserSignInResponse(AuthenticationResultType authenticationResultType){
        return UserSignInResponse.builder()
                .accessToken(authenticationResultType.getAccessToken())
                .expiresIn(authenticationResultType.getExpiresIn())
                .idToken(authenticationResultType.getIdToken())
                .refreshToken(authenticationResultType.getRefreshToken())
                .tokenType(authenticationResultType.getTokenType())
                .expiresIn(authenticationResultType.getExpiresIn())
                .build();
    }

    public void changePassword(String username, String password){
        try {
            // Make the password permanent and not temporary
            AdminSetUserPasswordRequest adminSetUserPasswordRequest =
                    new AdminSetUserPasswordRequest().withUsername(username)
                            .withUserPoolId(userPoolId).withPassword(password).withPermanent(true);
            cognitoClient.adminSetUserPassword(adminSetUserPasswordRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
