package com.artinus.user.service;

import com.artinus.user.helper.JwtProvider;
import com.artinus.user.domain.User;
import com.artinus.user.domain.UserRepository;
import com.artinus.user.exception.UserCommonException;
import com.artinus.user.service.model.PayloadDto;
import com.artinus.user.service.model.UserDto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String register(UserDto userDto) {
        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(i -> new UserCommonException("Exist email please retry again"));
        User user = User.newInstance(userDto.getEmail(), makeEncryptPassword(userDto.getPassword()));
        return userRepository.save(user).getId().toString();
    }

    public PayloadDto login(UserDto userDto) {
        String signInUserId = getUserId(userDto);
        String accessToken = jwtProvider.makeAccessToken(signInUserId);
        Claims payload = jwtProvider.getPayload(accessToken);
        return PayloadDto.builder()
                .accessToken(accessToken)
                .accessExpiredTime(payload.getExpiration().getTime())
                .build();
    }

    public void logout(String token) {
        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        jwtProvider.addToBlackList(token);
    }

    @Transactional(readOnly = true)
    public UserDto getUserInfoByUserId(String userId) {
        User user = userRepository.findById(UUID.fromString(userId)).orElseThrow(()
                -> new UserCommonException("Not found user"));
        return UserDto.builder()
                .userId(user.getId().toString())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    @Transactional(readOnly = true)
    public UserDto getUserInfoByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()
                -> new UserCommonException("Not found user"));
        return UserDto.builder()
                .userId(user.getId().toString())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    private String makeEncryptPassword(String password) {
        return passwordEncoder.encode(password);
    }

    @Transactional(readOnly = true)
    private String getUserId(UserDto userDto) {
        Optional<User> user = userRepository.findByEmail(userDto.getEmail());
        validateLoginUserInfo(user, userDto.getPassword());
        return user.get().getId().toString();
    }

    @Transactional(readOnly = true)
    private void validateLoginUserInfo(Optional<User> user, String password) {
        log.info(String.format("[VALIDATE] password=%s", password));
        User existUserByEmail = user.orElseThrow(()
                -> new UserCommonException("Invalid email please retry again"));
        if( !passwordEncoder.matches(password, existUserByEmail.getPassword()) ) {
            throw new UserCommonException("Invalid password please retry again");
        }
    }
}
