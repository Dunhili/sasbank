package com.dunhili.sasbank.audit.mapper;

import com.dunhili.sasbank.audit.dto.AuditModelWrapper;
import com.dunhili.sasbank.common.mapper.BaseMapper;
import com.dunhili.sasbank.user.dto.UserAddress;
import com.dunhili.sasbank.user.mapper.UserAddressRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.SQLException;

/**
 * Mapper for the {@link AuditModelWrapper} for the {@link UserAddress} entity.
 */
public class UserAddressAuditModelRowMapper extends BaseMapper implements RowMapper<AuditModelWrapper<UserAddress>> {

    public static final UserAddressAuditModelRowMapper INSTANCE = new UserAddressAuditModelRowMapper();

    /**
     * Maps a row from the result set to a {@link AuditModelWrapper} entity for {@link UserAddress}.
     * @param rs Result set row to map.
     * @param rowNum Row number.
     * @return Mapped user entity.
     * @throws SQLException if there is an error getting values from the result set.
     */
    @Override
    public AuditModelWrapper<UserAddress> mapRow(java.sql.ResultSet rs, int rowNum) throws SQLException {
        return mapAuditRow(rs, rowNum, UserAddressRowMapper.INSTANCE);
    }
}
