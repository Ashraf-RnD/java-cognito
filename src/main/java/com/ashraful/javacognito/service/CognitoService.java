package com.ashraful.javacognito.service;

import com.ashraful.javacognito.model.dto.*;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolClientResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.CreateUserPoolResponse;
import software.amazon.awssdk.services.cognitoidentityprovider.model.SignUpResponse;

import java.util.concurrent.CompletableFuture;

public interface CognitoService {
    CompletableFuture<String> createCognitoIdentityPool(CognitoCreatePoolRequest cognitoCreatePoolRequest);

    CompletableFuture<CognitoCreatePoolResponse> createCognitoUserPool(CognitoCreatePoolRequest cognitoCreatePoolRequest);

    CompletableFuture<CognitoCreateUserPoolClientResponse> createCognitoPoolClient(CognitoCreateUserPoolClientRequest cognitoCreateUserPoolClientRequest);

    CompletableFuture<Boolean> signUp(CognitoSignUpRequest cognitoSignUpRequest);
}