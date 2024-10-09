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
@Table(name = "products",
    indexes = {
        @Index(name = "idx_products_name", columnList = "name"),
        @Index(name = "idx_products_category_id", columnList = "category_id")
    }
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "thumbnail", nullable = false, columnDefinition = "nvarchar(255)")
    private String thumbnail;

    @Column(name = "description", nullable = false, columnDefinition = "nvarchar(max)")
    private String description; // mô tả sản phẩm

    @Column(name = "brand", nullable = false, columnDefinition = "nvarchar(255)")
    private String brand; // thương hiệu

    @Column(name = "brand_origin", nullable = false, columnDefinition = "nvarchar(255)")
    private String brandOrigin; // xuất xứ thương hiệu

    @Column(name = "made_in", nullable = false, columnDefinition = "nvarchar(255)")
    private String madeIn; // xuất xứ sản phẩm

    @Column(name = "guarantee", nullable = true, columnDefinition = "nvarchar(255)")
    private String guarantee; // bảo hành

    // hình ảnh sản phẩm
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductImage> images = new ArrayList<>();

    // biến thể sản phẩm: 1 sản phẩm có nhiều biến thể
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductVariant> variants = new ArrayList<>();

    // video sản phẩm: 1 sản phẩm có 1 video
    @Column(name = "video", nullable = true, columnDefinition = "nvarchar(255)")
    private String video;

    // danh mục sản phẩm
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // Kết nối với bảng product_view: 1 product có nhiều lượt xem
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductView> productViews = new ArrayList<>();

    // Kết nối với bảng product_rating: 1 product có nhiều đánh giá
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductRating> productRatings = new ArrayList<>();

    // Kết nối với bảng product_like: 1 product có nhiều like
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductLike> productLikes = new ArrayList<>();

    // Kết nối với bảng product_share: 1 product có nhiều chia sẻ
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductShare> productShares = new ArrayList<>();

    // Kết nối với bảng product_report: 1 product có nhiều báo cáo
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductReport> productReports = new ArrayList<>();

}
