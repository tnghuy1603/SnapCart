package com.snapcart.user_service.service.impl;

import com.snapcart.user_service.dto.response.UserResponse;
import com.snapcart.user_service.entity.UserEntity;
import com.snapcart.user_service.repository.UserRepository;
import com.snapcart.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserResponse getUserById(String id) {
        UserEntity entity = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        UserResponse userResponse = new UserResponse();
        BeanUtils.copyProperties(entity, userResponse);
        return userResponse;
    }
}
