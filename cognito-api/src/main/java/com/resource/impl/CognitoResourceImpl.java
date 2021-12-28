package com.resource.impl;

import com.pojo.request.UserSignInRequest;
import com.pojo.request.UserSignUpRequest;
import com.resource.ICognitoResource;
import com.service.aws.AwsCognitoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Cognito Resource")
public class CognitoResourceImpl implements ICognitoResource{

    @Autowired
    private AwsCognitoService cognitoResource;


    @Tag(name = "Cognito Resource")
    @Override
    public ResponseEntity<Object> signUp(UserSignUpRequest request) {
        cognitoResource.signUp(request);
        return ResponseEntity.ok("User is created");
    }

    @Tag(name = "Cognito Resource")
    @Override
    public ResponseEntity<Object> signIn(UserSignInRequest request) {
        return ResponseEntity.ok(cognitoResource.signIn(request));
    }

    @Tag(name = "Cognito Resource")
    @Override
    public ResponseEntity<Object> changePassword(String userName, String password) {
        cognitoResource.changePassword(userName, password);
        return ResponseEntity.ok("Password is changed");
    }

    @Override
    public ResponseEntity<Object> getDetails() {
            return ResponseEntity.ok("details");
    }
}
