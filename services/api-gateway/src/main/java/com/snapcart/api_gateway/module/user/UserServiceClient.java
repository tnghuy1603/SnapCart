package com.snapcart.api_gateway.module.user;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "user-service", url = "${api.user-service-host}")
public interface UserServiceClient {
    @PostMapping("auth/login")
    ResponseEntity<?> login(@RequestBody Object request);

    @PostMapping("auth/register")
    ResponseEntity<?> register(@RequestBody Object request);

    @GetMapping("users/{id}")
    ResponseEntity<?> getUserById(@PathVariable("id") String id);
}
