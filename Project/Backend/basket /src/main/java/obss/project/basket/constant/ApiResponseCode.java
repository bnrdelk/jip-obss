package obss.project.basket.constant;

import lombok.Getter;

@Getter
public enum ApiResponseCode {
    OK("201"),
    VALIDATION_ERROR("422"),
    NOT_FOUND("404"),
    UNAUTHORIZED("401"),
    FORBIDDEN("403"),
    CONFLICT("409"),
    INTERNAL_SERVER_ERROR("500"),
    BAD_REQUEST("501"),
    CREATED("200");

    private String code;

    ApiResponseCode(String code) {
        this.code = code;
    }

}
