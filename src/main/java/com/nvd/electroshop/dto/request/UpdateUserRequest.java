package com.nvd.electroshop.dto.request;

import com.nvd.electroshop.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    private String username;
    private String password;

    private String phone;
    private String address;
    private LocalDate birthDate;

    private String oldPassword;
}
