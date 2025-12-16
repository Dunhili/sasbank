package com.dunhili.sasbank.common.mapper;

import com.dunhili.sasbank.audit.dto.AuditModelWrapper;
import com.dunhili.sasbank.audit.enums.AuditAction;
import com.dunhili.sasbank.common.dto.BaseDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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

    /**
     * Maps the audit table row to a DTO.
     * @param rs ResultSet to get the row from
     * @param rowNum Row number
     * @param mapper Row mapper to use to map the row to a DTO
     * @return Mapped audit row
     * @param <T> Type of DTO to map the row to
     * @throws SQLException if there is an error getting values from the result set
     */
    protected <T> AuditModelWrapper<T> mapAuditRow(ResultSet rs, int rowNum, RowMapper<T> mapper) throws SQLException {
        UUID id = rs.getObject("id", UUID.class);
        T model = mapper.mapRow(rs, rowNum);
        AuditAction action = AuditAction.fromString(rs.getString("action"));
        return new AuditModelWrapper<>(id, action, model);
    }

    /**
     * Null-safe way to map a date column to a LocalDate.
     * @param rs ResultSet to get the date from
     * @param columnName Name of the column to get the date from
     * @return LocalDate converted from the date column, or null if the column is null
     * @throws SQLException if there is an error getting the date from the result set
     */
    protected LocalDate mapDate(ResultSet rs, String columnName) throws SQLException {
        Date date = rs.getDate(columnName);
        return date != null ? date.toLocalDate() : null;
    }
}
