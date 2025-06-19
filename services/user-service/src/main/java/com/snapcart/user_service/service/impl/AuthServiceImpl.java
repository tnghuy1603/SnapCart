package com.snapcart.user_service.service.impl;

import com.snapcart.user_service.config.Session;
import com.snapcart.user_service.dto.LoginRequest;
import com.snapcart.user_service.dto.LoginResponse;
import com.snapcart.user_service.dto.RegisterRequest;
import com.snapcart.user_service.dto.response.RegisterResponse;
import com.snapcart.user_service.entity.UserEntity;
import com.snapcart.user_service.repository.UserRepository;
import com.snapcart.user_service.service.AuthService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    @Override
    public void login(LoginRequest request, HttpServletResponse response) {
        UserEntity userEntity = userRepository.findByEmail(request.getEmail());
        if (userEntity == null) {
            throw new RuntimeException("Invalid email or password");
        }
        //TODO use password encoder
        if (!userEntity.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Invalid password");
        }
        Session session = Session.builder()
                .id(UUID.randomUUID().toString())
                .username(userEntity.getUsername())
                .email(userEntity.getEmail())
                .role(userEntity.getRole())
                .build();
        ResponseCookie responseCookie = ResponseCookie.from("AuthToken", session.getId())
                .httpOnly(true)
                .maxAge(3600)
                .sameSite("same-site")
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());

    }

    @Override
    public void register(RegisterRequest request) {
        UserEntity existingEntity = userRepository.findByEmail(request.getEmail());
        if (existingEntity != null) {
            throw new RuntimeException("Email already in use");
        }
        UserEntity userEntity = UserEntity.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role(request.getRole())
                .build();
        userRepository.save(userEntity);
    }
}
