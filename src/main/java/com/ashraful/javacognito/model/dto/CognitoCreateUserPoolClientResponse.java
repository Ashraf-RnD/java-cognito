package com.ashraful.javacognito.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CognitoCreateUserPoolClientResponse {
    private String clientId;
    private String clientSecret;
}
