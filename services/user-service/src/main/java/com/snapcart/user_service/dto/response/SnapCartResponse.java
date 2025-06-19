package com.snapcart.user_service.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
@Builder
public class SnapCartResponse<T> {
    private boolean success;
    private String message;
    private Integer code;
    private T data;
    private Integer currentPage;
    private Integer totalPage;
    private Integer totalElement;
    private Integer pageSize;
    public static <T> SnapCartResponse<T> successResponse(String message) {
        return SnapCartResponse.<T>builder()
                .success(true)
                .message(message)
                .code(200)
                .build();
    }
    public static <T> SnapCartResponse<T> successResponse(T data, @Nullable String message) {
        return SnapCartResponse.<T>builder()
                .success(true)
                .data(data)
                .code(200)
                .message(message)
                .build();
    }

    public static <T> SnapCartResponse<T> failureResponse(String message, Integer code) {
        return SnapCartResponse.<T>builder()
                .success(false)
                .code(code)
                .message(message)
                .build();
    }

    public static <T> SnapCartResponse<List<T>> successListResponse(Page<T> page, @Nullable String message) {
        return SnapCartResponse.<List<T>>builder()
                .success(true)
                .message(message)
                .data(page.getContent())
                .currentPage(page.getNumber())
                .totalPage(page.getTotalPages())
                .totalElement(page.getTotalPages())
                .pageSize(page.getSize())
                .build();
    }
}
