package com.dunhili.sasbank.audit.dto;

import com.dunhili.sasbank.audit.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * Wrapper class for audit models. Contains both the data that was audited along with the action taken
 * for each row.
 * @param <T> Type of data being audited.
 */
@Getter
@Setter
@AllArgsConstructor
public class AuditModelWrapper<T> {
    private UUID id;
    private AuditAction auditAction;
    private T data;
}
