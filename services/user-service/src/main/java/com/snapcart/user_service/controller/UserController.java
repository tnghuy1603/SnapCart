package com.snapcart.user_service.controller;

import com.snapcart.user_service.dto.response.SnapCartResponse;
import com.snapcart.user_service.service.UserService;
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
    private final UserService userService;

    @GetMapping("{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        return SnapCartResponse.successResponse(userService.getUserById(id), null);
    }
}
