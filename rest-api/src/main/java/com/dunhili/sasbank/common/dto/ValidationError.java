package com.dunhili.sasbank.common.dto;

import com.dunhili.sasbank.common.enums.ValidationMessages;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidationError {
    private String errorCode;
    private String errorMessage;

    public ValidationError(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ValidationError(ValidationMessages validation) {
        this(validation.name(), validation.getMessage());
    }
}
