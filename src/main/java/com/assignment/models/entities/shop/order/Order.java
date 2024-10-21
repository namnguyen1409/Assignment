package com.assignment.models.entities.shop.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.auth.UserLocation;
import com.assignment.models.entities.shop.store.Store;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // một người dùng có thể có nhiều đơn hàng
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // địa chỉ người nhận hàng: userlocation - mối quan hệ 1 đơn hàng chỉ thuộc về một địa chỉ
    @ManyToOne
    @JoinColumn(name = "user_location_id")
    private UserLocation userLocation;

    // nhiều đơn hàng thuộc về một cửa hàng
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    // tổng giá trị đơn hàng
    @Column(name = "total_quantity")
    private int totalQuantity;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "order_at") //thời gian đặt hàng
    private LocalDateTime orderAt = LocalDateTime.now();

    // trạng thái của đơn hàng
    @Column(name = "status")
    private OrderStatus status = OrderStatus.PENDING; // mặc định là đang chờ xử lý

    // thời gian xác nhận đơn hàng
    @Column(name = "confirm_at")
    private LocalDateTime confirmAt = null;

    // thời gian giao hàng
    @Column(name = "deliver_at")
    private LocalDateTime deliverAt = null;

    // thời gian hủy đơn hàng
    @Column(name = "cancel_at")
    private LocalDateTime cancelAt = null;

    // thời gian hoàn thành đơn hàng
    @Column(name = "complete_at")
    private LocalDateTime completeAt = null;

    // thời gian đơn hàng bị trả lại
    @Column(name = "return_at")
    private LocalDateTime returnAt = null;
    // kết nối với bảng order_items: mối quan hệ 1-n
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItems> orderItems = new ArrayList<>();

    // kết nối với bảng order_return: mối quan hệ 1-n
    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderReturn> orderReturns = new ArrayList<>();
}
