package com.dunhili.sasbank.auth.mapper;

import com.dunhili.sasbank.auth.dto.UserRole;
import com.dunhili.sasbank.auth.enums.Role;
import com.dunhili.sasbank.common.mapper.BaseMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Mapper for the {@link Role} enum.
 */
public class UserRoleRowMapper extends BaseMapper implements RowMapper<UserRole> {

    public static final UserRoleRowMapper INSTANCE = new UserRoleRowMapper();

    /**
     * Maps a row from the result set to the {@link Role} enum.
     * @param rs Result set row to map.
     * @param rowNum Row number.
     * @return Mapped role enum.
     * @throws SQLException if there is an error getting values from the result set.
     */
    @Override
    public UserRole mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserRole userRole = new UserRole();
        userRole.setUserId(rs.getObject("user_id", UUID.class));
        userRole.setUserRole(Role.fromString(rs.getString("user_role")));
        userRole.setCreatedAt(rs.getTimestamp("created_at"));
        userRole.setCreatedBy(rs.getString("created_by"));
        return userRole;
    }
}
