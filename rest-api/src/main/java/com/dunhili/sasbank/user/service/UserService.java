package com.dunhili.sasbank.user.service;

import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service class for user data.
 */
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserValidationService userValidationService;
    private final UserRepository userRepository;

    /**
     * Returns the user with the given ID.
     * @param userId ID of the user to return.
     * @return User with the given ID.
     */
    public User getUserById(UUID userId) {
        return userRepository.findUserById(userId).orElse(null);
    }

    /**
     * Creates or updates a user based on the given user data.
     * @param user User data to create or update.
     */
    public void createOrUpdateUser(User user) {

    }

    /**
     * Deletes the user with the given ID.
     * @param userId ID of the user to delete.
     */
    public void deleteUser(UUID userId) {

    }
}
