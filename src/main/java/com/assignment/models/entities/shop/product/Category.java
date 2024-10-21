package com.assignment.models.entities.shop.product;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories",
    indexes = {
        @Index(name = "idx_categories_name", columnList = "name"),
        @Index(name = "idx_categories_parent_id", columnList = "parent_id")
    }
)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;



    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent", cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Category> children = new ArrayList<>();

    // danh sách sản phẩm thuộc danh mục
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();


    @Column(name = "image", columnDefinition = "nvarchar(255)")
    private String image = "default.png";
    
    public CategoryDTO toDTO() {
        List<CategoryDTO> childDTOs = new ArrayList<>();
        for (Category child : children) {
            childDTOs.add(child.toDTO());
        }
        return new CategoryDTO(this.id, this.name, childDTOs);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CategoryDTO {
        private Long id;
        private String name;
        private List<CategoryDTO> children;
    }
    
}
