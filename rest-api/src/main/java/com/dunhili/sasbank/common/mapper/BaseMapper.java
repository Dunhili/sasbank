package com.dunhili.sasbank.common.mapper;

import com.dunhili.sasbank.common.dto.BaseDTO;

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
    public void mapId(BaseDTO dto, ResultSet rs) throws SQLException {
        dto.setId(rs.getObject("id", UUID.class));
    }

    /**
     * Maps the table audit fields to the DTO's audit fields.
     * @param dto DTO to map fields to
     * @param rs result set to get values from
     * @throws SQLException if there is an error getting values from the result set
     */
    public void mapAuditRows(BaseDTO dto, ResultSet rs) throws SQLException {
        dto.setCreatedAt(rs.getTimestamp("created_at"));
        dto.setCreatedBy(rs.getString("created_by"));
        dto.setUpdatedAt(rs.getTimestamp("updated_at"));
        dto.setUpdatedBy(rs.getString("updated_by"));
    }
}
