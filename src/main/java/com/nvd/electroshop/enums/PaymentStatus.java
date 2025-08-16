package com.nvd.electroshop.enums;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("Chờ thanh toán"),
    FAIL("Lỗi thanh toán"),
    SUCCESS("Thanh toán thành công");

    private final String message;

    PaymentStatus(String message) {
        this.message = message;
    }

}
