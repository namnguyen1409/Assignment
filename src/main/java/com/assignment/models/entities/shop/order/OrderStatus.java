package com.assignment.models.entities.shop.order;
public enum OrderStatus {
    PENDING, // chờ xác nhận
    CONFIRMED, // đã xác nhận
    PENDING_DELIVERY, // chờ giao hàng
    DELIVERING, // đang giao hàng
    DELIVERED, // đã giao hàng
    CANCELLED, // đã hủy
    RETURNED, // đã trả hàng
    REFUNDED, // đã hoàn tiền
    COMPLETED // đã hoàn thành
}
