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
    
}
