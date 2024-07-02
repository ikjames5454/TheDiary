package com.authorization.jwtAuthorization.dto.requestDto;

import lombok.Data;

@Data
public class createEntryRequest {
    private String title;
    private String body;
}
