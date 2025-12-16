package com.dunhili.sasbank.user.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

/**
 * Enum representing the current states of a user's account.
 * <ul>
 *     <li>Active</li>
 *     <li>Inactive</li>
 *     <li>Locked</li>
 *     <li>Banned</li>
 *     <li>Deleted</li>
 * </ul>
 */
public enum AccountStatus {
    ACTIVE,
    INACTIVE,
    LOCKED,
    BANNED,
    DELETED;

    @JsonCreator
    public static AccountStatus fromString(String accountStatus) {
        return accountStatus != null ? AccountStatus.valueOf(accountStatus.toUpperCase()) : null;
    }
}
