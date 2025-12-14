package com.dunhili.sasbank.common.enums;

import lombok.Getter;

@Getter
public enum ValidationMessages {
    USER_NOT_FOUND("User with ID %s was not found."),
    USER_INVALID_SSN("The given SSN of %s was invalid."),
    USER_EXACTLY_ONE_PRIMARY_ADDRESS("Exactly one address must be marked as primary."),
    USER_EXACTLY_ONE_PRIMARY_PHONE("Exactly one phone number must be marked as primary.");

    private final String message;

    ValidationMessages(String message) {
        this.message = message;
    }
}
