package obss.project.basket.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import obss.project.basket.constant.ApiResponseCode;
import org.springframework.http.HttpStatus;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> implements Serializable {
    private T data;
    private String message;
    private ApiResponseCode code;

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data, null, ApiResponseCode.OK);
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return new ApiResponse<>(data, message, ApiResponseCode.OK);
    }

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(null, null, ApiResponseCode.OK);
    }

    public static <T> ApiResponse<T> error(String message, ApiResponseCode code) {
        return new ApiResponse<>(null, message, code);
    }

    public static ApiResponse<?> success(AuthResponseDto authResponseDto, String message, ApiResponseCode apiResponseCode) {
        return new ApiResponse<>(authResponseDto, message, ApiResponseCode.OK);
    }
}
