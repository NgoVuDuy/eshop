package com.nvd.electroshop.dto.request;

import com.nvd.electroshop.constant.ValidationMessages;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthRequest {

    @NotBlank(message = ValidationMessages.USERNAME_NOT_BLANK)
    @Size(min = 8, max = 12, message = ValidationMessages.USERNAME_SIZE)
    private String username;

    @NotBlank(message = ValidationMessages.PASSWORD_NOT_BLANK)
    @Size(min = 8, max = 12, message = ValidationMessages.PASSWORD_SIZE)
    private String password;

}
