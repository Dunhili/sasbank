package com.dunhili.sasbank.auth.service;

import com.dunhili.sasbank.auth.dto.UserLogin;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Custom user details service that loads user details from the database.
 */
@AllArgsConstructor
@Service
public class LoadDatabaseUserDetailsService implements UserDetailsService {

    private final UserLoginService userLoginService;

    /**
     * Loads the user details from the database based on the given username.
     * @param username Username of the user to load.
     * @return User details for the given username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        UserLogin login = userLoginService.getLoginByUsername(username);
        if (login == null) {
            throw new UsernameNotFoundException(username);
        }

        List<String> userRoles = userLoginService.getUserRoles(login.getUserId())
                .stream()
                .map(role -> role.getUserRole().name())
                .toList();

        return User.withUsername(login.getUsername())
                .password(login.getPassword())
                .roles(userRoles.toArray(String[]::new))
                .build();
    }
}
