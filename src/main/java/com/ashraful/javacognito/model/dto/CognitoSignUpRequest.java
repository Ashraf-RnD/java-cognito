package com.ashraful.javacognito.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CognitoSignUpRequest {
    private String clientId;
    private String secretKey;
    private String userName;
    private String password;
}
