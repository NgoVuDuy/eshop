package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.request.UserRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.UserResponse;
import com.nvd.electroshop.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    // Admin
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @PatchMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> partialUpdateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(userService.partialUpdateUser(id, userRequest));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/profile")
    // User
    public ResponseEntity<ApiResponse<UserResponse>> getProfile() {

        return ResponseEntity.ok(userService.getProfile());
    }

    @PatchMapping("/update")
    //user
    public ResponseEntity<ApiResponse<UserResponse>> updateProfile(@RequestBody UpdateUserRequest updateUserRequest) {

        return ResponseEntity.ok(userService.updateProfile(updateUserRequest));
    }

    @DeleteMapping("/delete")
    //user
    public ResponseEntity<Message> deleteProfile() {

        return ResponseEntity.ok(userService.deleteProfile());
    }
}
