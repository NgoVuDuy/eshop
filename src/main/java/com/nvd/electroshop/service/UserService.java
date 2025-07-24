package com.nvd.electroshop.service;

import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.request.UserRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.UserReponse;
import com.nvd.electroshop.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public ApiResponse<List<UserReponse>>  getAllUsers();
    public ApiResponse<UserReponse> getUserById(Long id);
    public ApiResponse<UserReponse> createUser(UserRequest userRequest);
    public ApiResponse<UserReponse> updateUser(Long id, UserRequest userRequest);
    public Message deleteUser(Long id);

    public ApiResponse<UserReponse> getProfile();
    public ApiResponse<UserReponse> updateProfile(UpdateUserRequest updateUserRequest);
    public Message deleteProfile();

}
