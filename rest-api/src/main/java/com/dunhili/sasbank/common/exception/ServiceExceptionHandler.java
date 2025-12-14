package com.dunhili.sasbank.common.exception;

import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ApiServiceException.class)
    public ResponseEntity<ApiServiceErrorResponse> handleApiException(ApiServiceException e) {
        ApiServiceErrorResponse response = new ApiServiceErrorResponse(
                e.getHttpStatus(),
                e.getErrorCode(),
                e.getMessage(),
                CollectionUtils.isEmpty(e.getValidationMessages()) ? null : e.getValidationMessages(),
                MDC.get("requestId"),
                new Date()
        );

        return ResponseEntity.status(e.getHttpStatus()).body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiServiceErrorResponse> handleException(Exception e) {
        ApiServiceErrorResponse response = new ApiServiceErrorResponse(
                500,
                "INTERNAL_ERROR",
                "An unexpected error occurred",
                null,
                MDC.get("requestId"),
                new Date()
        );

        return ResponseEntity.status(500).body(response);
    }
}
