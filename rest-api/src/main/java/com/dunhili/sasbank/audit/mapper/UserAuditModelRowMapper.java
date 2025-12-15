package com.dunhili.sasbank.audit.mapper;

import com.dunhili.sasbank.audit.dto.AuditModelWrapper;
import com.dunhili.sasbank.common.mapper.BaseMapper;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.mapper.UserRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for the {@link AuditModelWrapper} for the {@link User} entity.
 */
public class UserAuditModelRowMapper extends BaseMapper implements RowMapper<AuditModelWrapper<User>> {

    public static final UserAuditModelRowMapper INSTANCE = new UserAuditModelRowMapper();

    /**
     * Maps a row from the result set to a {@link AuditModelWrapper} entity for {@link User}.
     * @param rs Result set row to map.
     * @param rowNum Row number.
     * @return Mapped user entity.
     * @throws SQLException if there is an error getting values from the result set.
     */
    @Override
    public AuditModelWrapper<User> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapAuditRow(rs, rowNum, UserRowMapper.INSTANCE);
    }
}
