package com.dunhili.sasbank.auth.service;

import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.auth.repository.UserLoginRepository;
import com.dunhili.sasbank.common.enums.ValidationMessages;
import com.dunhili.sasbank.common.exception.ApiServiceException;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service handling user login and credentials.
 */
@Service
@AllArgsConstructor
public class UserLoginService {

    private final UserLoginRepository userLoginRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Returns the login data for the user with the given username.
     * @param username Username of the user to retrieve login data for.
     * @return Login data for the user with the given username or null if no such user exists.
     */
    public UserLogin getLoginByUsername(String username) {
        return userLoginRepository.findByUsername(username).orElse(null);
    }

    /**
     * Creates a new login record for the given user.
     * @param login User login data to create.
     */
    public void createLogin(UserLogin login) {
        if (StringUtils.isEmpty(login.getUsername())) {
            throw new ApiServiceException(ValidationMessages.LOGIN_NO_USERNAME_PROVIDED, 400);
        } else if (StringUtils.isEmpty(login.getPassword())) {
            throw new ApiServiceException(ValidationMessages.LOGIN_NO_PASSWORD_PROVIDED, 400);
        } else if (login.getUserId() == null) {
            throw new ApiServiceException(ValidationMessages.LOGIN_NO_USER_ID_PROVIDED, 400);
        }

        login.setPassword(passwordEncoder.encode(login.getPassword()));
        login.setHashAlgorithm("BCrypt");
        userLoginRepository.createLogin(login);
    }
}
