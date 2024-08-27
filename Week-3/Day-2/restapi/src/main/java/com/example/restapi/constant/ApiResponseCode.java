package com.example.restapi.constant;

public enum ApiResponseCode {
    OK("200"),
    ERROR("0");

    private String code;
    ApiResponseCode(String code) { this.code=code;}
}
