package com.dunhili.sasbank.user.service;

import com.dunhili.sasbank.common.dto.ValidationError;
import com.dunhili.sasbank.common.enums.ValidationMessages;
import com.dunhili.sasbank.common.exception.ApiServiceException;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.dto.UserAddress;
import com.dunhili.sasbank.user.dto.UserPhone;
import com.dunhili.sasbank.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

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
    @Transactional
    public UUID createOrUpdateUser(User user) {
        if (user.getId() == null) {
            return createUser(user);
        } else {
            return updateUser(user);
        }
    }

    /**
     * Creates a new user in the database.
     * @param user User data to create.
     */
    @Transactional
    protected UUID createUser(User user) {
        log.info("Creating new user");
        List<ValidationError> validationErrors = userValidationService.validateUser(user);
        if (!CollectionUtils.isEmpty(validationErrors)) {
            throw new ApiServiceException("INVALID_USER", "The following validation errors were found.", 400, validationErrors);
        }

        // create user first to get the FK
        UUID userId = userRepository.createUser(user);

        // next create the addresses (if there are any)
        if (!CollectionUtils.isEmpty(user.getAddresses())) {
            user.getAddresses().forEach(userAddress -> userAddress.setUserId(userId));
            userRepository.createAddresses(user.getAddresses());
        }

        // next create the phone numbers (if there are any)
        if (!CollectionUtils.isEmpty(user.getPhoneNumbers())) {
            user.getPhoneNumbers().forEach(userPhoneNumber -> userPhoneNumber.setUserId(userId));
            userRepository.createPhoneNumbers(user.getPhoneNumbers());
        }

        return userId;
    }

    /**
     * Updates an existing user in the database.
     * @param user User data to update.
     */
    @Transactional
    protected UUID updateUser(User user) {
        UUID userId = user.getId();
        log.info("Updating user: {}", userId);

        User existingUser = getUserById(userId);
        List<ValidationError> validationErrors = userValidationService.validateUser(user);
        if (!CollectionUtils.isEmpty(validationErrors)) {
            throw new ApiServiceException("INVALID_USER", "The following validation errors were found.", 400, validationErrors);
        }

        // if there are differences between the given and existing user, update the user
        if (!user.equals(existingUser)) {
            userRepository.updateUser(user);
        }

        updateAddresses(user, existingUser);
        updatePhoneNumbers(user, existingUser);

        return userId;
    }

    /**
     * Updates the addresses for the given user.
     * @param user User data to update.
     * @param existingUser Existing user data.
     */
    private void updateAddresses(User user, User existingUser) {
        UUID userId = existingUser.getId();
        if (user.getAddresses() == null) {
            user.setAddresses(new ArrayList<>());
        }
        if (existingUser.getAddresses() == null) {
            existingUser.setAddresses(new ArrayList<>());
        }

        List<UserAddress> requestAddresses = user.getAddresses().stream()
                .filter(address -> address.getUserId() != null && address.getUserId().equals(userId))
                .toList();

        // first compute a map of the existing addresses and request addresses so we can compare them more efficiently
        Map<UUID, UserAddress> userRequestAddresses = requestAddresses
                .stream()
                .filter(address -> address.getId() != null)
                .collect(Collectors.toMap(UserAddress::getId, address -> address));
        Map<UUID, UserAddress> existingAddresses = existingUser.getAddresses()
                .stream()
                .collect(Collectors.toMap(UserAddress::getId, address -> address));

        // addresses that are new either won't have an ID or won't be present in existing ones
        List<UserAddress> addressesToInsert = requestAddresses.stream()
                .filter(address -> address.getId() == null || !existingAddresses.containsKey(address.getId()))
                .toList();

        // addresses that are deleted will have an ID but won't be present in the request
        List<UUID> addressesToDelete = existingUser.getAddresses().stream()
                .map(UserAddress::getId)
                .filter(id -> !userRequestAddresses.containsKey(id))
                .toList();

        // addresses that are updated will have an ID and will be present in both the request and existing ones but
        // have at least one difference
        List<UserAddress> addressesToUpdate = requestAddresses.stream()
                .filter(address -> existingAddresses.containsKey(address.getId()))
                .filter(address -> !address.equals(existingAddresses.get(address.getId())))
                .toList();

        if (!addressesToInsert.isEmpty()) {
            userRepository.createAddresses(addressesToInsert);
        }
        if (!addressesToDelete.isEmpty()) {
            userRepository.deleteUserAddressById(userId, addressesToDelete);
        }
        if (!addressesToUpdate.isEmpty()) {
            userRepository.updateAddresses(addressesToUpdate);
        }
    }

    /**
     * Updates the phone numbers for the given user.
     * @param user User data to update.
     * @param existingUser Existing user data.
     */
    private void updatePhoneNumbers(User user, User existingUser) {
        UUID userId = existingUser.getId();
        if (user.getPhoneNumbers() == null) {
            user.setPhoneNumbers(new ArrayList<>());
        }
        if (existingUser.getPhoneNumbers() == null) {
            existingUser.setPhoneNumbers(new ArrayList<>());
        }

        List<UserPhone> requestPhoneNumbers = user.getPhoneNumbers().stream()
                .filter(phoneNumber -> phoneNumber.getUserId() != null && phoneNumber.getUserId().equals(userId))
                .toList();

        // first computer a map of the existing addresses and request addresses so we can compare them more efficiently
        Map<UUID, UserPhone> userRequestAddresses = requestPhoneNumbers
                .stream()
                .filter(phoneNumber -> phoneNumber.getId() != null)
                .collect(Collectors.toMap(UserPhone::getId, phoneNumber -> phoneNumber));
        Map<UUID, UserPhone> existingPhoneNumbers = existingUser.getPhoneNumbers()
                .stream()
                .collect(Collectors.toMap(UserPhone::getId, phoneNumber -> phoneNumber));

        // addresses that are new either won't have an ID or won't be present in existing ones
        List<UserPhone> phoneNumbersToInsert = requestPhoneNumbers.stream()
                .filter(phoneNumber -> phoneNumber.getId() == null || !existingPhoneNumbers.containsKey(phoneNumber.getId()))
                .toList();

        // addresses that are deleted will have an ID but won't be present in the request
        List<UUID> phoneNumbersToDelete = existingUser.getPhoneNumbers().stream()
                .map(UserPhone::getId)
                .filter(id -> !userRequestAddresses.containsKey(id))
                .toList();

        // addresses that are updated will have an ID and will be present in both the request and existing ones but
        // have at least one difference
        List<UserPhone> phoneNumbersToUpdate = requestPhoneNumbers.stream()
                .filter(phoneNumber -> existingPhoneNumbers.containsKey(phoneNumber.getId()))
                .filter(phoneNumber -> !phoneNumber.equals(existingPhoneNumbers.get(phoneNumber.getId())))
                .toList();

        if (!phoneNumbersToInsert.isEmpty()) {
            userRepository.createPhoneNumbers(phoneNumbersToInsert);
        }
        if (!phoneNumbersToDelete.isEmpty()) {
            userRepository.deleteUserPhoneNumber(userId, phoneNumbersToDelete);
        }
        if (!phoneNumbersToUpdate.isEmpty()) {
            userRepository.updatePhoneNumber(phoneNumbersToUpdate);
        }
    }

    /**
     * Deletes the user with the given ID.
     * @param userId ID of the user to delete.
     */
    public void deleteUser(UUID userId) {
        log.info("Deleting user with ID: {}", userId);
        if (!isUserExists(userId)) {
            throw new ApiServiceException(ValidationMessages.USER_NOT_FOUND, 404, userId.toString());
        }
        userRepository.deleteUserById(userId);
    }

    /**
     * Checks if a user with the given ID exists.
     * @param userId ID of the user to check
     * @return True if a user with the given ID exists, false otherwise.
     */
    public boolean isUserExists(UUID userId) {
        return userRepository.userExists(userId);
    }
}
