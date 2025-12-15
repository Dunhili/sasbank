package com.dunhili.sasbank.auth.repository;

import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.auth.mapper.UserLoginRowMapper;
import com.dunhili.sasbank.common.BaseRepository;
import com.dunhili.sasbank.user.dto.UserPhone;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Repository class for user login data.
 */
@Repository
public class UserLoginRepository extends BaseRepository {

    public UserLoginRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        super(jdbcTemplate);
    }

    /**
     * Finds a user login record by username.
     * @param username Username of the user to find.
     * @return Optional containing the user login record with the given username or an empty Optional if no record is found.
     */
    public Optional<UserLogin> findByUsername(String username) {
        String sql = """
            SELECT *
            FROM public.user_login
            WHERE username = :username
        """;

        return queryOne(sql, Map.of("username", username), UserLoginRowMapper.INSTANCE);
    }

    /**
     * Creates a new user login record.
     * @param userLogin User login data to create.
     */
    public void createLogin(UserLogin userLogin) {
        String sql = """
            INSERT INTO public.user_login(user_id, username, password, hash_algorithm, created_at, created_by, updated_at, updated_by)
            VALUES (:userId, :username, :password, :hashAlgorithm, :createdAt, :createdBy, :updatedAt, :updatedBy)
        """;

        update(sql, getLoginParams(userLogin, true));
    }

    /**
     * Creates a parameter map for the given phone number.
     * @param userLogin User login data to create a parameter map for.
     * @param isCreate True if the user is being created, false otherwise.
     * @return Parameter map for the given phone number.
     */
    private Map<String, Object> getLoginParams(UserLogin userLogin, boolean isCreate) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", userLogin.getId());
        params.put("userId", userLogin.getUserId());
        params.put("username", userLogin.getUsername());
        params.put("password", userLogin.getPassword());
        params.put("hashAlgorithm", userLogin.getHashAlgorithm());
        addAuditParams(params, isCreate);

        return params;
    }
}
