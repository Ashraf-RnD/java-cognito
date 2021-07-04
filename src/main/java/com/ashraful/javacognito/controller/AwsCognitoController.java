package com.ashraful.javacognito.controller;

import com.ashraful.javacognito.model.dto.*;
import com.ashraful.javacognito.service.CognitoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequiredArgsConstructor
public class AwsCognitoController {
    private final CognitoService cognitoService;

    @PostMapping("/createCognitoIdentityPool")
    public Mono<String> createCognitoPool(@RequestBody CognitoCreatePoolRequest cognitoCreatePoolRequest){
        return Mono.fromCompletionStage(cognitoService.createCognitoIdentityPool(cognitoCreatePoolRequest)) ;
    }

    @PostMapping("/createCognitoUserPool")
    public Mono<CognitoCreatePoolResponse> createCognitoUserPool(@RequestBody CognitoCreatePoolRequest cognitoCreatePoolRequest){
        return Mono.fromCompletionStage(cognitoService.createCognitoUserPool(cognitoCreatePoolRequest)) ;
    }

    @PostMapping("/createCognitoPoolClient")
    public Mono<CognitoCreateUserPoolClientResponse> createCognitoPoolClient(@RequestBody CognitoCreateUserPoolClientRequest cognitoCreateUserPoolClientRequest){
        return Mono.fromCompletionStage(cognitoService.createCognitoPoolClient(cognitoCreateUserPoolClientRequest));
    }

    @PostMapping("/signUp")
    public Mono<Boolean> signUp(@RequestBody CognitoSignUpRequest cognitoSignUpRequest){
        return Mono.fromCompletionStage(cognitoService.signUp(cognitoSignUpRequest));
    }
}
