package com.dunhili.sasbank.user.controller;

import com.dunhili.sasbank.common.BaseController;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping(path = "", produces = "application/json")
    public User getUser(@RequestParam UUID userId) {
        return userService.getUserById(userId);
    }

    @PostMapping(path = "", consumes = "application/json")
    public void createOrUpdateUser(@RequestBody User user) {

    }


}
