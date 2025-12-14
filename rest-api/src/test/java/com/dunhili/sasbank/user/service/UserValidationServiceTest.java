package com.dunhili.sasbank.user.service;

import com.dunhili.sasbank.user.dto.UserAddress;
import com.dunhili.sasbank.user.dto.UserPhone;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the {@link UserValidationService} class.
 */
@ExtendWith(MockitoExtension.class)
public class UserValidationServiceTest {

    @InjectMocks
    @Spy
    private UserValidationService userValidationService;

    /**
     * Tests the {@link UserValidationService#isValidSsn(String)} method.
     */
    @Test
    public void validateSsnTest() {
        assertTrue(userValidationService.isValidSsn(null));
        assertTrue(userValidationService.isValidSsn(""));
        assertTrue(userValidationService.isValidSsn("123-45-6789"));
        assertFalse(userValidationService.isValidSsn("123"));
        assertFalse(userValidationService.isValidSsn("123456789"));
    }

    /**
     * Tests the {@link UserValidationService#hasOnePrimaryAddress(List)} method.
     */
    @Test
    public void validatePrimaryAddressTest() {
        // empty list
        assertTrue(userValidationService.hasOnePrimaryAddress(null));

        List<UserAddress> addresses = new ArrayList<>();
        assertTrue(userValidationService.hasOnePrimaryAddress(addresses));

        // one address that is not primary
        UserAddress address1 = new UserAddress();
        addresses.add(address1);
        assertFalse(userValidationService.hasOnePrimaryAddress(addresses));

        // one primary and one non-primary
        UserAddress address2 = new UserAddress();
        address2.setPrimary(true);
        addresses.add(address2);
        assertTrue(userValidationService.hasOnePrimaryAddress(addresses));

        // two primary addresses
        address1.setPrimary(true);
        assertFalse(userValidationService.hasOnePrimaryAddress(addresses));
    }

    /**
     * Tests the {@link UserValidationService#hasOnePrimaryPhone(List)} method.
     */
    @Test
    public void validatePrimaryPhoneTest() {
        // empty list
        assertTrue(userValidationService.hasOnePrimaryPhone(null));

        List<UserPhone> phoneNumbers = new ArrayList<>();
        assertTrue(userValidationService.hasOnePrimaryPhone(phoneNumbers));

        // one address that is not primary
        UserPhone phoneNumber1 = new UserPhone();
        phoneNumbers.add(phoneNumber1);
        assertFalse(userValidationService.hasOnePrimaryPhone(phoneNumbers));

        // one primary and one non-primary
        UserPhone phoneNumber2 = new UserPhone();
        phoneNumber2.setPrimary(true);
        phoneNumbers.add(phoneNumber2);
        assertTrue(userValidationService.hasOnePrimaryPhone(phoneNumbers));

        // two primary addresses
        phoneNumber1.setPrimary(true);
        assertFalse(userValidationService.hasOnePrimaryPhone(phoneNumbers));
    }
}
