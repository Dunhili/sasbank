package com.dunhili.sasbank.auth.service;

import com.dunhili.sasbank.auth.dto.UserLogin;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class LoadDatabaseUserDetailsService implements UserDetailsService {

    private final UserLoginService userLoginService;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserLogin login = userLoginService.getLoginByUsername(username);
        if (login == null) {
            throw new UsernameNotFoundException(username);
        }

        return User.withUsername(login.getUsername())
                .password(login.getPassword())
                .roles("USER")
                .build();
    }
}
