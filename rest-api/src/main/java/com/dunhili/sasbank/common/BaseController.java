package com.dunhili.sasbank.common;

import com.dunhili.sasbank.common.dto.ApiResponse;
import org.slf4j.MDC;
import org.springframework.http.ResponseEntity;

/**
 * Base controller class for all REST controllers. Contains additional methods for helping with response entities.
 */
public abstract class BaseController {

    /**
     * Returns a 200 OK response with an empty payload.
     * @param <T> Type of payload.
     * @return 200 Response with an empty payload.
     */
    public <T> ResponseEntity<ApiResponse<T>> ok() {
        ApiResponse<T> response = new ApiResponse<>();
        response.setRequestId(getRequestId());
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a 200 OK response with the given payload.
     * @param <T> Type of payload.
     * @param payload Payload to return in the response.
     * @return 200 Response with the given payload.
     */
    public <T> ResponseEntity<ApiResponse<T>> ok(T payload) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setPayload(payload);
        response.setRequestId(getRequestId());
        return ResponseEntity.ok(response);
    }

    /**
     * Returns a 400 Bad Request response with the given payload.
     * @param payload Payload to return in the response.
     * @param <T> Type of payload.
     * @return 400 Response with the given payload.
     */
    public <T> ResponseEntity<ApiResponse<T>> badRequest(T payload) {
        ApiResponse<T> response = new ApiResponse<>();
        response.setPayload(payload);
        response.setRequestId(getRequestId());
        return ResponseEntity.badRequest().body(response);
    }

    /**
     * Returns a 404 Not Found response.
     * @param <T> Type of payload.
     * @return 404 Response with no payload.
     */
    public <T> ResponseEntity<T> notFound() {
        return ResponseEntity.notFound().build();
    }

    private String getRequestId() {
        return MDC.get("requestId");
    }
}
