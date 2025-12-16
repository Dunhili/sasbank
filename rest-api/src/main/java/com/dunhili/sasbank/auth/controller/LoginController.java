package com.dunhili.sasbank.auth.controller;

import com.dunhili.sasbank.auth.dto.UserLogin;
import com.dunhili.sasbank.auth.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller handling user login.
 */
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserLoginService userLoginService;

    /**
     * Creates a new login record for the given user.
     * @param login User login data to create.
     * @return HTTP 200 OK if the login was created successfully, HTTP 400 Bad Request otherwise.
     */
    @PostMapping
    public ResponseEntity<Void> createOrUpdateLogin(@RequestBody UserLogin login) {
        userLoginService.createOrUpdateUserLogin(login);
        return ResponseEntity.ok().build();
    }
}
