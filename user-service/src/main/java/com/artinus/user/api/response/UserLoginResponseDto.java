package com.artinus.user.api.response;

import com.artinus.user.service.model.PayloadDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginResponseDto {
    private String accessToken;
    private long accessExpiredTime;

    public UserLoginResponseDto(PayloadDto payloadDto) {
        this.accessToken = payloadDto.getAccessToken();
        this.accessExpiredTime = payloadDto.getAccessExpiredTime();
    }
}
