package com.dunhili.sasbank.auth.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Enum representing the different roles a user can have.
 * <ul>
 *     <li>User</li>
 *     <li>Manager</li>
 *     <li>Support</li>
 *     <li>Admin</li>
 * </ul>
 */
public enum Role {
    USER,
    MANAGER,
    SUPPORT,
    ADMIN;

    @JsonCreator
    public static Role fromString(String role) {
        return role != null ? Role.valueOf(role.toUpperCase()) : null;
    }
}
