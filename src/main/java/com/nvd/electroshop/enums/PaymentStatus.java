package com.nvd.electroshop.enums;

public enum PaymentStatus {
    PENDING("Chờ thanh toán"),
    FAIL("Lỗi thanh toán"),
    SUCCESS("Thanh toán thành công");

    private String paymentStatus;

    PaymentStatus(String s) {
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }
}
