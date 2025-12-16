package com.dunhili.sasbank.auth.service;

import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.common.dto.ValidationError;
import com.dunhili.sasbank.common.enums.ValidationMessages;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for validating user login data.
 */
@Service
public class UserLoginValidationService {

    /**
     * Validates the given user login data and returns a list of validation errors if any are found.
     * @param userLogin User login data to validate.
     * @return List of validation errors, empty list if the user login data is valid.
     */
    public List<ValidationError> validateUserLogin(UserLogin userLogin) {
        List<ValidationError> errors = new ArrayList<>();

        if (StringUtils.isEmpty(userLogin.getUsername())) {
            errors.add(new ValidationError(ValidationMessages.LOGIN_NO_USERNAME_PROVIDED));
        }

        if (StringUtils.isEmpty(userLogin.getPassword())) {
            errors.add(new ValidationError(ValidationMessages.LOGIN_NO_PASSWORD_PROVIDED));
        }

        if (userLogin.getUserId() == null) {
            errors.add(new ValidationError(ValidationMessages.LOGIN_NO_USER_ID_PROVIDED));
        }

        if (CollectionUtils.isEmpty(userLogin.getRoles())) {
            errors.add(new ValidationError(ValidationMessages.LOGIN_ONE_ROLE_MUST_BE_PROVIDED));
        }

        return errors;
    }
}
