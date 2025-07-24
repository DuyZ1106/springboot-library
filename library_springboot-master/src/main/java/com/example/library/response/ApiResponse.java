package com.example.library.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse<T> {

    private String code;
    private String message;
    private T data;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>("success", "Success", data);
    }

    public static ApiResponse<?> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
