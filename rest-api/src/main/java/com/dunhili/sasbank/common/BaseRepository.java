package com.dunhili.sasbank.common;


import com.dunhili.sasbank.auth.utils.AuthUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

/**
 * Base repository class that all repositories should extend.
 */
@RequiredArgsConstructor
public abstract class BaseRepository {

    protected final NamedParameterJdbcTemplate jdbcTemplate;

    /**
     * Queries the database for a single row and returns it as an Optional.
     * @param <T> Type of object to map the query result to.
     * @param sql SQL query to execute.
     * @param params Parameters to use for the query.
     * @param rowMapper Row mapper to use for mapping the query result to a Java object.
     * @return Optional containing the query result or an empty Optional if no result was found.
     */
    protected <T> Optional<T> queryOne(String sql, Map<String, ?> params, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, params, rowMapper).stream().findFirst();
    }

    /**
     * Queries the database for a single row and returns it as an Optional.
     * @param <T> Type of object to map the query result to.
     * @param sql SQL query to execute.
     * @param params Parameters to use for the query.
     * @param rowMapper Row mapper to use for mapping the query result to a Java object.
     * @return Optional containing the query result or an empty Optional if no result was found.
     */
    protected <T> List<T> queryAll(String sql, Map<String, ?> params, RowMapper<T> rowMapper) {
        return jdbcTemplate.query(sql, params, rowMapper);
    }

    /**
     * Updates the database using the given SQL query and parameters.
     * @param sql SQL query to execute.
     * @param params Parameters to use for the query.
     */
    protected void update(String sql, Map<String, ?> params) {
        jdbcTemplate.update(sql, params);
    }

    /**
     * Updates the database in a batch using the given SQL query and batch of parameters.
     * @param sql SQL query to execute.
     * @param batch Batch of parameters to use for the query.
     */
    protected void batchUpdate(String sql, List<Map<String, Object>> batch) {
        jdbcTemplate.batchUpdate(sql, batch.toArray(new Map[0]));
    }

    /**
     * Deletes from the database using the given SQL query and parameters.
     * @param sql SQL query to execute.
     * @param params Parameters to use for the query.
     */
    protected void delete(String sql, Map<String, ?> params) {
        jdbcTemplate.update(sql, params);
    }

    /**
     * Checks if a row exists in the database using the given SQL query and parameters.
     * @param sql SQL query to execute.
     * @param params Parameters to use for the query.
     * @return True if a row exists, false otherwise.
     */
    protected boolean exists(String sql, Map<String, ?> params) {
        Boolean result = jdbcTemplate.queryForObject(sql, params, Boolean.class);
        return result != null && result;
    }

    /**
     * Creates a parameter map for the given user ID.
     * @param userId ID of the user to create a parameter map for.
     * @return Parameter map for the given user ID.
     */
    protected Map<String, UUID> getUserParamMap(UUID userId) {
        return Map.of("userId", userId);
    }

    /**
     * Adds audit parameters to the given parameter map.
     * @param params Parameter map to add audit parameters to.
     * @param isCreate True if the parameters are being added for a create operation, false otherwise.
     */
    protected void addAuditParams(Map<String, Object> params, boolean isCreate) {
        String userName = AuthUtils.getCurrentUser();
        Date now = new Date();
        if (isCreate) {
            params.put("createdAt", now);
            params.put("createdBy", userName);
        }

        params.put("updatedAt", now);
        params.put("updatedBy", userName);
    }
}
