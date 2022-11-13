package com.artinus.user.api.response;

import com.artinus.user.service.model.UserDto;
import lombok.Data;

@Data
public class UserInfoResponseDto {
    private String userId;
    private String email;

    public UserInfoResponseDto(UserDto userDto) {
        this.userId = userDto.getUserId();
        this.email = userDto.getEmail();
    }
}
