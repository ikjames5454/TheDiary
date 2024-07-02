package com.authorization.jwtAuthorization.dto.requestDto;

import lombok.Data;

@Data
public class CreateDiaryRequest {
    private String diaryName;
    private String imageUrl;
}
