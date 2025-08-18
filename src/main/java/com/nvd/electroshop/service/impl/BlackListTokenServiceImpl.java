package com.nvd.electroshop.service.impl;

import com.nvd.electroshop.repository.BlackListTokenRepository;
import com.nvd.electroshop.service.BlackListTokenService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BlackListTokenServiceImpl implements BlackListTokenService {

    private final BlackListTokenRepository blackListTokenRepository;

    public BlackListTokenServiceImpl(BlackListTokenRepository blackListTokenRepository) {
        this.blackListTokenRepository = blackListTokenRepository;
    }

    @Override
    @Cacheable(value = "blacklist-tokens", key = "#jti")
    public boolean isBackListToken(String jti) {

        return blackListTokenRepository.existsById(jti);
    }
}
