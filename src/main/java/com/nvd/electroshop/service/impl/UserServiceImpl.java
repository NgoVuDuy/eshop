package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.request.UserRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.UserResponse;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.exception.BadRequestException;
import com.nvd.electroshop.exception.ConflictException;
import com.nvd.electroshop.exception.ResourceNotFoundException;
import com.nvd.electroshop.mapper.UserMapper;
import com.nvd.electroshop.repository.UserRepository;
import com.nvd.electroshop.service.GlobalService;
import com.nvd.electroshop.service.UserService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final GlobalService globalService;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, GlobalService globalService) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.globalService = globalService;
    }

    @Override
    // Admin
    public ApiResponse<List<UserResponse>> getAllUsers() {

        List<User> userList = userRepository.findAll();

        List<UserResponse> userReponseList = userMapper.mapToUserResponseList(userList);

        return new ApiResponse<>(1, userReponseList);
    }

    @Override
    // Admin
    public ApiResponse<UserResponse> getUserById(Long id) {

        User user = getUser(id);
        UserResponse userReponse = userMapper.mapToUserResponse(user);

        return new ApiResponse<>(1, userReponse);
    }

    @Override
    // Admin
    public ApiResponse<UserResponse> createUser(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {

            throw new ConflictException("Tên người dùng đã tồn tại");
        }
        User user = userMapper.mapToUser(userRequest);

        user = userRepository.save(user);

        UserResponse userReponse = userMapper.mapToUserResponse(user);

        return new ApiResponse<>(1, userReponse);
    }

    @Override
    // Admin
    public ApiResponse<UserResponse> updateUser(Long id, UserRequest userRequest) {

        User user = getUser(id);
        user = userMapper.mapToUser(userRequest, user);

        user = userRepository.save(user);

        UserResponse userReponse = userMapper.mapToUserResponse(user);

        return new ApiResponse<>(1, userReponse);
    }

    @Override
    public ApiResponse<UserResponse> partialUpdateUser(Long id, UserRequest userRequest) {

        User user = getUser(id);
        user = userMapper.mapToUserRequireNonNull(userRequest, user);

        user = userRepository.save(user);

        UserResponse userResponse = userMapper.mapToUserResponse(user);
        return null;
    }

    @Override
    public Message deleteUser(Long id) { // Xóa người dùng

        userRepository.deleteById(id);

        return new Message(1, "Xóa người dùng thành công");
    }

    @Override
    // User
    public ApiResponse<UserResponse> getProfile() {

        User user = globalService.getUserByToken();

        UserResponse userReponse = userMapper.mapToUserResponse(user);

        return new ApiResponse<>(1, userReponse);
    }

    @Override
    // User
    public ApiResponse<UserResponse> updateProfile(UpdateUserRequest updateUserRequest) {

        User user = globalService.getUserByToken();

        if(updateUserRequest.getUsername() != null) {

            if (userRepository.existsByUsername(updateUserRequest.getUsername())) {
                throw new ConflictException("Tên người dùng đã tồn tại");
            }
        }

        if(updateUserRequest.getPassword() != null && updateUserRequest.getOldPassword() != null) {

            if(!passwordEncoder.matches(updateUserRequest.getOldPassword(), user.getPassword())) {
                throw new BadRequestException("Mật khẩu không trùng khớp");
            }
        }

        user = userMapper.mapToUserRequireNonNull(updateUserRequest, user);
        user = userRepository.save(user);

        UserResponse userReponse = userMapper.mapToUserResponse(user);

        return new ApiResponse<>(1, userReponse);
    }

    @Override
    // User
    public Message deleteProfile() { // Khóa người dùng

        User user = globalService.getUserByToken();

        user.setDelete(true);

        user = userRepository.save(user);

        return new Message(1, "Bạn đã xóa tài khoản thành công");
    }

    // ins
    private User getUser(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy người dùng để lấy thông tin");

        }

        return userOptional.get();
    }
}
