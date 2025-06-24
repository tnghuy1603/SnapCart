package com.snapcart.order_service.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class UserInfo {
    private String id;
    private String email;
    private String phoneNumber;
    private String username;
    private List<String> address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
