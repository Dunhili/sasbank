package com.dunhili.sasbank.user.service;

import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserValidationService userValidationService;
    private final UserRepository userRepository;

    public User getUserById(UUID userId) {
        User user = new User();
        user.setId(userId);
        return user;
    }
}
