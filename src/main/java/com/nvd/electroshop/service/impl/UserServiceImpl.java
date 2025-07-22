package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.repository.UserRepository;
import com.nvd.electroshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    // Admin
    public ApiResponse<Iterable<User>> getAllUsers() {

        return new ApiResponse<>(1, userRepository.findAll());
    }

    @Override
    // Admin
    public ApiResponse<User> getUserById(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()) {
            User user = userOptional.get();

            return new ApiResponse<>(1, user);
        } else {

            throw new RuntimeException("Không tìm thấy người dùng để lấy thông tin");
        }
    }

    @Override
    // Admin
    public ApiResponse<User> createUser(User user) {

        return new ApiResponse<>(1, userRepository.save(user));
    }

    @Override
    // Admin
    public ApiResponse<User> updateUser(Long id, User userDetails) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()) {

            User user = userOptional.get();

            if(userDetails.getPhone() != null) user.setPhone(userDetails.getPhone());
            if(userDetails.getAddress() != null) user.setAddress(userDetails.getAddress());
            if(userDetails.getBirthDate() != null) user.setBirthDate(userDetails.getBirthDate());
            if(userDetails.getRole() != null) user.setRole(userDetails.getRole());

            if(userDetails.getUsername() != null) user.setUsername(userDetails.getUsername());
            if(userDetails.getPassword() != null) user.setPassword(userDetails.getPassword());

            return new ApiResponse<>(1, userRepository.save(user));
        } else {

            throw new RuntimeException("Không tìm thấy người dùng để cập nhật");
        }
    }

    @Override
    public Message deleteUser(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isPresent()) {

            userRepository.delete(userOptional.get());

            return new Message(1, "Xóa người dùng thành công");
        } else {

            throw new RuntimeException("Không tìm thấy người dùng để xóa");
        }
    }

    @Override
    // User
    public ApiResponse<User> getProfile() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        String username = securityContext.getAuthentication().getName();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {

            return new ApiResponse<>(1, userOptional.get());
        } else {

            throw new RuntimeException("Không tìm thấy người dùng");
        }
    }

    @Override
    // User
    public ApiResponse<User> updateProfile(UpdateUserRequest updateUserRequest) {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        String username = securityContext.getAuthentication().getName();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {

            User user = userOptional.get();

            if(updateUserRequest.getUsername() != null) {

                if (userRepository.existsByUsername(updateUserRequest.getUsername())) {
                    throw new RuntimeException("Tên người dùng đã tồn tại");
                } else {

                    user.setUsername(updateUserRequest.getUsername());
                }
            }

            if(updateUserRequest.getPassword() != null && updateUserRequest.getOldPassword() != null) {

                if(!passwordEncoder.matches(updateUserRequest.getOldPassword(), user.getPassword())) {
                    throw new RuntimeException("Mật khẩu không trùng khớp");
                } else {
                    user.setPassword(updateUserRequest.getPassword());
                }
            }

            if(updateUserRequest.getPhone() != null) user.setPhone(updateUserRequest.getPhone());
            if(updateUserRequest.getAddress() != null) user.setAddress(updateUserRequest.getAddress());
            if(updateUserRequest.getBirthDate() != null) user.setBirthDate(updateUserRequest.getBirthDate());

            return new ApiResponse<>(1, userRepository.save(user));

        } else {

            throw new RuntimeException("Không tìm thấy người dùng để cập nhật");
        }
    }

    @Override
    // User
    public Message deleteProfile() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        String username = securityContext.getAuthentication().getName();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isPresent()) {

            userRepository.delete(userOptional.get());

            return new Message(1, "Xóa người dùng thành công");
        } else {

            throw new RuntimeException("Không tìm thấy người dùng để xóa");
        }
    }
}
