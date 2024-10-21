/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.assignment.models.entities.shop.product;

import java.time.LocalDateTime;

import com.assignment.models.entities.auth.User;

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
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity 
@Table(name = "product_shares")
public class ProductShare {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(
        name = "share_at",
        nullable = false,
        columnDefinition = "datetime"
    )
    private LocalDateTime shareAt = LocalDateTime.now();

    // số lần click vào link chia sẻ
    @Column(
        name = "click_count",
        nullable = false
    )
    private int clickCount = 0;

    // số lần mua hàng từ link chia sẻ
    @Column(
        name = "purchase_count",
        nullable = false
    )
    private int purchaseCount = 0;
    
    // -> giúp thực hiện affiliate marketing: tiếp thị liên kết
}
