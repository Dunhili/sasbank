package com.dunhili.sasbank.user.dto;

import com.dunhili.sasbank.common.dto.BaseDTO;
import com.dunhili.sasbank.user.enums.PhoneType;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

/**
 * DTO representing a user's phone number.
 */
@Getter
@Setter
public class UserPhone extends BaseDTO {
    private UUID userId;
    private String phoneNumber;
    private PhoneType phoneType;
    private boolean isPrimary;
}
