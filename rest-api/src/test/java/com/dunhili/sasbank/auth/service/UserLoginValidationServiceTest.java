package com.dunhili.sasbank.auth.service;

import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.auth.dto.UserRole;
import com.dunhili.sasbank.auth.enums.Role;
import com.dunhili.sasbank.common.dto.ValidationError;
import com.dunhili.sasbank.common.enums.ValidationMessages;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link UserLoginValidationService} class.
 */
@ExtendWith(MockitoExtension.class)
public class UserLoginValidationServiceTest {

    @InjectMocks
    @Spy
    private UserLoginValidationService userLoginValidationService;

    /**
     * Tests the {@link UserLoginValidationService#validateUserLogin(UserLogin)} method for the username.
     */
    @Test
    public void validateUserUsernameTest() {
        UserLogin user = new UserLogin();
        user.setPassword("password");
        user.setUserId(UUID.randomUUID());

        UserRole role = new UserRole();
        role.setUserRole(Role.ADMIN);
        role.setUserId(user.getUserId());
        user.setRoles(List.of(role));

        List<ValidationError> errors = userLoginValidationService.validateUserLogin(user);
        assertFalse(errors.isEmpty());
        assertEquals(ValidationMessages.LOGIN_NO_USERNAME_PROVIDED.getMessage(), errors.getFirst().getErrorMessage());

        user.setUsername("test");
        errors = userLoginValidationService.validateUserLogin(user);
        assertTrue(errors.isEmpty());
    }

    /**
     * Tests the {@link UserLoginValidationService#validateUserLogin(UserLogin)} method for the password.
     */
    @Test
    public void validateUserPasswordTest() {
        UserLogin user = new UserLogin();
        user.setUsername("test");
        user.setUserId(UUID.randomUUID());

        UserRole role = new UserRole();
        role.setUserRole(Role.ADMIN);
        role.setUserId(user.getUserId());
        user.setRoles(List.of(role));

        List<ValidationError> errors = userLoginValidationService.validateUserLogin(user);
        assertFalse(errors.isEmpty());
        assertEquals(ValidationMessages.LOGIN_NO_PASSWORD_PROVIDED.getMessage(), errors.getFirst().getErrorMessage());

        user.setPassword("password");
        errors = userLoginValidationService.validateUserLogin(user);
        assertTrue(errors.isEmpty());
    }

    /**
     * Tests the {@link UserLoginValidationService#validateUserLogin(UserLogin)} method for the user ID.
     */
    @Test
    public void validateUserUserIdTest() {
        UserLogin user = new UserLogin();
        user.setUsername("test");
        user.setPassword("password");
        UUID userId = UUID.randomUUID();

        UserRole role = new UserRole();
        role.setUserRole(Role.ADMIN);
        role.setUserId(userId);
        user.setRoles(List.of(role));

        List<ValidationError> errors = userLoginValidationService.validateUserLogin(user);
        assertFalse(errors.isEmpty());
        assertEquals(ValidationMessages.LOGIN_NO_USER_ID_PROVIDED.getMessage(), errors.getFirst().getErrorMessage());

        user.setUserId(userId);
        errors = userLoginValidationService.validateUserLogin(user);
        assertTrue(errors.isEmpty());
    }

    /**
     * Tests the {@link UserLoginValidationService#validateUserLogin(UserLogin)} method for the roles.
     */
    @Test
    public void validateUserRolesTest() {
        UserLogin user = new UserLogin();
        user.setUsername("test");
        user.setPassword("password");
        user.setUserId(UUID.randomUUID());
        user.setRoles(new ArrayList<>());

        List<ValidationError> errors = userLoginValidationService.validateUserLogin(user);
        assertFalse(errors.isEmpty());
        assertEquals(ValidationMessages.LOGIN_ONE_ROLE_MUST_BE_PROVIDED.getMessage(), errors.getFirst().getErrorMessage());

        UserRole role = new UserRole();
        role.setUserRole(Role.ADMIN);
        role.setUserId(user.getUserId());

        user.getRoles().add(role);
        errors = userLoginValidationService.validateUserLogin(user);
        assertTrue(errors.isEmpty());
    }
}
