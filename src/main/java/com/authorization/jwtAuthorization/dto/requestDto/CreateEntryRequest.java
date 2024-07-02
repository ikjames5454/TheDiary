package com.authorization.jwtAuthorization.dto.requestDto;

import lombok.Data;

@Data
public class CreateEntryRequest {
    private String title;
    private String body;
}
