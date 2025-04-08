package com.libraryManagement.controller;

import com.libraryManagement.entity.User;
import com.libraryManagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/users")
@Tag(name = "User Controller", description = "Endpoints for user management")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        if (registeredUser != null) {
            return ResponseEntity.ok(registeredUser);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", responses = {
            @ApiResponse(responseCode = "200", description = "Login successful", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    public ResponseEntity<User> loginUser(@RequestBody User user) {
        User loggedInUser = userService.loginUser(user.getUsername(), user.getPassword());
        if (loggedInUser != null) {
            return ResponseEntity.ok(loggedInUser);
        }
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get user by ID", responses = {
            @ApiResponse(responseCode = "200", description = "User found", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<Optional<User>> getUserById(@PathVariable int userId) {
        Optional<User> user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Update user by ID", responses = {
            @ApiResponse(responseCode = "200", description = "User updated successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    public ResponseEntity<User> updateUser(@PathVariable int userId, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok(updatedUser);
    }
}