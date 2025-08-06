package com.nvd.electroshop.config.security;

import com.nvd.electroshop.repository.BlackListTokenRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

public class CustomJwtDecoder implements JwtDecoder {

    private final BlackListTokenRepository blackListTokenRepository;
    private final NimbusJwtDecoder nimbusJwtDecoder;

    public CustomJwtDecoder(BlackListTokenRepository blackListTokenRepository, NimbusJwtDecoder nimbusJwtDecoder) {
        this.blackListTokenRepository = blackListTokenRepository;
        this.nimbusJwtDecoder = nimbusJwtDecoder;
    }

    @Override
    public Jwt decode(String token) throws JwtException {

        Jwt jwt = nimbusJwtDecoder.decode(token); // Giải mã token
        // Lấy token id
        String jti = jwt.getClaimAsString("jti");
        // Kiểm tra có trong blacklist không
        if(blackListTokenRepository.existsById(jti)) {

            throw new JwtException("Token không khả dụng");
        }

        return jwt;
    }
}
