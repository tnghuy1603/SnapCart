package com.snapcart.order_service.exception;

import lombok.Getter;

@Getter
public class OrderServiceException extends RuntimeException {
    private int code;
    public OrderServiceException(int code, String message) {
        super(message);
        this.code = code;
    }
    public static OrderServiceException buildBadRequest(String message) {
        return new OrderServiceException(400, message);
    }
    public static OrderServiceException buildResourceNotFound(String message) {
        return new OrderServiceException(404, message);
    }
}
