package com.snapcart.order_service.service;

import com.snapcart.order_service.dto.response.SnapCartResponse;
import com.snapcart.order_service.dto.response.UserInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "user-service", url = "${url.user-service}")
public interface UserServiceClient {
    @GetMapping("/users/{id}")
    SnapCartResponse<UserInfo> getUserInfo(@PathVariable("id") String userId);
}
