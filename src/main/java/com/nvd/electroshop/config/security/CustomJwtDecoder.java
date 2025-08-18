package com.nvd.electroshop.config.security;

import com.nvd.electroshop.repository.BlackListTokenRepository;
import com.nvd.electroshop.service.BlackListTokenService;
import lombok.extern.java.Log;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

import java.util.UUID;


public class CustomJwtDecoder implements JwtDecoder {

    private final BlackListTokenService blackListTokenService;
    private final NimbusJwtDecoder nimbusJwtDecoder;

    public CustomJwtDecoder(BlackListTokenService blackListTokenService, NimbusJwtDecoder nimbusJwtDecoder) {
        this.blackListTokenService = blackListTokenService;
        this.nimbusJwtDecoder = nimbusJwtDecoder;
    }

    @Override
    public Jwt decode(String token) throws JwtException {

        Jwt jwt = nimbusJwtDecoder.decode(token); // Giải mã token
        // Lấy token id
        String jti = jwt.getClaimAsString("jti");
        // Kiểm tra có trong blacklist không
        boolean isBlackListToken = blackListTokenService.isBackListToken(jti);

        if (isBlackListToken) {
            throw new JwtException("Token không khả dụng");
        }

        return jwt;
    }
}
