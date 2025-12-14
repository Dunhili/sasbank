package com.dunhili.sasbank.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Wrapper class for API responses. Contains the payload along with additional metadata about the response.
 * @param <T> type of data for the payload
 */
@Getter
@Setter
public class ApiResponse<T> {
    private T payload;
    private String requestId;
    private List<ValidationError> errors = new ArrayList<>();
}
