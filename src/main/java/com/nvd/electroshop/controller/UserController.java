package com.nvd.electroshop.controller;

import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.request.UserRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.UserReponse;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.service.UserService;
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
    public ResponseEntity<ApiResponse<List<UserReponse>>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<UserReponse>> getUserById(@PathVariable Long id) {

        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<UserReponse>> createUser(@RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(userService.createUser(userRequest));
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<ApiResponse<UserReponse>> updateUser(@PathVariable Long id, @RequestBody UserRequest userRequest) {

        return ResponseEntity.ok(userService.updateUser(id, userRequest));
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<Message> deleteUser(@PathVariable Long id) {

        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @GetMapping("/profile")
    // User
    public ResponseEntity<ApiResponse<UserReponse>> getProfile() {

        return ResponseEntity.ok(userService.getProfile());
    }

    @PutMapping("/update")
    //user
    public ResponseEntity<ApiResponse<UserReponse>> updateProfile(@RequestBody UpdateUserRequest updateUserRequest) {

        return ResponseEntity.ok(userService.updateProfile(updateUserRequest));
    }
    @DeleteMapping("/delete")
    //user
    public ResponseEntity<Message> deleteProfile() {

        return ResponseEntity.ok(userService.deleteProfile());
    }
}
