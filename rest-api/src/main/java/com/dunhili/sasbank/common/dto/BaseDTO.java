package com.dunhili.sasbank.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Base DTO class that all other DTOs use. Contains data in all tables (i.e., IDs and create/update data).
 */
@Getter
@Setter
public abstract class BaseDTO {
    private UUID id;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
