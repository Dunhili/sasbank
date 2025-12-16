package com.dunhili.sasbank.audit.mapper;

import com.dunhili.sasbank.audit.dto.AuditModelWrapper;
import com.dunhili.sasbank.auth.dto.UserRole;
import com.dunhili.sasbank.auth.mapper.UserRoleRowMapper;
import com.dunhili.sasbank.common.mapper.BaseMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for the {@link AuditModelWrapper} for the {@link UserRole} entity.
 */
public class UserRoleAuditModelRowMapper extends BaseMapper implements RowMapper<AuditModelWrapper<UserRole>> {

    public static final UserRoleAuditModelRowMapper INSTANCE = new UserRoleAuditModelRowMapper();

    /**
     * Maps a row from the result set to a {@link AuditModelWrapper} entity for {@link UserRole}.
     * @param rs Result set row to map.
     * @param rowNum Row number.
     * @return Mapped user entity.
     * @throws SQLException if there is an error getting values from the result set.
     */
    @Override
    public AuditModelWrapper<UserRole> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapAuditRow(rs, rowNum, UserRoleRowMapper.INSTANCE);
    }
}
