/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.assignment.models.entities.shop.product;

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
@Table(name = "product_variants")
class ProductVariant {

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
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // giá gốc của sản phẩm
    private Double price;

    // giá khuyến mãi của sản phẩm
    private Double salePrice;

    // số lượng sản phẩm
    private Integer quantity;

}
