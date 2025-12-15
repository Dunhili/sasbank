package com.dunhili.sasbank.user.controller;

import com.dunhili.sasbank.common.BaseController;
import com.dunhili.sasbank.common.dto.ApiResponse;
import com.dunhili.sasbank.user.dto.User;
import com.dunhili.sasbank.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller for user-related operations.
 * <p>
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
    @GetMapping(produces = "application/json")
    public ResponseEntity<ApiResponse<User>> getUser(@RequestParam UUID userId) {
        return ok(userService.getUserById(userId));
    }

    /**
     * Creates or updates a user based on the given user data. Returns a 200 OK response if the user is
     * successfully updated or created.
     * @param user user data to create or update
     */
    @PostMapping(consumes = "application/json")
    public ResponseEntity<ApiResponse<UUID>> createOrUpdateUser(@RequestBody User user) {
        return ok(userService.createOrUpdateUser(user));
    }

    /**
     * Deletes the user with the given ID. Returns a 200 OK response if the user is successfully deleted or a
     * 404 Not Found response if no user is found.
     * @param userId user ID to use for deleting delete
     */
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteUser(@RequestParam UUID userId) {
        userService.deleteUser(userId);
        return ok();
    }
}
