package com.dunhili.sasbank.user.dto;

import com.dunhili.sasbank.common.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * DTO representing a user's login credentials.
 */
@Getter
@Setter
public class UserLogin extends BaseDTO {
    private String username;
    private String passwordHash;
    private String passwordSalt;
    private String hashAlgorithm;
    private Date lastLoginDate;
    private int passwordAttempts;
}
