package com.dunhili.sasbank.audit.repository;

import com.dunhili.sasbank.audit.dto.AuditModelWrapper;
import com.dunhili.sasbank.audit.mapper.UserAddressAuditModelRowMapper;
import com.dunhili.sasbank.audit.mapper.UserAuditModelRowMapper;
import com.dunhili.sasbank.audit.mapper.UserPhoneAuditModelRowMapper;
import com.dunhili.sasbank.common.BaseRepository;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.dto.UserAddress;
import com.dunhili.sasbank.user.dto.UserPhone;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Repository class for retrieving audit data.
 */
@Repository
public class AuditRepository extends BaseRepository {

    public AuditRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /**
     * Retrieves audit rows for a user with the given ID.
     * @param userId ID of the user to retrieve audit rows for.
     * @return List of audit rows for the given user ID.
     */
    public List<AuditModelWrapper<User>> findUserAuditRowsByUserId(UUID userId) {
        String sql = """
            SELECT *
            FROM audit.user_app_aud
            WHERE user_id = :userId
        """;

        return queryAll(sql, Map.of("userId", userId), UserAuditModelRowMapper.INSTANCE);
    }

    /**
     * Retrieves audit rows for a user address with the given ID.
     * @param addressId ID of the user address to retrieve audit rows for.
     * @return List of audit rows for the given user address ID.
     */
    public List<AuditModelWrapper<UserAddress>> findUserAddressAuditRowsByAddressId(UUID addressId) {
        String sql = """
            SELECT *
            FROM audit.user_address_aud
            WHERE user_address_id = :addressId
        """;

        return queryAll(sql, Map.of("addressId", addressId), UserAddressAuditModelRowMapper.INSTANCE);
    }

    /**
     * Retrieves audit rows for a user phone with the given ID.
     * @param phoneId ID of the user phone to retrieve audit rows for.
     * @return List of audit rows for the given user phone ID.
     */
    public List<AuditModelWrapper<UserPhone>> findUserPhoneAuditRowsByPhoneId(UUID phoneId) {
        String sql = """
            SELECT *
            FROM audit.user_phone_aud
            WHERE user_phone_id = :phoneId
        """;

        return queryAll(sql, Map.of("phoneId", phoneId), UserPhoneAuditModelRowMapper.INSTANCE);
    }
}
