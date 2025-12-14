package com.dunhili.sasbank.auth.mapper;

import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.common.mapper.BaseMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Mapper for the {@link UserLogin} entity.
 */
public class UserLoginRowMapper extends BaseMapper implements RowMapper<UserLogin> {

    public static final UserLoginRowMapper INSTANCE = new UserLoginRowMapper();

    @Override
    public UserLogin mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserLogin login = new UserLogin();
        mapId(login, rs);
        login.setUserId(rs.getObject("user_id", UUID.class));
        login.setUsername(rs.getString("username"));
        login.setPasswordHash(rs.getString("password_hash"));
        login.setPasswordSalt(rs.getString("password_salt"));
        login.setHashAlgorithm(rs.getString("hash_algorithm"));
        login.setLastLoginDate(rs.getTimestamp("last_login_date"));
        login.setPasswordAttempts(rs.getInt("password_attempts"));
        mapAuditRows(login, rs);
        return login;
    }
}
