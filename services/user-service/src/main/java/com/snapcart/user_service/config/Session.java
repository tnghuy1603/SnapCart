package com.snapcart.user_service.config;

import com.snapcart.user_service.constant.UserRole;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Session {
    private String id;
    private String username;
    private String email;
    private UserRole role;
}
