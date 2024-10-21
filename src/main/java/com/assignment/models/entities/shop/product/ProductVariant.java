/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.assignment.models.entities.shop.product;

import java.util.ArrayList;
import java.util.List;

import com.assignment.models.entities.shop.order.OrderItems;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_variants")
public class ProductVariant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // kết nối với size của sản phẩm
    @ManyToOne
    @JoinColumn(name = "size_id")
    private Size size;

    // kết nối với kiểu san phẩm
    @ManyToOne
    @JoinColumn(name = "style_id")
    private Style style;

    // kết nối với sản phẩm
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // giá gốc của sản phẩm
    private Double price;

    // giá khuyến mãi của sản phẩm
    private Double salePrice;

    // số lượng sản phẩm
    private Integer quantity;

    // Kết nối với bảng order_items: 1 product có nhiều order_items
    @OneToMany(mappedBy = "productVariant", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<OrderItems> orderItems = new ArrayList<>();


    public ProductVariantDTO toDTO() {
        var dto = new ProductVariantDTO();
        dto.setId(this.id);
        if (this.size != null) {
            dto.setSize(this.size.toDTO());
        }
        if (this.style != null) {
            dto.setStyle(this.style.toDTO());
        }
        dto.setPrice(this.price);
        dto.setSalePrice(this.salePrice);
        dto.setQuantity(this.quantity);
        return dto;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class ProductVariantDTO {
        private Long id;
        private Size.SizeDTO size;
        private Style.StyleDTO style;
        private Double price;
        private Double salePrice;
        private Integer quantity;
    }

}
