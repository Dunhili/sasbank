package com.dunhili.sasbank.common.exception;

import com.dunhili.sasbank.common.dto.ValidationError;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiServiceErrorResponse {
    private int httpStatus;
    private String errorCode;
    private String errorMessage;
    private List<ValidationError> validationMessages;
    private String requestId;
    private Date timestamp;
}
