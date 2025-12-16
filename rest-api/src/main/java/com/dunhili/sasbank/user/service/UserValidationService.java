package com.dunhili.sasbank.user.service;

import com.dunhili.sasbank.common.dto.ValidationError;
import com.dunhili.sasbank.common.enums.ValidationMessages;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.dto.UserAddress;
import com.dunhili.sasbank.user.dto.UserPhone;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Service class for validating user data.
 */
@Service
@AllArgsConstructor
public class UserValidationService {

    private static final Pattern SSN_PATTERN = Pattern.compile("^(?!000|666|9\\d{2})\\d{3}-(?!00)\\d{2}-(?!0000)\\d{4}$");

    /**
     * Validates the given user data.
     * @param user User data to validate
     * @return List of validation errors, empty list if the user data is valid.
     */
    public List<ValidationError> validateUser(User user) {
        List<ValidationError> errors = new ArrayList<>();

        if (!isValidSsn(user.getSsn())) {
            errors.add(new ValidationError(ValidationMessages.USER_INVALID_SSN));
        }
        if (!hasOnePrimaryAddress(user.getAddresses())) {
            errors.add(new ValidationError(ValidationMessages.USER_EXACTLY_ONE_PRIMARY_ADDRESS));
        }
        if (!hasOnePrimaryPhone(user.getPhoneNumbers())) {
            errors.add(new ValidationError(ValidationMessages.USER_EXACTLY_ONE_PRIMARY_PHONE));
        }
        return errors;
    }

    /**
     * Checks if the given SSN is valid.
     * @param ssn SSN to validate
     * @return True if the SSN is valid, false otherwise.
     */
    boolean isValidSsn(String ssn) {
        // only validate if there's actually an SSN
        if (StringUtils.isEmpty(ssn)) {
            return true;
        }

        return SSN_PATTERN.matcher(ssn).matches();
    }

    /**
     * Checks if the given list of addresses contains exactly one primary address.
     * @param addresses List of addresses to validate
     * @return True if the list contains exactly one primary address, false otherwise.
     */
    boolean hasOnePrimaryAddress(List<UserAddress> addresses) {
        // only validate if there's at least one address
        if (CollectionUtils.isEmpty(addresses)) {
            return true;
        }

        return addresses.stream().filter(UserAddress::isPrimary).count() == 1;
    }

    /**
     * Checks if the given list of phone numbers contains exactly one primary phone number.
     * @param phoneNumbers List of phone numbers to validate
     * @return True if the list contains exactly one primary phone number, false otherwise.
     */
    boolean hasOnePrimaryPhone(List<UserPhone> phoneNumbers) {
        // only validate if there's at least one phone number
        if (CollectionUtils.isEmpty(phoneNumbers)) {
            return true;
        }

        return phoneNumbers.stream().filter(UserPhone::isPrimary).count() == 1;
    }
}
