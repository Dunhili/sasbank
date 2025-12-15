package com.dunhili.sasbank.user.repository;

import com.dunhili.sasbank.common.BaseRepository;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.dto.UserAddress;
import com.dunhili.sasbank.user.dto.UserPhone;
import com.dunhili.sasbank.user.mapper.UserAddressRowMapper;
import com.dunhili.sasbank.user.mapper.UserPhoneMapper;
import com.dunhili.sasbank.user.mapper.UserRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * Repository class for user data.
 */
@Repository
public class UserRepository extends BaseRepository {

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /**
     * Finds a user by their ID.
     * @param userId ID of the user to find.
     * @return Optional containing the user with the given ID or an empty Optional if no user is found.
     */
    public Optional<User> findUserById(UUID userId) {
        String sql = """
            SELECT *
            FROM public.user_app
            WHERE id = :userId
        """;

        return queryOne(sql, getUserParamMap(userId), UserRowMapper.INSTANCE);
    }

    /**
     * Finds all addresses for the user with the given ID.
     * @param userId ID of the user to find addresses for.
     * @return List of addresses for the user with the given ID.
     */
    public List<UserAddress> findAddressesByUserId(UUID userId) {
        String sql = """
            SELECT *
            FROM public.user_address
            WHERE user_id = :userId
        """;

        return queryAll(sql, getUserParamMap(userId), UserAddressRowMapper.INSTANCE);
    }

    /**
     * Finds all phone numbers for the user with the given ID.
     * @param userId ID of the user to find phone numbers for.
     * @return List of phone numbers for the user with the given ID.
     */
    public List<UserPhone> findPhoneNumbersByUserId(UUID userId) {
        String sql = """
            SELECT *
            FROM public.user_phone
            WHERE user_id = :userId
        """;

        return queryAll(sql, getUserParamMap(userId), UserPhoneMapper.INSTANCE);
    }

    /**
     * Creates a new user in the database.
     * @param user User data to create.
     * @return ID of the newly created user.
     */
    public UUID createUser(User user) {
        String sql = """
            INSERT INTO public.user_app (id, first_name, middle_name, last_name, gender, email_address, ssn, birthday, status, created_at, created_by, updated_at, updated_by)
            VALUES (:id, :firstName, :middleName, :lastName, :gender, :emailAddress, :ssn, :birthday, :status, :createdAt, :createdBy, :updatedAt, :updatedBy)
        """;

        UUID userId = UUID.randomUUID();
        Map<String, Object> params = getUserParams(user, userId, true);
        update(sql, params);
        return userId;
    }

    public void createAddresses(List<UserAddress> addresses) {
        String sql = """
            INSERT INTO public.user_address(user_id, address_line_1, address_line_2, address_line_3, state, city, zipcode, country, is_primary, created_at, created_by, updated_at, updated_by)
            VALUES (:userId, :addressLine1, :addressLine2, :addressLine3, :state, :city, :zipcode, :country, :isPrimary, :createdAt, :createdBy, :updatedAt, :updatedBy)
        """;

        List<Map<String, Object>> batch = addresses.stream()
                .map(address -> getAddressParams(address, true))
                .toList();

        batchUpdate(sql, batch);
    }

    public void createPhoneNumbers(List<UserPhone> phoneNumbers) {
        String sql = """
            INSERT INTO public.user_phone(user_id, phone_number, phone_type, is_primary, created_at, created_by, updated_at, updated_by)
            VALUES (:userId, :phoneNumber, :phoneType, :isPrimary, :createdAt, :createdBy, :updatedAt, :updatedBy)
        """;

        List<Map<String, Object>> batch = phoneNumbers.stream()
                .map(phoneNumber -> getPhoneNumberParams(phoneNumber, true))
                .toList();

        batchUpdate(sql, batch);
    }

    /**
     * Updates the user with the given ID.
     * @param user User data to update.
     */
    public void updateUser(User user) {
        String sql = """
            UPDATE public.user_app
            SET first_name = :firstName,
                middle_name = :middleName,
                last_name = :lastName,
                gender = :gender,
                email_address = :emailAddress,
                ssn = :ssn,
                birthday = :birthday,
                status = :status,
                updated_at = :updatedAt,
                updated_by = :updatedBy
            WHERE id = :id
        """;

        Map<String, Object> params = getUserParams(user, user.getId(), false);
        update(sql, params);
    }

    /**
     * Updates the address with the given ID.
     * @param addresses Address data to update.
     */
    public void updateAddresses(List<UserAddress> addresses) {
        String sql = """
            UPDATE public.user_address
            SET address_line_1 = :addressLine1,
                address_line_2 = :addressLine2,
                address_line_3 = :addressLine3,
                state = :state,
                city = :city,
                zipcode = :zipcode,
                country = :country,
                is_primary = :isPrimary,
                updated_at = :updatedAt,
                updated_by = :updatedBy
            WHERE id = :id
            AND user_id = :userId
        """;

        List<Map<String, Object>> batch = addresses.stream()
                .map(address -> getAddressParams(address, false))
                .toList();

        batchUpdate(sql, batch);
    }

    /**
     * Updates the phone number with the given ID.
     * @param phoneNumbers Phone number data to update.
     */
    public void updatePhoneNumber(List<UserPhone> phoneNumbers) {
        String sql = """
            UPDATE public.user_phone
            SET phone_number = :phoneNumber,
                phone_type = :phoneType,
                is_primary = :isPrimary,
                updated_at = :updatedAt,
                updated_by = :updatedBy
            WHERE id = :id
            AND user_id = :userId
        """;

        List<Map<String, Object>> batch = phoneNumbers.stream()
                .map(phoneNumber -> getPhoneNumberParams(phoneNumber, false))
                .toList();

        batchUpdate(sql, batch);
    }

    /**
     * Deletes the user with the given ID.
     * @param userId ID of the user to delete.
     */
    public void deleteUserById(UUID userId) {
        String sql = """
            DELETE FROM public.user_app WHERE id = :userId
        """;

        delete(sql, getUserParamMap(userId));
    }

    /**
     * Deletes the user address with the given ID.
     * @param userId ID of the user to delete the address for.
     * @param addressIds List of address IDs to delete.
     */
    public void deleteUserAddressById(UUID userId, List<UUID> addressIds) {
        String sql = """
            DELETE FROM public.user_address WHERE id IN (:addressIds) AND user_id = :userId
        """;

        delete(sql, Map.of("addressIds", addressIds, "userId", userId));
    }

    /**
     * Deletes the user phone number with the given ID.
     * @param userId ID of the user to delete the phone number for.
     * @param phoneNumberIds List of phone number IDs to delete.
     */
    public void deleteUserPhoneNumber(UUID userId, List<UUID> phoneNumberIds) {
        String sql = """
            DELETE FROM public.user_phone WHERE id IN (:phoneNumberIds) AND user_id = :userId
        """;

        delete(sql, Map.of("phoneNumberIds", phoneNumberIds, "userId", userId));
    }

    /**
     * Checks if a user with the given ID exists in the database.
     * @param userId ID of the user to check.
     * @return True if a user with the given ID exists, false otherwise.
     */
    public boolean userExists(UUID userId) {
        String sql = """
            SELECT EXISTS(SELECT 1 FROM users WHERE id = :userId)
        """;

        return exists(sql, getUserParamMap(userId));
    }

    /**
     * Creates a parameter map for the given user ID.
     * @param userId ID of the user to create a parameter map for.
     * @return Parameter map for the given user ID.
     */
    private Map<String, UUID> getUserParamMap(UUID userId) {
        return Map.of("userId", userId);
    }

    /**
     * Creates a parameter map for the given user.
     * @param user User to create a parameter map for.
     * @param userId new user ID of the user to create a parameter map for.
     * @param isCreate True if the user is being created, false otherwise.
     * @return Parameter map for the given user.
     */
    private Map<String, Object> getUserParams(User user, UUID userId, boolean isCreate) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", userId);
        params.put("firstName", user.getFirstName());
        params.put("middleName", user.getMiddleName());
        params.put("lastName", user.getLastName());
        params.put("gender", user.getGender().getCode());
        params.put("emailAddress", user.getEmailAddress());
        params.put("ssn", user.getSsn());
        params.put("birthday", user.getBirthday());
        params.put("status", user.getStatus().name());
        addAuditParams(params, isCreate);

        return params;
    }

    /**
     * Creates a parameter map for the given address.
     * @param address Address to create a parameter map for.
     * @param isCreate True if the user is being created, false otherwise.
     * @return Parameter map for the given address.
     */
    private Map<String, Object> getAddressParams(UserAddress address, boolean isCreate) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", address.getId());
        params.put("userId", address.getUserId());
        params.put("addressLine1", address.getAddressLine1());
        params.put("addressLine2", address.getAddressLine2());
        params.put("addressLine3", address.getAddressLine3());
        params.put("state", address.getState());
        params.put("city", address.getCity());
        params.put("zipcode", address.getZipcode());
        params.put("country", address.getCountry());
        params.put("isPrimary", address.isPrimary());
        addAuditParams(params, isCreate);

        return params;
    }

    /**
     * Creates a parameter map for the given phone number.
     * @param phoneNumber Phone number to create a parameter map for.
     * @param isCreate True if the user is being created, false otherwise.
     * @return Parameter map for the given phone number.
     */
    private Map<String, Object> getPhoneNumberParams(UserPhone phoneNumber, boolean isCreate) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", phoneNumber.getId());
        params.put("userId", phoneNumber.getUserId());
        params.put("phoneNumber", phoneNumber.getPhoneNumber());
        params.put("phoneType", phoneNumber.getPhoneType().name());
        params.put("isPrimary", phoneNumber.isPrimary());
        addAuditParams(params, isCreate);

        return params;
    }

    /**
     * Adds audit parameters to the given parameter map.
     * @param params Parameter map to add audit parameters to.
     * @param isCreate True if the parameters are being added for a create operation, false otherwise.
     */
    private void addAuditParams(Map<String, Object> params, boolean isCreate) {
        Date now = new Date();
        if (isCreate) {
            params.put("createdAt", now);
            params.put("createdBy", "bbowden89");
        }

        params.put("updatedAt", now);
        params.put("updatedBy", "bbowden89");
    }
}
