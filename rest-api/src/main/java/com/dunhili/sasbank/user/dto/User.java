package com.dunhili.sasbank.user.dto;

import com.dunhili.sasbank.common.dto.BaseDTO;
import com.dunhili.sasbank.user.enums.AccountStatus;
import com.dunhili.sasbank.user.enums.Gender;
import com.dunhili.sasbank.user.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO representing a user.
 */
@Getter
@Setter
@EqualsAndHashCode(callSuper = true, exclude = {"phoneNumbers", "addresses"})
public class User extends BaseDTO {
    private String firstName;
    private String middleName;
    private String lastName;
    private Gender gender;
    private String emailAddress;
    private String ssn;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-yyyy")
    private LocalDate birthday;
    private AccountStatus status;
    private List<UserRole> roles = new ArrayList<>();
    private List<UserPhone> phoneNumbers = new ArrayList<>();
    private List<UserAddress> addresses = new ArrayList<>();
}
