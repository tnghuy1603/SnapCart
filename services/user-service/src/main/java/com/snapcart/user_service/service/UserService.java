package com.snapcart.user_service.service;

import com.snapcart.user_service.dto.response.UserResponse;

public interface UserService {
    UserResponse getUserById(String id);
}
