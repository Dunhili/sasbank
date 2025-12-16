package com.dunhili.sasbank.audit.service;

import com.dunhili.sasbank.audit.dto.AuditModelWrapper;
import com.dunhili.sasbank.audit.repository.AuditRepository;
import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.auth.dto.UserRole;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.dto.UserAddress;
import com.dunhili.sasbank.user.dto.UserPhone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * Service class for retrieving audit data.
 */
@RequiredArgsConstructor
@Service
public class AuditService {

    private final AuditRepository auditRepository;

    /**
     * Returns a list of audit rows for the given user ID.
     * @param userId ID of the user to retrieve audit rows for.
     * @return List of audit rows for the given user ID.
     */
    public List<AuditModelWrapper<User>> getAuditsForUser(UUID userId) {
        return auditRepository.findUserAuditRowsByUserId(userId);
    }

    /**
     * Returns a list of audit rows for the given address ID.
     * @param userId ID of the user to retrieve audit rows for.
     * @return List of audit rows for the given address ID.
     */
    public List<AuditModelWrapper<UserAddress>> getAuditsForAddress(UUID userId) {
        return auditRepository.findUserAddressAuditRowsByAddressId(userId);
    }

    /**
     * Returns a list of audit rows for the given phone ID.
     * @param userId ID of the user to retrieve audit rows for.
     * @return List of audit rows for the given phone ID.
     */
    public List<AuditModelWrapper<UserPhone>> getAuditsForPhone(UUID userId) {
        return auditRepository.findUserPhoneAuditRowsByPhoneId(userId);
    }

    /**
     * Returns a list of audit rows for the given login ID.
     * @param userLoginId ID of the login to retrieve audit rows for.
     * @return List of audit rows for the given login ID.
     */
    public List<AuditModelWrapper<UserLogin>> getAuditsForLogin(UUID userLoginId) {
        return auditRepository.findUserLoginAuditRowsByLoginId(userLoginId);
    }

    /**
     * Returns a list of audit rows for the given user role ID.
     * @param userId ID of the user role to retrieve audit rows for.
     * @return List of audit rows for the given user role ID.
     */
    public List<AuditModelWrapper<UserRole>> getAuditsForRole(UUID userId) {
        return auditRepository.findUserRoleAuditRowsByPhoneId(userId);
    }
}
