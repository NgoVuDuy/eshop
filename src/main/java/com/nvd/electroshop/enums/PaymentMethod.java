package com.nvd.electroshop.enums;


import lombok.Data;

public enum PaymentMethod {

    COD("Thanh toán khi nhận hàng"),
    VNPAY("Cổng thanh toán VNPay");

    private String paymentMethod;

    PaymentMethod(String paymentMethod) {

        this.paymentMethod = paymentMethod;
    }

    public String getMethod() {
        return paymentMethod;
    }

    public void setMethod(String method) {
        this.paymentMethod = method;
    }
}
