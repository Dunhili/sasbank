package com.dunhili.sasbank.auth.dto;

import com.dunhili.sasbank.common.dto.BaseDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

/**
 * DTO representing a user's login credentials.
 */
@Getter
@Setter
public class UserLogin extends BaseDTO {
    private UUID userId;
    private String username;
    private String passwordHash;
    private String passwordSalt;
    private String hashAlgorithm;
    private Date lastLoginDate;
    private int passwordAttempts;
}
