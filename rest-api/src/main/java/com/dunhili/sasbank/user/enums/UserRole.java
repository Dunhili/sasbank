package com.dunhili.sasbank.user.enums;

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
public enum UserRole {
    USER,
    MANAGER,
    SUPPORT,
    ADMIN;

    @JsonCreator
    public static UserRole fromString(String role) {
        return role != null ? UserRole.valueOf(role.toUpperCase()) : null;
    }
}
