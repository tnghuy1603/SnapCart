package com.snapcart.api_gateway.module.user.controller;

import com.snapcart.api_gateway.module.user.UserServiceClient;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserServiceClient userServiceClient;
    @PostMapping("/login")
    public Object login(@RequestBody Object request) {
        return userServiceClient.login(request);
    }

    @PostMapping("/register")
    public Object register(@RequestBody Object request) {
        return userServiceClient.register(request);
    }
}
