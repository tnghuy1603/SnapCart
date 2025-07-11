package com.snapcart.api_gateway.module.user.controller;

import com.snapcart.api_gateway.module.user.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserServiceClient userServiceClient;
    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        return userServiceClient.getUserById(id);
    }
}
