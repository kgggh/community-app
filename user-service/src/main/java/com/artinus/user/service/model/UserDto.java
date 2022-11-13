package com.artinus.user.service.model;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private String userId;
    private String email;
    private String password;

    @Builder
    protected UserDto(String userId, String email, String password) {
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
}
