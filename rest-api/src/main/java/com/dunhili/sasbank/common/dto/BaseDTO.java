package com.dunhili.sasbank.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * Base DTO class that all other DTOs use. Contains data in all tables (i.e., IDs and create/update data).
 */
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public abstract class BaseDTO {
    private UUID id;

    @JsonIgnore
    private Date createdAt;

    @JsonIgnore
    private String createdBy;

    @JsonIgnore
    private Date updatedAt;

    @JsonIgnore
    private String updatedBy;
}
