package com.assignment.models.entities.staff;

import java.util.List;

import com.assignment.models.entities.auth.User;
import com.assignment.models.entities.shop.product.ProductReport;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// người kiểm duyệt
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "moderators")
public class Moderators {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    // lương của người kiểm duyệt
    @JoinColumn(name = "salary")
    private Double salary;
    
    // danh sách các báo cáo sản phẩm
    @OneToMany(mappedBy = "moderator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductReport> productReports;
    
}
