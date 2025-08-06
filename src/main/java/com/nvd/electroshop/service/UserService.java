package com.nvd.electroshop.service;

import com.cloudinary.Api;
import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.request.UserRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    // admin
    public ApiResponse<List<UserResponse>>  getAllUsers();
    public ApiResponse<UserResponse> getUserById(Long id);
    public ApiResponse<UserResponse> createUser(UserRequest userRequest);
    public ApiResponse<UserResponse> updateUser(Long id, UserRequest userRequest);
    public ApiResponse<UserResponse> partialUpdateUser(Long id, UserRequest userRequest);
    public Message deleteUser(Long id);

    // user
    public ApiResponse<UserResponse> getProfile();
    public ApiResponse<UserResponse> updateProfile(UpdateUserRequest updateUserRequest);
    public Message deleteProfile();

}
