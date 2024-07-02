package com.authorization.jwtAuthorization.dto;

import lombok.Data;

@Data
public class DeleteResponse {
    private int statusCode;
    private String error;
    private String message;
}
