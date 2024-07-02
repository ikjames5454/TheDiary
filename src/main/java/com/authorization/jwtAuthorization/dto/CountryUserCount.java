package com.authorization.jwtAuthorization.dto;

import lombok.Data;

@Data
public class CountryUserCount {
    private String country;
    private Long userCount;

    public CountryUserCount(String country, Long userCount) {
        this.country = country;
        this.userCount = userCount;
    }

}
