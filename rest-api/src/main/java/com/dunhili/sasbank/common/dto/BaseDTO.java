package com.dunhili.sasbank.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public abstract class BaseDTO {
    private UUID id;
    private Date createdAt;
    private String createdBy;
    private Date updatedAt;
    private String updatedBy;
}
