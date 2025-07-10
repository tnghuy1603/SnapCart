package com.snapcart.user_service.dto.response;

import com.snapcart.user_service.constant.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class UserResponse {
    private String id;
    private String email;
    private String phoneNumber;
    private String username;
    private List<String> address;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
