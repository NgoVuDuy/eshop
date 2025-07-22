package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.User;

import java.util.Optional;

public interface UserService {

    public ApiResponse<Iterable<User>>  getAllUsers();
    public ApiResponse<User> getUserById(Long id);
    public ApiResponse<User> createUser(User user);
    public ApiResponse<User> updateUser(Long id, User user);
    public Message deleteUser(Long id);

    public ApiResponse<User> getProfile();
    public ApiResponse<User> updateProfile(UpdateUserRequest updateUserRequest);
    public Message deleteProfile();

}
