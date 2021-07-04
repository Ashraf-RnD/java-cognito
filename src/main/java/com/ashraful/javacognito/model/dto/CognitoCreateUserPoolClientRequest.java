package com.ashraful.javacognito.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CognitoCreateUserPoolClientRequest {
    private String clientName;
    private String userPoolId;
}
