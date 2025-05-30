package com.assignment.models.entities.auth;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.assignment.models.entities.ewallet.Ewallet;
import com.assignment.models.entities.shop.cart.Cart;
import com.assignment.models.entities.shop.order.Order;
import com.assignment.models.entities.shop.product.ProductLike;
import com.assignment.models.entities.shop.product.ProductRating;
import com.assignment.models.entities.shop.product.ProductReport;
import com.assignment.models.entities.shop.product.ProductShare;
import com.assignment.models.entities.shop.product.ProductView;
import com.assignment.models.entities.shop.store.Store;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(exclude = {"userDevices", "userRoles", "userLocations", "userPermissions", "store", "searchHistories", "orders", "productViews", "productLikes", "productRatings", "productShares", "productReports", "cart"})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    // Khai báo các trường dữ liệu
    // Khai báo trường id là khóa chính
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    // Khai báo trường username
    @Column(name = "username", nullable = false, unique = true, columnDefinition = "nvarchar(50)")
    private String username;

    // Khai báo trường password
    @Column(name = "password", nullable = false, columnDefinition = "nvarchar(100)")
    private String password;

    @Column(name = "last_update_password", nullable = true, columnDefinition = "datetime")
    private LocalDateTime lastUpdatePassword = LocalDateTime.now();

    // Khai báo trường firstname
    @Column(name = "firstname", nullable = false, columnDefinition = "nvarchar(50)")
    private String firstName;

    // Khai báo trường lastname
    @Column(name = "lastname", nullable = false, columnDefinition = "nvarchar(50)")
    private String lastName;

    // Khai báo trường email
    @Column(name = "email", nullable = false, unique = true, columnDefinition = "nvarchar(100)")
    private String email;

    // Khai báo trường phone
    @Column(name = "phone", nullable = false, unique = true, columnDefinition = "nvarchar(20)")
    private String phone;

    // Khai báo trường gender
    @Column(name = "gender", nullable = false, columnDefinition = "nvarchar(20)")
    private String gender;

    // Khai báo trường birthday
    @Column(name = "birthday", nullable = false)
    private LocalDate birthday = LocalDate.now();

    // Khai báo trường status
    @Column(name = "isEnable", nullable = false, columnDefinition = "bit")
    private boolean isEnable = false;

    // khai báo trường email verified code
    @Column(name = "email_verified_code", nullable = true, columnDefinition = "nvarchar(255)")
    private String emailVerifiedCode;

    // khai báo trường email verified at
    @Column(name = "email_verified_at", nullable = true)
    private LocalDateTime emailVerifiedAt;

    // khai báo trường email verified expired at
    @Column(name = "email_verified_expired_at", nullable = true)
    private LocalDateTime emailVerifiedExpiredAt;

    // khai báo trường is email verified
    @Column(name = "is_email_verified", nullable = false, columnDefinition = "bit")
    private boolean isEmailVerified = false;

    @Column(name = "is_locked", nullable = false, columnDefinition = "bit")
    private boolean isLocked = false;

    // khai báo trường lock reason: lý do tài khoản bị vô hiệu hóa
    @Column(name = "lock_reason", nullable = true, columnDefinition = "nvarchar(255)")
    private String lockReason;

    // khai báo trường totp secret key: dùng để xác thực 2 yếu tố
    @Column(name = "totp_secret_key", nullable = true, columnDefinition = "nvarchar(255)")
    private String totpSecretKey;

    // Khai báo trường createdAt: thời gian tạo user
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(name = "avatar", nullable = true, columnDefinition = "nvarchar(255)")
    private String avatar = "default_avatar.png";

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserDevice> userDevices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserLocation> userLocations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<UserPermission> userPermissions = new ArrayList<>();

    // 1 người dùng có 0 hoặc 1 cửa hàng
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Store store;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<SearchHistory> searchHistories = new ArrayList<>();

    /*
     * Kết nối với những bảng của nhóm shop
     */
    // Kết nối với bảng orders: 1 user có nhiều đơn hàng
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    // Kết nối với bảng product_view: 1 user có nhiều lượt xem
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductView> productViews = new ArrayList<>();

    // Kết nối với bảng product_like: 1 user có nhiều like
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductLike> productLikes = new ArrayList<>();

    // Kết nối với bảng product_rating: 1 user có nhiều đánh giá
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductRating> productRatings = new ArrayList<>();

    // Kết nối với bảng product_share: 1 user có nhiều chia sẻ
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductShare> productShares = new ArrayList<>();

    // Kết nối với bảng product_report: 1 user có nhiều báo cáo
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductReport> productReports = new ArrayList<>();

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Cart cart;

    // kết nối với e-wallet
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Ewallet ewallet;


}
