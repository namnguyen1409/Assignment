package com.assignment.models.entities.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
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
    @Column(
            name = "id"
    )
    private long id;

    // Khai báo trường username
    @Column(
            name = "username",
            nullable = false,
            unique = true,
            columnDefinition = "nvarchar(50)"
    )
    private String username;

    // Khai báo trường password
    @Column(
            name = "password",
            nullable = false,
            columnDefinition = "nvarchar(100)"
    )
    private String password;

    // Khai báo trường firstname
    @Column(
            name = "firstname",
            nullable = false,
            columnDefinition = "nvarchar(50)"
    )
    private String firstName;

    // Khai báo trường lastname
    @Column(
            name = "lastname",
            nullable = false,
            columnDefinition = "nvarchar(50)"
    )
    private String lastName;

    // Khai báo trường email
    @Column(
            name = "email",
            nullable = false,
            unique = true,
            columnDefinition = "nvarchar(100)"
    )
    private String email;

    // Khai báo trường phone
    @Column(
            name = "phone",
            nullable = false,
            unique = true,
            columnDefinition = "nvarchar(20)"
    )
    private String phone;

    // Khai báo trường gender
    @Column(
            name = "gender",
            nullable = false,
            columnDefinition = "nvarchar(20)"
    )
    private String gender;

    // Khai báo trường birthday
    @Column(
            name = "birthday",
            nullable = false
    )
    private LocalDate birthday = LocalDate.now();

    // Khai báo trường status
    @Column(
            name = "status",
            nullable = false,
            columnDefinition = "nvarchar(50)"
    )
    private String status = "active";

    // khai báo trường blockReason: lý do block user
    @Column(
            name = "block_reason",
            nullable = true,
            columnDefinition = "nvarchar(255)"
    )
    private String blockReason;

    // khai báo trường email verified code
    @Column(
            name = "email_verified_code",
            nullable = true,
            columnDefinition = "nvarchar(255)"
    )
    private String emailVerifiedCode;

    // khai báo trường email verified at
    @Column(
            name = "email_verified_at",
            nullable = true
    )
    private LocalDateTime emailVerifiedAt;

    // khai báo trường email verified expired at
    @Column(
            name = "email_verified_expired_at",
            nullable = true
    )
    private LocalDateTime emailVerifiedExpiredAt;

    // khai báo trường is email verified
    @Column(
            name = "is_email_verified",
            nullable = false,
            columnDefinition = "bit"
    )
    private boolean isEmailVerified = false;

    // khai báo trường totp secret key: dùng để xác thực 2 yếu tố
    @Column(
            name = "totp_secret_key",
            nullable = true,
            columnDefinition = "nvarchar(255)"
    )
    private String totpSecretKey;

    // khai báo trường totp verified at
    @Column(
            name = "totp_verified_at",
            nullable = true
    )
    private LocalDateTime totpVerifiedAt;

    // Khai báo trường createdAt: thời gian tạo user
    @Column(
            name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt = LocalDateTime.now();

    @Column(
            name = "avatar",
            nullable = true,
            columnDefinition = "nvarchar(255)"
    )
    private String avatar;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<UserDevice> UserDevices = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<UserRole> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<UserLocation> userLocations = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch=FetchType.LAZY)
    private List<UserPermission> userPermissions = new ArrayList<>();
}
