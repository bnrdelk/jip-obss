package com.example.restapi.exception;

import com.example.restapi.dto.ApiResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.stream.Collectors;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> exception(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(
                ApiResponse.of(ex.getBindingResult().getAllErrors()
                        .stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .collect(Collectors.joining("\n"))));
    }
}
