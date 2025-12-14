package com.dunhili.sasbank.user.dto;

import com.dunhili.sasbank.common.dto.BaseDTO;
import com.dunhili.sasbank.user.enums.PhoneType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPhone extends BaseDTO {
    private String phoneNumber;
    private PhoneType phoneType;
    private boolean isPrimary;
}
