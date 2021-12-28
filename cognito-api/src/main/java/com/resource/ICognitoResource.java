package com.resource;

import com.constants.AppConstant;
import com.pojo.request.UserSignInRequest;
import com.pojo.request.UserSignUpRequest;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping(AppConstant.API_USERS)
public interface ICognitoResource {

    @ApiOperation(value = "SignUp new User", response = ResponseEntity.class)
    @PostMapping(value =  "/sign-up")
    ResponseEntity<Object> signUp(@RequestBody final UserSignUpRequest request);

    @ApiOperation(value = "SignIn existing User", response = ResponseEntity.class)
    @PostMapping(value =  "/sign-in")
    ResponseEntity<Object> signIn(@RequestBody final UserSignInRequest request);

    @ApiOperation(value = "Change password for existing User", response = ResponseEntity.class)
    @PutMapping(value =  "/change-password")
    ResponseEntity<Object> changePassword(@RequestParam("username") final String userName,
                                          @RequestParam("password") final String password);

    @GetMapping(value = "details")
    ResponseEntity<Object> getDetails();
}
