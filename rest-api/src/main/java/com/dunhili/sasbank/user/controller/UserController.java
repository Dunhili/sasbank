package com.dunhili.sasbank.user.controller;

import com.dunhili.sasbank.common.BaseController;
import com.dunhili.sasbank.common.dto.ApiResponse;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for user-related operations.
 *
 * Base URL - /users
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    /**
     * Returns a 200 OK for the user with the given ID or a 404 Not Found response if no user is found.
     * @param userId user ID to use for finding the user
     * @return 200 OK response with the user data or 404 Not Found response
     */
    @GetMapping(path = "", produces = "application/json")
    public ResponseEntity<ApiResponse<User>> getUser(@RequestParam UUID userId) {
        return ok(userService.getUserById(userId));
    }

    /**
     * Creates or updates a user based on the given user data. Returns a 200 OK response if the user is
     * successfully updated or created.
     * @param user user data to create or update
     */
    @PostMapping(path = "", consumes = "application/json")
    public ResponseEntity<ApiResponse<Void>> createOrUpdateUser(@RequestBody User user) {
        userService.createOrUpdateUser(user);
        return ok();
    }

    /**
     * Deletes the user with the given ID. Returns a 200 OK response if the user is successfully deleted or a
     * 404 Not Found response if no user is found.
     * @param userId user ID to use for deleting delete
     */
    @DeleteMapping(path = "")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@RequestParam UUID userId) {
        userService.deleteUser(userId);
        return ok();
    }
}
