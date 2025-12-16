package com.dunhili.sasbank.auth.service;

import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.auth.dto.UserRole;
import com.dunhili.sasbank.auth.enums.Role;
import com.dunhili.sasbank.auth.repository.UserLoginRepository;
import com.dunhili.sasbank.common.dto.ValidationError;
import com.dunhili.sasbank.common.enums.ValidationMessages;
import com.dunhili.sasbank.common.exception.ApiServiceException;
import com.dunhili.sasbank.user.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Service handling user login and credentials.
 */
@Service
@AllArgsConstructor
public class UserLoginService {

    private final UserLoginValidationService userLoginValidationService;
    private final UserLoginRepository userLoginRepository;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Returns the login data for the user with the given username.
     * @param username Username of the user to retrieve login data for.
     * @return Login data for the user with the given username or null if no such user exists.
     */
    public UserLogin getLoginByUsername(String username) {
        Optional<UserLogin> userOptional = userLoginRepository.findByUsername(username);
        if (userOptional.isPresent()) {
            UserLogin user = userOptional.get();
            user.setRoles(userLoginRepository.findUserRolesByUserId(user.getUserId()));
            return user;
        }

        return null;
    }

    /**
     * Returns a list of roles assigned to the given user.
     * @param userId ID of the user to retrieve roles for.
     * @return List of roles assigned to the given user.
     */
    public List<UserRole> getUserRoles(UUID userId) {
        return userLoginRepository.findUserRolesByUserId(userId);
    }

    /**
     * Creates or updates a login record for the given user.
     * @param user User data to create or update.
     */
    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void createOrUpdateUserLogin(UserLogin user) {
        List<ValidationError> errors = userLoginValidationService.validateUserLogin(user);
        if (user.getUserId() != null && !userService.isUserExists(user.getUserId())) {
            errors.add(new ValidationError(ValidationMessages.USER_NOT_FOUND, user.getUserId().toString()));
        }
        if (!errors.isEmpty()) {
            throw new ApiServiceException("INVALID_USER_LOGIN", "The following validation errors were found.", 400, errors);
        }

        UserLogin existingUser = getLoginByUsername(user.getUsername());
        user.getRoles().forEach(role -> role.setUserId(user.getUserId()));
        if (existingUser == null) {
            createLogin(user);
        } else {
            updateLogin(user, existingUser);
        }
    }

    /**
     * Creates a new login record for the given user.
     * @param login User login data to create.
     */
    @Transactional
    protected void createLogin(UserLogin login) {
        login.setPassword(passwordEncoder.encode(login.getPassword()));
        login.setHashAlgorithm("BCrypt");
        userLoginRepository.createLogin(login);
        userLoginRepository.createRoles(login.getRoles());
    }

    /**
     * Updates the login record for the given user.
     * @param user User data to update.
     * @param existingUser Existing user data.
     */
    @Transactional
    protected void updateLogin(UserLogin user, UserLogin existingUser) {
        if (user.getHashAlgorithm() == null) {
            user.setHashAlgorithm(existingUser.getHashAlgorithm());
        }
        if (user.getLastLoginDate() == null) {
            user.setLastLoginDate(existingUser.getLastLoginDate());
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (!user.equals(existingUser)) {
            userLoginRepository.updateLogin(user);
        }

        updateRoles(user, existingUser);
    }

    /**
     * Updates the roles for the user.
     * @param user User data to update.
     * @param existingUser Existing user data.
     */
    @Transactional
    protected void updateRoles(UserLogin user, UserLogin existingUser) {
        if (user.getRoles() == null) {
            user.setRoles(new ArrayList<>());
        }
        if (existingUser.getRoles() == null) {
            existingUser.setRoles(new ArrayList<>());
        }

        Set<Role> givenRoles = new HashSet<>(user.getRoles().stream().map(UserRole::getUserRole).toList());
        Set<Role> existingRoles = new HashSet<>(existingUser.getRoles().stream().map(UserRole::getUserRole).toList());

        List<UserRole> rolesToAdd = user.getRoles()
                .stream()
                .filter(role -> !existingRoles.contains(role.getUserRole()))
                .toList();

        List<UserRole> rolesToDelete = existingUser.getRoles()
                .stream()
                .filter(role -> !givenRoles.contains(role.getUserRole()))
                .toList();

        if (!rolesToAdd.isEmpty()) {
            userLoginRepository.createRoles(rolesToAdd);
        }
        if (!rolesToDelete.isEmpty()) {
            userLoginRepository.deleteUserRoles(rolesToDelete);
        }
    }

    /**
     * Returns true if the user with the given ID has the given role, false otherwise.
     * @param userId ID of the user to check.
     * @param role Role to check for.
     * @return True if the user has the given role, false otherwise.
     */
    public boolean userHasRole(UUID userId, Role role) {
        return userLoginRepository.findUserRolesByUserId(userId)
                .stream()
                .anyMatch(userRole -> userRole.getUserRole().equals(role));
    }
}
