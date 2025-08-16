package com.nvd.electroshop.dto.request;

import com.nvd.electroshop.constant.ValidationMessages;
import com.nvd.electroshop.validation.annotation.Age;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = ValidationMessages.USERNAME_NOT_BLANK)
    @Size(min = 8, max = 12, message = ValidationMessages.USERNAME_SIZE)
    private String username;

    @NotBlank(message = ValidationMessages.PASSWORD_NOT_BLANK)
    @Size(min = 8, max = 12, message = ValidationMessages.PASSWORD_SIZE)
    private String password;

    @NotBlank(message = ValidationMessages.PHONE_NOT_BLANK)
    @Pattern(regexp = "^(0|\\+84)(3[2-9]|5[689]|7[06-9]|8[1-9]|9[0-9])[0-9]{7}$",
            message = ValidationMessages.PHONE_INVALID)
    private String phone;

    @NotBlank(message = ValidationMessages.ADDRESS_NOT_BLANK)
    @Size(min = 5, max = 255, message = ValidationMessages.ADDRESS_SIZE)
    private String address;

    @Age(value = 18)
    private LocalDate birthDate;

    @NotBlank(message = ValidationMessages.PASSWORD_NOT_BLANK)
    @Size(min = 8, max = 12, message = ValidationMessages.PASSWORD_SIZE)
    private String oldPassword;
}
