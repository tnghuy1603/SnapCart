package com.snapcart.user_service.dto.request;

import com.snapcart.user_service.constant.UserRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String email;
    private String password;
    private UserRole role;
}
