package com.dunhili.sasbank.user.mapper;

import com.dunhili.sasbank.common.mapper.BaseMapper;
import com.dunhili.sasbank.user.dto.UserPhone;
import com.dunhili.sasbank.user.enums.PhoneType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Mapper for the {@link UserPhone} entity.
 */
public class UserPhoneMapper extends BaseMapper implements RowMapper<UserPhone> {

    public static final UserPhoneMapper INSTANCE = new UserPhoneMapper();

    /**
     * Maps a row from the result set to a {@link UserPhone} entity.
     * @param rs Result set row to map.
     * @param rowNum Row number.
     * @return Mapped user phone entity.
     * @throws SQLException if there is an error getting values from the result set.
     */
    @Override
    public UserPhone mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserPhone phone = new UserPhone();
        mapId(phone, rs);
        phone.setUserId(rs.getObject("user_id", UUID.class));
        phone.setPhoneNumber(rs.getString("phone_number"));
        phone.setPhoneType(PhoneType.fromString(rs.getString("phone_type")));
        phone.setPrimary(rs.getBoolean("is_primary"));
        mapAuditRows(phone, rs);
        return phone;
    }
}
