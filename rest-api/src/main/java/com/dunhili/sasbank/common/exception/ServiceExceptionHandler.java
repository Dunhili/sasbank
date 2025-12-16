package com.dunhili.sasbank.common.exception;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

/**
 * Custom exception handler for service layer exceptions.
 */
@RestControllerAdvice
@Slf4j
public class ServiceExceptionHandler {

    /**
     * Handles exceptions thrown by the service layer.
     * @param e Exception thrown by the service layer.
     * @return API error response containing the error details.
     */
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

    /**
     * Handles authorization denied exceptions thrown by the service layer.
     * @param e Exception thrown by the service layer.
     * @return API error response containing the error details.
     */
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<ApiServiceErrorResponse> handleApiException(AuthorizationDeniedException e) {
        log.error(e.getMessage(), e);

        ApiServiceErrorResponse response = new ApiServiceErrorResponse(
                403,
                "UNAUTHORIZED",
                "User is not authorized to perform this action.",
                null,
                MDC.get("requestId"),
                new Date()
        );

        return ResponseEntity.status(403).body(response);
    }

    /**
     * Handles any other exceptions thrown by the service layer.
     * @param e Exception thrown by the service layer.
     * @return API error response containing the error details.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiServiceErrorResponse> handleException(Exception e) {
        log.error(e.getMessage(), e);

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
