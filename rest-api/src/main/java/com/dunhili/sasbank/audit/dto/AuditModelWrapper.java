package com.dunhili.sasbank.audit.dto;

import com.dunhili.sasbank.audit.enums.AuditAction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuditModelWrapper<T> {
    private T data;
    private AuditAction auditAction;
}
