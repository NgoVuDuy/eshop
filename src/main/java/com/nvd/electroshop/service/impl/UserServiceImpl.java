package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.request.UserRequest;
import com.nvd.electroshop.dto.response.ApiResponse;
import com.nvd.electroshop.dto.response.Message;
import com.nvd.electroshop.dto.response.UserReponse;
import com.nvd.electroshop.entity.User;
import com.nvd.electroshop.repository.UserRepository;
import com.nvd.electroshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    // Admin
    public ApiResponse<List<UserReponse>> getAllUsers() {

        List<User> userIterable = userRepository.findAll();

        List<UserReponse> userReponseList = new ArrayList<>();

        userIterable.forEach(user -> {

            UserReponse userReponse = UserReponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .phone(user.getPhone())
                    .address(user.getAddress())
                    .birthDate(user.getBirthDate())
                    .role(user.getRole())
                    .build();

            userReponseList.add(userReponse);
        });

        return new ApiResponse<>(1, userReponseList);
    }

    @Override
    // Admin
    public ApiResponse<UserReponse> getUserById(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new RuntimeException("Không tìm thấy người dùng để lấy thông tin");

        }

        User user = userOptional.get();

        UserReponse userReponse = UserReponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .build();

        return new ApiResponse<>(1, userReponse);
    }

    @Override
    // Admin
    public ApiResponse<UserReponse> createUser(UserRequest userRequest) {

        if (userRepository.existsByUsername(userRequest.getUsername())) {

            throw new RuntimeException("Tên người dùng đã tồn tại");
        }
        User user = User.builder()
                .username(userRequest.getUsername())
                .password(userRequest.getPassword())
                .phone(userRequest.getPhone())
                .address(userRequest.getAddress())
                .birthDate(userRequest.getBirthDate())
                .role(userRequest.getRole())
                .build();

        user = userRepository.save(user);

        UserReponse userReponse = UserReponse.builder()
                .username(user.getUsername())
                .phone(user.getPhone())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .build();

        return new ApiResponse<>(1, userReponse);
    }

    @Override
    // Admin
    public ApiResponse<UserReponse> updateUser(Long id, UserRequest userRequest) {

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {

            throw new RuntimeException("Không tìm thấy người dùng để cập nhật");

        }

        User user = userOptional.get();

        if(userRequest.getPhone() != null) user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress() != null) user.setAddress(userRequest.getAddress());
        if(userRequest.getBirthDate() != null) user.setBirthDate(userRequest.getBirthDate());
        if(userRequest.getRole() != null) user.setRole(userRequest.getRole());

        if(userRequest.getUsername() != null) user.setUsername(userRequest.getUsername());
        if(userRequest.getPassword() != null) user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        user = userRepository.save(user);

        UserReponse userReponse = UserReponse.builder()
                .username(user.getUsername())
                .phone(user.getPhone())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .build();

        return new ApiResponse<>(1, userReponse);
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
    public ApiResponse<UserReponse> getProfile() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        String username = securityContext.getAuthentication().getName();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {

            throw new RuntimeException("Không tìm thấy người dùng");
        }

        User user = userOptional.get();

        UserReponse userReponse = UserReponse.builder()
                .username(user.getUsername())
                .phone(user.getPhone())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .build();

        return new ApiResponse<>(1, userReponse);
    }

    @Override
    // User
    public ApiResponse<UserReponse> updateProfile(UpdateUserRequest updateUserRequest) {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        String username = securityContext.getAuthentication().getName();

        Optional<User> userOptional = userRepository.findByUsername(username);

        if (userOptional.isEmpty()) {

            throw new RuntimeException("Không tìm thấy người dùng để cập nhật");
        }

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
                user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));
            }
        }

        if(updateUserRequest.getPhone() != null) user.setPhone(updateUserRequest.getPhone());
        if(updateUserRequest.getAddress() != null) user.setAddress(updateUserRequest.getAddress());
        if(updateUserRequest.getBirthDate() != null) user.setBirthDate(updateUserRequest.getBirthDate());

        user = userRepository.save(user);

        UserReponse userReponse = UserReponse.builder()
                .username(user.getUsername())
                .phone(user.getPhone())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .role(user.getRole())
                .build();

        return new ApiResponse<>(1, userReponse);

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
