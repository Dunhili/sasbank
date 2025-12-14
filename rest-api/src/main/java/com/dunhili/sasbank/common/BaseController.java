package com.dunhili.sasbank.common;

import com.dunhili.sasbank.common.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Base controller class for all REST controllers. Contains additional methods for helping with response entities.
 *
 * Base URL - /restapi
 */
@RestController
@RequestMapping("/restapi")
public class BaseController {

    /**
     * Returns a 200 OK response with an empty payload.
     * @param <T> Type of payload.
     * @return 200 Response with an empty payload.
     */
    public <T> ResponseEntity<ApiResponse<T>> ok() {
        ApiResponse<T> response = new ApiResponse<>();
        // set request id
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
        // set request id
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
        // set request id
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
}
