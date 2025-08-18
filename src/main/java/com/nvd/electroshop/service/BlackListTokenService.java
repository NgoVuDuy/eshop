package com.nvd.electroshop.service;

public interface BlackListTokenService {

    boolean isBackListToken(String jti);
}
