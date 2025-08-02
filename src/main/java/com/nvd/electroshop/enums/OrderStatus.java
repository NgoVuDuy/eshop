package com.nvd.electroshop.enums;

public enum OrderStatus {
    PENDING("Chờ xác nhận"),
    CONFIRMED("Đã xác nhận"),
    SHIPPED("Đang giao hàng"),
    DELIVERED("Đã giao hàng"),
    CANCELLED("Đã hủy");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    public static boolean isValidOrderStatus(String status) {

        for (OrderStatus orderStatus : OrderStatus.values()) {

            if (orderStatus.name().equalsIgnoreCase(status)) {

                return true;
            }
        }
        return false;
    }
}
