package com.dunhili.sasbank.user.mapper;

import com.dunhili.sasbank.common.mapper.BaseMapper;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.enums.AccountStatus;
import com.dunhili.sasbank.user.enums.Gender;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Mapper for the {@link User} entity.
 */
public class UserRowMapper extends BaseMapper implements RowMapper<User> {

    public static final UserRowMapper INSTANCE = new UserRowMapper();

    /**
     * Maps a row from the result set to a {@link User} entity.
     * @param rs Result set row to map.
     * @param rowNum Row number.
     * @return Mapped user entity.
     * @throws SQLException if there is an error getting values from the result set.
     */
    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        mapId(user, rs);
        user.setFirstName(rs.getString("first_name"));
        user.setMiddleName(rs.getString("middle_name"));
        user.setLastName(rs.getString("last_name"));
        user.setGender(Gender.fromString(rs.getString("gender")));
        user.setEmailAddress(rs.getString("email_address"));
        user.setSsn(rs.getString("ssn"));
        user.setBirthday(mapDate(rs, "birthday"));
        user.setStatus(AccountStatus.fromString(rs.getString("status")));
        mapAuditRows(user, rs);
        return user;
    }
}
