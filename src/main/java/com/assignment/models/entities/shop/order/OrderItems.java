package com.assignment.models.entities.shop.order;

import com.assignment.models.entities.shop.product.ProductVariant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor  
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItems {

    @Id
    @Column(name = "id")
    private Long id;

    // kết nối với bảng order
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_variant_id")
    private ProductVariant productVariant;

    @Column(name = "quantity")
    private int quantity;

    // giá sản phẩm khi đặt hàng
    @Column(name = "price")
    private Double price;

    // giá sản phẩm khi khuyến mãi
    @Column(name = "sale_price")
    private Double salePrice;

    // giảm giá
    @Column(name = "discount")
    private Double discount;

    // tổng giá sản phẩm
    @Column(name = "total_price")
    private Double totalPrice;
}
