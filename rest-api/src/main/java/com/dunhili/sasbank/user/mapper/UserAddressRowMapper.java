package com.dunhili.sasbank.user.mapper;

import com.dunhili.sasbank.common.mapper.BaseMapper;
import com.dunhili.sasbank.user.dto.UserAddress;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Mapper for the {@link UserAddress} entity.
 */
public class UserAddressRowMapper extends BaseMapper implements RowMapper<UserAddress> {

    public static final UserAddressRowMapper INSTANCE = new UserAddressRowMapper();

    @Override
    public UserAddress mapRow(ResultSet rs, int rowNum) throws SQLException {
        UserAddress address = new UserAddress();
        mapId(address, rs);
        address.setUserId(rs.getObject("user_id", UUID.class));
        address.setAddressLine1(rs.getString("address_line_1"));
        address.setAddressLine2(rs.getString("address_line_2"));
        address.setAddressLine3(rs.getString("address_line_3"));
        address.setState(rs.getString("state"));
        address.setCity(rs.getString("city"));
        address.setZipcode(rs.getString("zipcode"));
        address.setCountry(rs.getString("country"));
        address.setPrimary(rs.getBoolean("is_primary"));
        mapAuditRows(address, rs);
        return address;
    }
}
