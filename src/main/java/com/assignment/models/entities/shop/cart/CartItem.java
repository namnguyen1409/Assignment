package com.assignment.models.entities.shop.cart;

import com.assignment.models.entities.shop.product.ProductVariant;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;    
    
    // kết nối với bảng cart
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    // kết nối với bảng product varianr: người dùng chọn kiểu sản phẩm chứ không phải sản phẩm
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    // số lượng của kiểu sản phẩm
    private int quantity;
}
