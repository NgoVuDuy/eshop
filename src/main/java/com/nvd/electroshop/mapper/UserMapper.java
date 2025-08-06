package com.nvd.electroshop.mapper;

import com.nvd.electroshop.dto.request.UpdateUserRequest;
import com.nvd.electroshop.dto.request.UserRequest;
import com.nvd.electroshop.dto.response.UserResponse;
import com.nvd.electroshop.entity.User;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class UserMapper {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // response
    public UserResponse mapToUserResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .phone(user.getPhone())
                .address(user.getAddress())
                .birthDate(user.getBirthDate())
                .build();
    }

    public List<UserResponse> mapToUserResponseList(List<User> userList) {

        return userList.stream().map(this::mapToUserResponse).toList();
    }

    // request
    public User mapToUser(UserRequest userRequest) {

        return this.mapToUser(userRequest, null);
    }

    public User mapToUser(UserRequest userRequest, User userDetails) {

        User user = Objects.requireNonNullElseGet(userDetails,User::new);

        user.setUsername(userRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPhone(userDetails.getPhone());
        user.setAddress(userDetails.getAddress());
        user.setBirthDate(userDetails.getBirthDate());
        user.setRole(userRequest.getRole());

        return user;
    }


    public User mapToUserRequireNonNull(UserRequest userRequest, User userDetails) {

        User user = Objects.requireNonNullElseGet(userDetails,User::new);

        if(userRequest.getPhone() != null) user.setPhone(userRequest.getPhone());
        if(userRequest.getAddress() != null) user.setAddress(userRequest.getAddress());
        if(userRequest.getBirthDate() != null) user.setBirthDate(userRequest.getBirthDate());
        if(userRequest.getRole() != null) user.setRole(userRequest.getRole());

        if(userRequest.getUsername() != null) user.setUsername(userRequest.getUsername());
        if(userRequest.getPassword() != null) user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

        return user;
    }

    public User mapToUserRequireNonNull(UpdateUserRequest updateUserRequest, User userDetails) {

        User user = Objects.requireNonNullElseGet(userDetails,User::new);

        if(updateUserRequest.getUsername() != null) user.setUsername(updateUserRequest.getUsername());
        if(updateUserRequest.getPassword() != null) user.setPassword(passwordEncoder.encode(updateUserRequest.getPassword()));

        if(updateUserRequest.getPhone() != null) user.setPhone(updateUserRequest.getPhone());
        if(updateUserRequest.getAddress() != null) user.setAddress(updateUserRequest.getAddress());
        if(updateUserRequest.getBirthDate() != null) user.setBirthDate(updateUserRequest.getBirthDate());

        return user;
    }
}
