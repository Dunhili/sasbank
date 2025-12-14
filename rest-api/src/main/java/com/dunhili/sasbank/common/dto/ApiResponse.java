package com.dunhili.sasbank.common.dto;

import java.util.List;
import java.util.UUID;

public class ApiResponse<T> {
    private T payload;
    private UUID requestId;
    private List<ValidationError> errors;
}
