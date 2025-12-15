package com.dunhili.sasbank.auth.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class related to authentication.
 */
public class AuthUtils {

    private final static String SYSTEM_USER = "System";

    private AuthUtils() { }

    /**
     * Returns the username of the currently authenticated user.
     * @return Username of the currently authenticated user.
     */
    public static String getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            return SYSTEM_USER;
        }

        return auth.getName();

    }
}
