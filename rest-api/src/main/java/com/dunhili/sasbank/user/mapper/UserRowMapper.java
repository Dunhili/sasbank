package com.dunhili.sasbank.user.mapper;

import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.enums.AccountStatus;
import com.dunhili.sasbank.user.enums.Gender;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getObject("id", UUID.class));
        user.setFirstName(rs.getString("first_name"));
        user.setMiddleName(rs.getString("middle_name"));
        user.setLastName(rs.getString("last_name"));
        user.setGender(Gender.fromString(rs.getString("gender")));
        user.setEmailAddress(rs.getString("email_address"));
        user.setSsn(rs.getString("ssn"));
        user.setBirthdate(rs.getDate("birthday"));
        user.setStatus(AccountStatus.fromString(rs.getString("status")));
        user.setCreatedAt(rs.getTimestamp("created_at"));
        user.setCreatedBy(rs.getString("created_by"));
        user.setUpdatedAt(rs.getTimestamp("updated_at"));
        user.setUpdatedBy(rs.getString("updated_by"));
        return user;
    }
}
