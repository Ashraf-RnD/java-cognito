package com.ashraful.javacognito.service;

import com.ashraful.javacognito.model.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityAsyncClient;
import software.amazon.awssdk.services.cognitoidentity.model.CreateIdentityPoolRequest;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderAsyncClient;
import software.amazon.awssdk.services.cognitoidentityprovider.model.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class CognitoServiceImpl implements CognitoService{

    private final String HMAC_SHA256_ALGORITHM = "HmacSHA256";

    private final CognitoIdentityAsyncClient cognitoIdentityAsyncClient;
    private final CognitoIdentityProviderAsyncClient cognitoIdentityProviderAsyncClient;

    @Override
    public CompletableFuture<String> createCognitoIdentityPool(CognitoCreatePoolRequest cognitoCreatePoolRequest) {
        log.info("createCognitoIdentityPool:: request: {}",cognitoCreatePoolRequest.toString());

        return cognitoIdentityAsyncClient.createIdentityPool(CreateIdentityPoolRequest.builder()
                .allowUnauthenticatedIdentities(false)
                .identityPoolName(cognitoCreatePoolRequest.getPoolName())
                .build())
                .thenApply(createIdentityPoolResponse -> {
                    log.info("createCognitoPool:: createIdentityPool:: createIdentityPoolResponse: {}", createIdentityPoolResponse.toString());
                    return createIdentityPoolResponse.identityPoolId();
                });

    }

    @Override
    public CompletableFuture<CognitoCreatePoolResponse> createCognitoUserPool(CognitoCreatePoolRequest cognitoCreatePoolRequest) {
        log.info("createCognitoUserPool:: request: {}",cognitoCreatePoolRequest.toString());
        return cognitoIdentityProviderAsyncClient.createUserPool(getUserPoolCreateRequest(cognitoCreatePoolRequest))
                .thenApply(createUserPoolResponse -> {
                    log.info("createCognitoUserPool:: response: {}", createUserPoolResponse.toString());
                    return CognitoCreatePoolResponse.builder().poolId(createUserPoolResponse.userPool().id()).build();
                });
    }

    private CreateUserPoolRequest getUserPoolCreateRequest(CognitoCreatePoolRequest cognitoCreatePoolRequest) {
        return CreateUserPoolRequest.builder()
                .poolName(cognitoCreatePoolRequest.getPoolName())
                .build();
    }

    @Override
    public CompletableFuture<CognitoCreateUserPoolClientResponse> createCognitoPoolClient(CognitoCreateUserPoolClientRequest cognitoCreateUserPoolClientRequest) {
        return cognitoIdentityProviderAsyncClient.createUserPoolClient(CreateUserPoolClientRequest.builder()
                .clientName(cognitoCreateUserPoolClientRequest.getClientName())
                .generateSecret(true)
                .userPoolId(cognitoCreateUserPoolClientRequest.getUserPoolId())
                .allowedOAuthFlowsUserPoolClient(true)
                .allowedOAuthFlows(OAuthFlowType.CLIENT_CREDENTIALS)
                .allowedOAuthScopes("allowedScope")//Allowed Custom Scopes
                .supportedIdentityProviders("COGNITO")
                .build())
                .thenApply(createUserPoolClientResponse -> {
                    log.info("createCognitoPoolClient:: createUserPoolClientResponse: {}", createUserPoolClientResponse.toString());
                    return CognitoCreateUserPoolClientResponse.builder()
                            .clientId(createUserPoolClientResponse.userPoolClient().clientId())
                            .clientSecret(createUserPoolClientResponse.userPoolClient().clientSecret())
                            .build();
                });
    }

    @Override
    public CompletableFuture<Boolean> signUp(CognitoSignUpRequest cognitoSignUpRequest) {

        log.info("signUp:: request: {}",cognitoSignUpRequest.toString());
        var secretVal = calculateSecretHash(cognitoSignUpRequest.getClientId(), cognitoSignUpRequest.getSecretKey(), cognitoSignUpRequest.getUserName());

        var signUpRequest = SignUpRequest.builder()
                .username(cognitoSignUpRequest.getUserName())
                .clientId(cognitoSignUpRequest.getClientId())
                .password(cognitoSignUpRequest.getPassword())
                .userAttributes(AttributeType.builder().name("email").value("alam.ashraful@bkash.com").build())
                .secretHash(secretVal)
                .build();

        return cognitoIdentityProviderAsyncClient.signUp(signUpRequest)
                .thenApply(signUpResponse -> {
                    log.info("signUp:: signUpResponse:: {}",signUpResponse.toString());
                    return signUpResponse.userConfirmed();
                });
    }

    private String calculateSecretHash(String userPoolClientId, String userPoolClientSecret, String userName) {

        var signingKey = new SecretKeySpec(userPoolClientSecret.getBytes(StandardCharsets.UTF_8), HMAC_SHA256_ALGORITHM);
        try {
            Mac mac = Mac.getInstance(HMAC_SHA256_ALGORITHM);
            mac.init(signingKey);
            mac.update(userName.getBytes(StandardCharsets.UTF_8));
            byte[] rawHmac = mac.doFinal(userPoolClientId.getBytes(StandardCharsets.UTF_8));
            return java.util.Base64.getEncoder().encodeToString(rawHmac);
        } catch (Exception e) {
            throw new RuntimeException("Error while calculating ");
        }
    }
}