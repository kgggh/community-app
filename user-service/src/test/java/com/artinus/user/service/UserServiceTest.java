package com.artinus.user.service;

import com.artinus.user.domain.User;
import com.artinus.user.domain.UserRepository;
import com.artinus.user.exception.UserCommonException;
import com.artinus.user.service.model.PayloadDto;
import com.artinus.user.service.model.UserDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired UserService userService;
    @Autowired UserRepository userRepository;
    @Autowired PasswordEncoder passwordEncoder;

    @Test
    void register() {
        //given
        UserDto userDto1 = UserDto.builder()
                .email("tester")
                .password("testPassword")
                .build();

        //email null, blank check
        UserDto userDto2 = UserDto.builder()
                .email("")
                .password("testPassword")
                .build();

        //email 길이 20자 초과
        UserDto userDto3 = UserDto.builder()
                .email("11111111111111111111111111111111111111111111111111111111111111111")
                .password("testPassword")
                .build();
        //when
        String userId = userService.register(userDto1);
        assertThrows(UserCommonException.class, () -> userService.register(userDto2));
        assertThrows(UserCommonException.class, () -> userService.register(userDto3));

        //then
        assertNotNull(userId);
    }

    @Test
    void login() {
        //given
        String email = "tester";
        String password = "password";
        User savedUser = userRepository.save(User.newInstance(email, passwordEncoder.encode(password)));
        UserDto loginSucceedUserDto = UserDto.builder()
                .email(savedUser.getEmail())
                .password(password)
                .build();

        UserDto loginEmailFailUserDto = UserDto.builder()
                .email("존재하지 않음.")
                .password(password)
                .build();

        UserDto loginPasswordFailUserDto = UserDto.builder()
                .email(savedUser.getEmail())
                .password("invalidPassword")
                .build();

        //when
        PayloadDto payloadDto = userService.login(loginSucceedUserDto);
        assertThrows(UserCommonException.class, () -> userService.login(loginEmailFailUserDto));
        assertThrows(UserCommonException.class, () -> userService.login(loginPasswordFailUserDto));

        //then
        assertNotNull(payloadDto);
    }

    @Test
    void logout() {
        /* Todo */
        //given


        //when


        //then
    }
    @Test
    void getUserInfo() {
        //given
        String email = "tester";
        String password = "password";
        UUID userId = userRepository.save(User.newInstance(email, passwordEncoder.encode(password))).getId();

        //when
        UserDto userInfo = userService.getUserInfoByUserId(userId.toString());
        assertThrows(RuntimeException.class, () -> userService.getUserInfoByUserId("12354vf4"));

        //then
        assertNotNull(userInfo);
    }
}