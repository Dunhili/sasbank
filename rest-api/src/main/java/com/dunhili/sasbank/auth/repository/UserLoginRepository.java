package com.dunhili.sasbank.auth.repository;

import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.auth.dto.UserRole;
import com.dunhili.sasbank.auth.mapper.UserLoginRowMapper;
import com.dunhili.sasbank.auth.mapper.UserRoleRowMapper;
import com.dunhili.sasbank.auth.utils.AuthUtils;
import com.dunhili.sasbank.common.BaseRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

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
     * Retrieves all roles assigned to the user with the given ID.
     * @param userId ID of the user to retrieve roles for.
     * @return List of roles assigned to the user with the given ID.
     */
    public List<UserRole> findUserRolesByUserId(UUID userId) {
        String sql = """
            SELECT * FROM public.user_role WHERE user_id = :userId
        """;

        return queryAll(sql, getUserParamMap(userId), UserRoleRowMapper.INSTANCE);
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
     * Updates the user login record with the given ID.
     * @param userLogin User login data to update.
     */
    public void updateLogin(UserLogin userLogin) {
        String sql = """
            UPDATE public.user_login
            SET username = :username,
                password = :password,
                hash_algorithm = :hashAlgorithm,
                last_login_date = :lastLoginDate,
                password_attempts = :passwordAttempts,
                updated_at = :updatedAt,
                updated_by = :updatedBy
            WHERE user_id = :userId
        """;

        update(sql, getLoginParams(userLogin, false));
    }

    /**
     * Creates new roles for the user with the given ID.
     * @param roles List of roles to create.
     */
    public void createRoles(List<UserRole> roles) {
        String sql = """
            INSERT INTO public.user_role(user_id, user_role, created_at, created_by)
            VALUES (:userId, :userRole, :createdAt, :createdBy)
        """;

        Date now = new Date();
        String createdBy = AuthUtils.getCurrentUser();

        List<Map<String, Object>> batch = roles.stream()
                .map(role -> getUserRoleParams(role, now, createdBy))
                .toList();

        batchUpdate(sql, batch);
    }

    /**
     * Deletes the user roles with the given roles.
     * @param roles List of roles to delete.
     */
    public void deleteUserRoles(List<UserRole> roles) {
        String sql = """
            DELETE FROM public.user_role WHERE user_role IN (:roles) AND user_id = :userId
        """;

        UUID userId = roles.getFirst().getUserId();
        List<String> roleStrings = roles.stream().map(role -> role.getUserRole().name()).toList();
        delete(sql, Map.of("roles", roleStrings, "userId", userId));
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
        params.put("lastLoginDate", userLogin.getLastLoginDate());
        params.put("passwordAttempts", userLogin.getPasswordAttempts());
        addAuditParams(params, isCreate);

        return params;
    }

    /**
     * Creates a parameter map for the given role.
     * @param userRole role to add
     * @param now current date
     * @param createdBy user creating the role
     * @return parameter map for the given role
     */
    private Map<String, Object> getUserRoleParams(UserRole userRole, Date now, String createdBy) {
        Map<String, Object> params = new HashMap<>();
        params.put("userRole", userRole.getUserRole().name());
        params.put("userId", userRole.getUserId());
        params.put("createdAt", now);
        params.put("createdBy", createdBy);
        return params;
    }
}
