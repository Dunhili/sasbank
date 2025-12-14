package com.dunhili.sasbank.user.repository;

import com.dunhili.sasbank.common.BaseRepository;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.mapper.UserRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository class for user data.
 */
@Repository
public class UserRepository extends BaseRepository {

    private final UserRowMapper userRowMapper;

    public UserRepository(NamedParameterJdbcTemplate jdbcTemplate, UserRowMapper userRowMapper) {
        super(jdbcTemplate);
        this.userRowMapper = userRowMapper;
    }

    public Optional<User> findUserById(UUID userId) {
        String sql = """
            SELECT *
            FROM public.user_app
            WHERE id = :userId
        """;

        return queryOne(sql, Map.of("userId", userId), userRowMapper);
    }

}
