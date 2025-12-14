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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
            WHERE id = :userId;
        """;

        return queryOne(sql, Map.of("userId", userId), UserRowMapper.INSTANCE);
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
            WHERE user_id = :userId;
        """;

        return queryAll(sql, Map.of("userId", userId), UserAddressRowMapper.INSTANCE);
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
            WHERE user_id = :userId;
        """;

        return queryAll(sql, Map.of("userId", userId), UserPhoneMapper.INSTANCE);
    }

    /**
     * Deletes the user with the given ID.
     * @param userId ID of the user to delete.
     */
    public void deleteUserById(UUID userId) {
        String sql = """
            DELETE FROM public.user_app WHERE id = :userId;
        """;

        update(sql, Map.of("userId", userId));
    }
}
