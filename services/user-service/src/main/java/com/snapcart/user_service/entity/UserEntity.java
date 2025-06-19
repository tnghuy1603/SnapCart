package com.snapcart.user_service.entity;

import com.snapcart.user_service.constant.UserRole;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.annotation.Documented;
import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("user")
public class UserEntity {
    @Id
    private String id;
    @Indexed
    private String email;
    private String password;
    @Indexed
    private String phoneNumber;
    private String username;
    private List<String> address;
    private UserRole role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
