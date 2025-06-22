package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.response.SnapCartResponse;
import com.snapcart.order_service.dto.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient
public interface UserServiceClient {
    @GetMapping
    SnapCartResponse<UserInfo> getUserInfo(@RequestParam String userId);
}
