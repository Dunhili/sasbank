package com.dunhili.sasbank.common.mapper;

import com.dunhili.sasbank.audit.dto.AuditModelWrapper;
import com.dunhili.sasbank.audit.enums.AuditAction;
import com.dunhili.sasbank.common.dto.BaseDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

/**
 * Base mapper to help with mapping common fields in DTOs.
 */
public abstract class BaseMapper {

    /**
     * Maps the table "id" field to the DTO's ID field.
     * @param dto DTO to map fields to
     * @param rs result set to get values from
     * @throws SQLException if there is an error getting values from the result set
     */
    protected void mapId(BaseDTO dto, ResultSet rs) throws SQLException {
        dto.setId(rs.getObject("id", UUID.class));
    }

    /**
     * Maps the table audit fields to the DTO's audit fields.
     * @param dto DTO to map fields to
     * @param rs result set to get values from
     * @throws SQLException if there is an error getting values from the result set
     */
    protected void mapAuditRows(BaseDTO dto, ResultSet rs) throws SQLException {
        dto.setCreatedAt(rs.getTimestamp("created_at"));
        dto.setCreatedBy(rs.getString("created_by"));
        dto.setUpdatedAt(rs.getTimestamp("updated_at"));
        dto.setUpdatedBy(rs.getString("updated_by"));
    }

    protected <T> AuditModelWrapper<T> mapAuditRow(ResultSet rs, int rowNum, RowMapper<T> mapper) throws SQLException {
        UUID id = rs.getObject("id", UUID.class);
        T model = mapper.mapRow(rs, rowNum);
        AuditAction action = AuditAction.fromString(rs.getString("action"));
        return new AuditModelWrapper<>(id, action, model);
    }
}
