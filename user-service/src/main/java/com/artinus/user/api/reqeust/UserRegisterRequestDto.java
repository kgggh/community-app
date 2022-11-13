package com.artinus.user.api.reqeust;

import com.artinus.user.service.model.UserDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserRegisterRequestDto {
    private String email;
    private String password;

    public UserDto toUserDto() {
        return UserDto.builder()
                .email(this.email)
                .password(this.password)
                .build();
    }
}
