package com.nvd.electroshop.dto.request;

import com.nvd.electroshop.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class AuthRequest {

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;
}
