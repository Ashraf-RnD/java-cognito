package com.ashraful.javacognito.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.cognitoidentity.CognitoIdentityAsyncClient;
import software.amazon.awssdk.services.cognitoidentityprovider.CognitoIdentityProviderAsyncClient;

@Configuration
public class AppConfig {

    @Bean
    public CognitoIdentityAsyncClient cognitoIdentityAsyncClient(){
        return CognitoIdentityAsyncClient.builder().region(Region.AP_SOUTHEAST_1).build();
    }

    @Bean
    public CognitoIdentityProviderAsyncClient cognitoIdentityProviderAsyncClient(){
        return CognitoIdentityProviderAsyncClient.builder().region(Region.AP_SOUTHEAST_1).build();
    }


}
