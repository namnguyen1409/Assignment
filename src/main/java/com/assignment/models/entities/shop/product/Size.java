/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.assignment.models.entities.shop.product;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@ToString(exclude = "productVariants")
@Table(name = "sizes")
public class Size {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name; // Ví dụ: S, M, L, XL

    // kết nối với bảng variant
    @OneToMany(mappedBy = "size")
    private List<ProductVariant> productVariants;

    public SizeDTO toDTO() {
        return new SizeDTO(this.id, this.name);
    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class SizeDTO {
        private Long id;
        private String name;
    }
}