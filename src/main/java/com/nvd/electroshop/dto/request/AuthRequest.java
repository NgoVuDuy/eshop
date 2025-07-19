package com.nvd.electroshop.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthRequest {

    private String username;
    private String password;
}
