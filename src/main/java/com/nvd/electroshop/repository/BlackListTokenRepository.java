package com.nvd.electroshop.repository;

import com.nvd.electroshop.entity.BlackListToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlackListTokenRepository extends JpaRepository<BlackListToken, Long> {

    public boolean existsById(String id);
}
