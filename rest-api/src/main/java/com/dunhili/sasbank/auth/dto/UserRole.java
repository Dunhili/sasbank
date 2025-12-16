package com.dunhili.sasbank.auth.dto;

import com.dunhili.sasbank.auth.enums.Role;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UserRole {
    private UUID userId;
    private Role userRole;

    @JsonIgnore
    private Date createdAt;

    @JsonIgnore
    private String createdBy;

    @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
    public UserRole(String userRole) {
        this.userRole = Role.fromString(userRole);
    }
}
