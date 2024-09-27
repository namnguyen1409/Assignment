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
@Table(name = "user_permissions")
public class UserPermission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;

    @Column(name = "is_active",
            nullable = false,
            columnDefinition = "bit"
    )
    private boolean isActive = true;

    // Khai báo trường createdAt: thời gian tạo user permission
    @Column(name = "created_at",
            nullable = false
    )
    private LocalDateTime createdAt = LocalDateTime.now();

    // Khai báo trường disabledReason: lý do vô hiệu hóa user permission
    @Column(name = "disabled_reason",
            nullable = true,
            columnDefinition = "nvarchar(255)"
    )
    private String disabledReason;

    public UserPermission() {
    }

    public UserPermission(long id, User user, Permission permission, boolean isActive, LocalDateTime createdAt, String disabledReason) {
        this.id = id;
        this.user = user;
        this.permission = permission;
        this.isActive = isActive;
        this.createdAt = createdAt;
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

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
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
