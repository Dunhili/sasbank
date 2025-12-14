package com.dunhili.sasbank.user.dto;

import com.dunhili.sasbank.common.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO representing a user's address.
 */
@Getter
@Setter
public class UserAddress extends BaseDTO {
    private UUID userId;
    private String addressLine1;
    private String addressLine2;
    private String addressLine3;
    private String state;
    private String city;
    private String zipcode;
    private String country;
    private boolean isPrimary;
}
