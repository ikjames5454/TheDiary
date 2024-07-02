package com.authorization.jwtAuthorization.dto;

import lombok.Data;

@Data
public class DiaryResponse {
    private int statusCode;
    private String error;
    private String message;
}
