package com.dunhili.sasbank.user.service;

import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.dto.UserAddress;
import com.dunhili.sasbank.user.dto.UserPhone;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Service class for validating user data.
 */
@Service
@NoArgsConstructor
public class UserValidationService {

    private static final Pattern SSN_PATTERN = Pattern.compile("^(?!000|666|9\\d{2})\\d{3}-(?!00)\\d{2}-(?!0000)\\d{4}$");

    public void validateUser(User user) {

    }

    boolean isValidSsn(String ssn) {
        // only validate if there's actually an SSN
        if (StringUtils.isEmpty(ssn)) {
            return true;
        }

        return SSN_PATTERN.matcher(ssn).matches();
    }

    boolean hasOnePrimaryAddress(List<UserAddress> addresses) {
        // only validate if there's at least one address
        if (CollectionUtils.isEmpty(addresses)) {
            return true;
        }

        return addresses.stream().filter(UserAddress::isPrimary).count() == 1;
    }

    boolean hasOnePrimaryPhone(List<UserPhone> phoneNumbers) {
        // only validate if there's at least one phone number
        if (CollectionUtils.isEmpty(phoneNumbers)) {
            return true;
        }

        return phoneNumbers.stream().filter(UserPhone::isPrimary).count() == 1;
    }
}
