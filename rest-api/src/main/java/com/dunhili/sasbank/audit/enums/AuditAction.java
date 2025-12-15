package com.dunhili.sasbank.audit.enums;

import java.util.Arrays;

/**
 * Enum representing the different actions that have occurred with regard to a database record. This applies to
 * the audit tables for each of the main tables.
 *
 * <ul>
 *     <li>Insert</li>
 *     <li>Update</li>
 *     <li>Delete</li>
 * </ul>
 */
public enum AuditAction {
    INSERT,
    UPDATE,
    DELETE;

    public static AuditAction fromString(String auditAction) {
        return Arrays.stream(AuditAction.values())
                .filter(action -> action.name().equals(auditAction))
                .findFirst()
                .orElse(null);
    }
}
