package com.dunhili.sasbank.audit.mapper;

import com.dunhili.sasbank.audit.dto.AuditModelWrapper;
import com.dunhili.sasbank.common.mapper.BaseMapper;
import com.dunhili.sasbank.user.dto.UserPhone;
import com.dunhili.sasbank.user.mapper.UserPhoneRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for the {@link AuditModelWrapper} for the {@link UserPhone} entity.
 */
public class UserPhoneAuditModelRowMapper extends BaseMapper implements RowMapper<AuditModelWrapper<UserPhone>> {

    public static final UserPhoneAuditModelRowMapper INSTANCE = new UserPhoneAuditModelRowMapper();

    /**
     * Maps a row from the result set to a {@link AuditModelWrapper} entity for {@link UserPhone}.
     * @param rs Result set row to map.
     * @param rowNum Row number.
     * @return Mapped user entity.
     * @throws SQLException if there is an error getting values from the result set.
     */
    @Override
    public AuditModelWrapper<UserPhone> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return mapAuditRow(rs, rowNum, UserPhoneRowMapper.INSTANCE);
    }
}
