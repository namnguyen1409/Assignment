package com.assignment.models.entities.shop.store;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.location.District;
import com.assignment.models.entities.location.Province;
import com.assignment.models.entities.location.Ward;
import com.assignment.models.entities.shop.order.Order;
import com.assignment.models.entities.shop.order.OrderReturn;
import com.assignment.models.entities.shop.product.Product;
import com.assignment.models.entities.shop.voucher.Voucher;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@EqualsAndHashCode(exclude = {"products", "orders", "orderReturns"})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "stores")
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "avatar", nullable = false, columnDefinition = "nvarchar(255)")
    private String avatar = "default_avatar.png";

    @Column(name = "cover", nullable = false, columnDefinition = "nvarchar(255)")
    private String cover = "demo.jpg";

    @Column(name = "description", nullable = false, columnDefinition = "nvarchar(500)")
    private String description;

    @Column(name = "home", nullable = true, columnDefinition = "nvarchar(max)")
    private String home;

    @ManyToOne
    @JoinColumn(name = "province_id", nullable = false)
    private Province province;

    @ManyToOne
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

    @ManyToOne
    @JoinColumn(name = "ward_id", nullable = false)
    private Ward ward;

    @Column(name = "detail_address", nullable = false, columnDefinition = "nvarchar(255)")
    private String detailAddress;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status", nullable = false, columnDefinition = "nvarchar(20)")
    private String status = "active"; // trạng thái: active, inactive, deleted

    @Column(name = "created_at", nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt = LocalDateTime.now();

    // danh sách sản phẩm
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    // danh sách order
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Order> orders = new ArrayList<>();

    // danh sách order return
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderReturn> orderReturns = new ArrayList<>();

    // danh sách mã giảm giá được tung ra bởi cửa hàng
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Voucher> vouchers = new ArrayList<>();
    
}
