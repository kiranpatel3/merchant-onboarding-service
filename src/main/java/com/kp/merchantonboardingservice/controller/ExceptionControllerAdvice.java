package com.kp.merchantonboardingservice.controller;

import com.kp.merchantonboardingservice.models.ApiError;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public final ResponseEntity<ApiError> handleException(Exception ex, WebRequest request) {
        ApiError apiError;
        if (ex instanceof IllegalArgumentException argumentException) {
            apiError = new ApiError(argumentException.getMessage());
            apiError.setErrorMessage(argumentException.getMessage());
            return ResponseEntity.badRequest().body(apiError);
        } else {
            apiError = new ApiError("Internal Server Error occurred");
            return ResponseEntity.internalServerError().body(apiError);
        }
    }
}
