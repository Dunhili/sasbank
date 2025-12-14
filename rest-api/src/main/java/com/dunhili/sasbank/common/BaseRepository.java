package com.dunhili.sasbank.common;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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

    protected void update(String sql, Map<String, ?> params) {
        jdbcTemplate.update(sql, params);
    }
}
