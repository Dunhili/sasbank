package com.dunhili.sasbank.common.exception;

import com.dunhili.sasbank.common.dto.ValidationError;
import com.dunhili.sasbank.common.enums.ValidationMessages;
import lombok.Getter;

import java.util.List;

/**
 * Custom exception class for API service errors.
 */
@Getter
public class ApiServiceException extends RuntimeException {
    private final int httpStatus;
    private final String errorCode;
    private final List<ValidationError> validationMessages;

    public ApiServiceException(ValidationMessages validationMessage, int httpStatus) {
        this(validationMessage.name(), validationMessage.getMessage(), httpStatus, List.of());
    }

    public ApiServiceException(ValidationMessages validationMessage, int httpStatus, String formattedString) {
        this(validationMessage.name(), String.format(validationMessage.getMessage(), formattedString), httpStatus, List.of());
    }

    public ApiServiceException(String errorCode, String errorMessage, int httpStatus) {
        this(errorCode, errorMessage, httpStatus, List.of());
    }

    /**
     * Constructor for ApiServiceException with a single validation error message.
     * @param errorCode error code for the exception
     * @param errorMessage error message for the exception
     * @param httpStatus HTTP code
     * @param validationMessage Validation error
     */
    public ApiServiceException(String errorCode, String errorMessage, int httpStatus, ValidationError validationMessage) {
        this(errorMessage, errorCode, httpStatus, List.of(validationMessage));
    }

    /**
     * Constructor for ApiServiceException with a list of validation error messages.
     * @param errorCode error code for the exception
     * @param errorMessage error message for the exception
     * @param httpStatus HTTP code
     * @param validationMessages list of validation errors
     */
    public ApiServiceException(String errorCode, String errorMessage, int httpStatus, List<ValidationError> validationMessages) {
        super(errorMessage);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.validationMessages = validationMessages;
    }
}
