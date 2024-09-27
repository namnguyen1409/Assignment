package com.assignment.models.entities.user;

import java.time.LocalDateTime;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

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

    // Khai báo trường fullname
    @Column(
            name = "fullname",
            nullable = false,
            columnDefinition = "nvarchar(100)"
    )
    private String fullname;

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
    private LocalDateTime birthday = LocalDateTime.now();

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

    // liên kết với bảng roles thông qua bảng user_roles (nhiều - nhiều)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserLocation> userLocations;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserPermission> userPermissions;

    public User() {
    }

    public User(long id, String username, String password, String fullname, String email, String phone, String gender, String blockReason, String emailVerifiedCode, LocalDateTime emailVerifiedAt, LocalDateTime emailVerifiedExpiredAt, String totpSecretKey, LocalDateTime totpVerifiedAt, String avatar, Set<UserRole> userRoles, Set<UserLocation> userLocations, Set<UserPermission> userPermissions) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.blockReason = blockReason;
        this.emailVerifiedCode = emailVerifiedCode;
        this.emailVerifiedAt = emailVerifiedAt;
        this.emailVerifiedExpiredAt = emailVerifiedExpiredAt;
        this.totpSecretKey = totpSecretKey;
        this.totpVerifiedAt = totpVerifiedAt;
        this.avatar = avatar;
        this.userRoles = userRoles;
        this.userLocations = userLocations;
        this.userPermissions = userPermissions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBlockReason() {
        return blockReason;
    }

    public void setBlockReason(String blockReason) {
        this.blockReason = blockReason;
    }

    public String getEmailVerifiedCode() {
        return emailVerifiedCode;
    }

    public void setEmailVerifiedCode(String emailVerifiedCode) {
        this.emailVerifiedCode = emailVerifiedCode;
    }

    public LocalDateTime getEmailVerifiedAt() {
        return emailVerifiedAt;
    }

    public void setEmailVerifiedAt(LocalDateTime emailVerifiedAt) {
        this.emailVerifiedAt = emailVerifiedAt;
    }

    public LocalDateTime getEmailVerifiedExpiredAt() {
        return emailVerifiedExpiredAt;
    }

    public void setEmailVerifiedExpiredAt(LocalDateTime emailVerifiedExpiredAt) {
        this.emailVerifiedExpiredAt = emailVerifiedExpiredAt;
    }

    public boolean isIsEmailVerified() {
        return isEmailVerified;
    }

    public void setIsEmailVerified(boolean isEmailVerified) {
        this.isEmailVerified = isEmailVerified;
    }

    public String getTotpSecretKey() {
        return totpSecretKey;
    }

    public void setTotpSecretKey(String totpSecretKey) {
        this.totpSecretKey = totpSecretKey;
    }

    public LocalDateTime getTotpVerifiedAt() {
        return totpVerifiedAt;
    }

    public void setTotpVerifiedAt(LocalDateTime totpVerifiedAt) {
        this.totpVerifiedAt = totpVerifiedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public Set<UserLocation> getUserLocations() {
        return userLocations;
    }

    public void setUserLocations(Set<UserLocation> userLocations) {
        this.userLocations = userLocations;
    }

    public Set<UserPermission> getUserPermissions() {
        return userPermissions;
    }

    public void setUserPermissions(Set<UserPermission> userPermissions) {
        this.userPermissions = userPermissions;
    }

}
