package com.artinus.user.api;

import com.artinus.user.api.reqeust.UserLoginRequestDto;
import com.artinus.user.api.reqeust.UserRegisterRequestDto;
import com.artinus.user.api.response.ResponseVO;
import com.artinus.user.api.response.UserInfoResponseDto;
import com.artinus.user.api.response.UserLoginResponseDto;
import com.artinus.user.service.model.PayloadDto;
import com.artinus.user.service.model.UserDto;
import com.artinus.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/user-service")
public class UserController {
    private final UserService userService;

    @GetMapping("/health")
    public ResponseEntity<Object> healthCheck() {
        return ResponseEntity.ok("lived user-service !!");
    }

    @PostMapping("/users")
    public ResponseEntity<Object> register(@RequestBody UserRegisterRequestDto userRegisterRequestDto) {
        log.info(userRegisterRequestDto.toString());
        String userId = userService.register(userRegisterRequestDto.toUserDto());
        return ResponseEntity.created(URI.create(String.format("/user-service/users/%s", userId)))
                .build();
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        PayloadDto payloadDto = userService.login(userLoginRequestDto.toUserDto());
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto(payloadDto);
        ResponseVO<UserLoginResponseDto> result = ResponseVO.success(userLoginResponseDto);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(@RequestHeader(name = "Authorization", required = false) String token) {
        /* Todo */
        userService.logout(token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        UserDto userDto = userService.getUserInfoByEmail(email);
        UserInfoResponseDto result = new UserInfoResponseDto(userDto);
        return ResponseEntity.ok(result);
    }
}
