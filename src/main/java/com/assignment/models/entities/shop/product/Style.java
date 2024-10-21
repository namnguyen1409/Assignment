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
@Table(name = "styles")
public class Style {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "image", nullable = false)
    private String image;

    // kêt nối với bảng variant
    @OneToMany(mappedBy = "style")
    private List<ProductVariant> productVariants;

    public StyleDTO toDTO() {
        return new StyleDTO(this.id, this.name, this.image);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public class StyleDTO {
        private Long id;
        private String name;
        private String image;
    }


}
