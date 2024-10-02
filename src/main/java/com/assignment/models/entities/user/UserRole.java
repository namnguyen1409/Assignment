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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}
