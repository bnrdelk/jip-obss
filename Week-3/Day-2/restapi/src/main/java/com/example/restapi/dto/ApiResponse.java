package com.example.restapi.dto;

import com.example.restapi.constant.ApiResponseCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private T data;
    private String message;
    private ApiResponseCode code;

    public ApiResponse(ApiResponseCode apiResponseCode, String message) {
        this.code = apiResponseCode;
        this.message = message;
    }

    public static <T> ApiResponse<T> of(T data) {
        return new ApiResponse<>(data, null, ApiResponseCode.OK);
    }

    public static <T> ApiResponse<T> of(T data, String message) {
        return new ApiResponse<>(data, message, ApiResponseCode.OK);
    }

    public static <T> ApiResponse<T> of(T data, ApiResponseCode apiResponseCode) {
        return new ApiResponse<>(data, null, apiResponseCode);
    }

    public static <T> ApiResponse<T> of(T data, String message, ApiResponseCode apiResponseCode) {
        return new ApiResponse<>(data, message, apiResponseCode);
    }

    public static ApiResponse<Void> ofCode(ApiResponseCode apiResponseCode) {
        return new ApiResponse<>(null, null, apiResponseCode);
    }

    public static ApiResponse<Void> ofCode(ApiResponseCode apiResponseCode, String message) {
        return new ApiResponse<>(null, message, apiResponseCode);
    }
}
