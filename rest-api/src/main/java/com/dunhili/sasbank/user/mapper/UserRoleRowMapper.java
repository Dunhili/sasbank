package com.dunhili.sasbank.user.mapper;

import com.dunhili.sasbank.common.mapper.BaseMapper;
import com.dunhili.sasbank.user.enums.UserRole;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for the {@link UserRole} enum.
 */
public class UserRoleRowMapper extends BaseMapper implements RowMapper<UserRole> {

    public static final UserRoleRowMapper INSTANCE = new UserRoleRowMapper();

    /**
     * Maps a row from the result set to the {@link UserRole} enum.
     * @param rs Result set row to map.
     * @param rowNum Row number.
     * @return Mapped user role enum
     * @throws SQLException if there is an error getting values from the result set.
     */
    @Override
    public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        return UserRole.fromString(rs.getString("user_role"));
    }
}
