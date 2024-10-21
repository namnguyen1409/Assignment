package com.assignment.models.entities.shop.order;

public enum ReturnStatus {
    PENDING, // chờ xác nhận
    APPROVED, // đã xác nhận
    REJECTED, // đã từ chối
    COMPLETED // đã hoàn thành
}
