package com.dunhili.sasbank.user.service;

import com.dunhili.sasbank.common.enums.ValidationMessages;
import com.dunhili.sasbank.common.exception.ApiServiceException;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for user data.
 */
@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserValidationService userValidationService;
    private final UserRepository userRepository;

    /**
     * Returns the user with the given ID.
     * @param userId ID of the user to return.
     * @return User with the given ID.
     */
    public User getUserById(UUID userId) {
        log.info("Getting user with ID: {}", userId);
        User user = userRepository.findUserById(userId).orElse(null);
        if (user == null) {
            throw new ApiServiceException(ValidationMessages.USER_NOT_FOUND, 404, userId.toString());
        }

        user.setAddresses(userRepository.findAddressesByUserId(userId));
        user.setPhoneNumbers(userRepository.findPhoneNumbersByUserId(userId));
        return user;
    }

    /**
     * Creates or updates a user based on the given user data.
     * @param user User data to create or update.
     */
    public void createOrUpdateUser(User user) {
        if (user.getId() == null) {
            createUser(user);
        } else {
            updateUser(user);
        }
    }

    private void createUser(User user) {
        log.info("Creating new user");
    }

    private void updateUser(User user) {
        UUID userId = user.getId();
        log.info("Updating user: {}", userId);

        if (!userValidationService.isUserExists(userId)) {
            throw new ApiServiceException(ValidationMessages.USER_NOT_FOUND, 404, userId.toString());
        }


    }

    /**
     * Deletes the user with the given ID.
     * @param userId ID of the user to delete.
     */
    public void deleteUser(UUID userId) {
        log.info("Deleting user with ID: {}", userId);
        if (!userValidationService.isUserExists(userId)) {
            throw new ApiServiceException(ValidationMessages.USER_NOT_FOUND, 404, userId.toString());
        }
        userRepository.deleteUserById(userId);
    }
}
