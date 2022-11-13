package com.artinus.user.service.model;

import lombok.Builder;
import lombok.Data;

@Data
public class PayloadDto {
    private String accessToken;
    private long accessExpiredTime;

    @Builder
    public PayloadDto(String accessToken, long accessExpiredTime) {
        this.accessToken = accessToken;
        this.accessExpiredTime = accessExpiredTime;
    }
}
