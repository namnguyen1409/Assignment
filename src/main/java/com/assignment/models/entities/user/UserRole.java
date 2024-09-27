package com.assignment.models.entities.user;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_roles")
public class UserRole {
    
    // Khai báo các trường dữ liệu
    // Khai báo trường id là khóa chính
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    // Khai báo trường user: tham chiếu đến đối tượng User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // Khai báo trường role: tham chiếu đến đối tượng Role
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    // Khai báo trường isActive: trạng thái hoạt động của user role
    @Column(name = "is_active",
            nullable = false
    )
    private boolean isActive = true;

    // Khai báo trường createdAt: thời gian tạo user role
    @Column(name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt = LocalDateTime.now();

    // Khai báo trường disabledReason: lý do vô hiệu hóa user role
    @Column(name = "disabled_reason",
            nullable = true,
            columnDefinition = "nvarchar(255)"
    )
    private String disabledReason;

    public UserRole() {
    }

    public UserRole(long id, User user, Role role, String disabledReason) {
        this.id = id;
        this.user = user;
        this.role = role;
        this.disabledReason = disabledReason;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDisabledReason() {
        return disabledReason;
    }

    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason;
    }


}
