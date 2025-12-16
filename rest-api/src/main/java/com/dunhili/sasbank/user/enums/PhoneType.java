package com.dunhili.sasbank.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * Enum representing the different phone types a user can have.
 * <ul>
 *     <li>Home</li>
 *     <li>Mobile</li>
 *     <li>Work</li>
 *     <li>Fax</li>
 * </ul>
 */
public enum PhoneType {
    HOME,
    MOBILE,
    WORK,
    FAX;

    @JsonCreator
    public static PhoneType fromString(String phoneType) {
        return phoneType != null ? PhoneType.valueOf(phoneType.toUpperCase()) : null;
    }
}
