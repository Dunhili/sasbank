package com.dunhili.sasbank.user.dto;

import com.dunhili.sasbank.common.dto.BaseDTO;
import com.dunhili.sasbank.user.enums.AccountStatus;
import com.dunhili.sasbank.user.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * DTO representing a user.
 */
@Getter
@Setter
public class User extends BaseDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private String emailAddress;
    private String ssn;
    private Date birthdate;
    private AccountStatus status;
    private UserLogin login;
    private List<UserPhone> phoneNumbers;
    private List<UserAddress> addresses;
}
