package com.dunhili.sasbank.common.enums;

import lombok.Getter;

@Getter
public enum ValidationMessages {
    LOGIN_NO_USERNAME_PROVIDED("No username was provided."),
    LOGIN_NO_PASSWORD_PROVIDED("No password was provided."),
    LOGIN_NO_USER_ID_PROVIDED("No user ID was provided."),
    USER_NOT_FOUND("User with ID '%s' was not found."),
    USER_INVALID_SSN("The given SSN of %s was invalid."),
    USER_EXACTLY_ONE_PRIMARY_ADDRESS("Exactly one address must be marked as primary."),
    USER_EXACTLY_ONE_PRIMARY_PHONE("Exactly one phone number must be marked as primary.");

    private final String message;

    ValidationMessages(String message) {
        this.message = message;
    }
}
