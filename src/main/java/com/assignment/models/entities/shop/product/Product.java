package com.assignment.models.entities.shop.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import com.assignment.models.entities.shop.order.OrderReturn;
import com.assignment.models.entities.shop.store.Store;

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
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(exclude = {"images", "variants", "productViews", "productRatings", "productLikes", "productShares", "productReports", "orderReturns"})
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

    @Column(name = "weight", nullable = false)
    private Integer weight; // trọng lượng sản phẩm(tính theo gram)

    @Column(name = "length", nullable = false)
    private Integer length; // chiều dài sản phẩm(tính theo cm)

    @Column(name = "width", nullable = false)
    private Integer width; // chiều rộng sản phẩm(tính theo cm)

    @Column(name = "height", nullable = false)
    private Integer height; // chiều cao sản phẩm(tính theo cm)

    @Column(name = "view_count", nullable = false)
    private Long viewCount = 0L; // Số lượt xem sản phẩm

    @Column(name = "like_count", nullable = false)
    private Long likeCount = 0L; // Số lượt like sản phẩm

    @Column(name = "share_count", nullable = false)
    private Long shareCount = 0L; // Số lượt chia sẻ sản phẩm

    // số lượt mua sản phẩm
    @Column(name = "buy_count", nullable = false)
    private Long buyCount = 0L;

    //số sản phẩm còn lại
    @Column(name = "quantity", nullable = false)
    private Integer quantity = 0;

    // khoảng giá sản phẩm
    @Column(name = "price_from")
    private Double priceFrom;

    @Column(name = "price_to")
    private Double priceTo;

    // khoảng giá gốc
    @Column(name = "price_org_from")
    private Double priceOrgFrom;

    @Column(name = "price_org_to")
    private Double priceOrgTo;



    // hình ảnh sản phẩm
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<ProductImage> images = new ArrayList<>();

    // biến thể sản phẩm: 1 sản phẩm có nhiều biến thể
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductVariant> variants = new ArrayList<>();

    // video sản phẩm: 1 sản phẩm có 1 video
    @Column(name = "video", nullable = true, columnDefinition = "nvarchar(255)")
    private String video;

    // trạng thái sản phẩm: bị khóa, ẩn, hiện
    @Column(name = "status", nullable = false, columnDefinition = "nvarchar(50)")
    private ProductStatus status = ProductStatus.ACTIVE;

    // lý do khóa sản phẩm
    @Column(name = "reason", nullable = true, columnDefinition = "nvarchar(255)")
    private String reason;

    // giảm giá lên đến %
    @Column(name = "discount", nullable = true)
    private Integer discount;

    // ngày tạo sản phẩm
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // ngày cập nhật gần nhất
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt = LocalDateTime.now();

    // danh mục sản phẩm
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    // sản phẩm được trả tiền để quảng cáo
    @Column(name = "is_advertise")
    private Boolean isAdvertise = false;

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

    // nhiều sản phẩm thuộc về một cửa hàng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;


    // Kết nối với bảng order_return: 1 product có nhiều order_return
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderReturn> orderReturns = new ArrayList<>();

    // add image method
    public void addImage(ProductImage image) {
        this.images.add(image);
        image.setProduct(this);
    }
    
    public void removeImage(ProductImage image) {
        images.remove(image);
        image.setProduct(null);
    }


    // add variants
    public void addVariant(ProductVariant variant) {
        this.variants.add(variant);
        variant.setProduct(this);
    }


    public void updateDiscount() {
        OptionalDouble maxDiscount = this.variants.stream()
            .filter(e -> e.getPrice() > 0)
            .mapToDouble(e -> (e.getPrice() - e.getSalePrice()) / e.getPrice())
            .max();
        if (maxDiscount.isPresent()) {
            this.discount = (int) (maxDiscount.getAsDouble() * 100);
        } else {
            this.discount = 0;
        }
    }

    public void updatePrice() {
        OptionalDouble maxPrice = this.variants.stream()
            .mapToDouble(ProductVariant::getSalePrice)
            .max();
        OptionalDouble minPrice = this.variants.stream()
            .mapToDouble(ProductVariant::getSalePrice)
            .min();
        if (maxPrice.isPresent() && minPrice.isPresent()) {
            this.priceFrom = minPrice.getAsDouble();
            this.priceTo = maxPrice.getAsDouble();
        }

        OptionalDouble maxOrgPrice = this.variants.stream()
            .mapToDouble(ProductVariant::getPrice)
            .max();
        OptionalDouble minOrgPrice = this.variants.stream()
            .mapToDouble(ProductVariant::getPrice)
            .min();
        if (maxOrgPrice.isPresent() && minOrgPrice.isPresent()) {
            this.priceOrgFrom = minOrgPrice.getAsDouble();
            this.priceOrgTo = maxOrgPrice.getAsDouble();
        }
    }




    // toSellDTO
    public ProductSellDTO toSellDTO() {
        return new ProductSellDTO(
        this.id,
        this.name,
        this.thumbnail,
        this.priceFrom,
        this.priceTo,
        this.viewCount,
        this.likeCount, 
        this.shareCount,
        this.buyCount,
        this.status,
        this.reason
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProductSellDTO {
        private Long id;
        private String name;
        private String thumbnail;
        private Double priceFrom;
        private Double priceTo;
        private Long viewCount;
        private Long likeCount;
        private Long shareCount;
        private Long buyCount;
        private ProductStatus status;
        private String reason;
    }

    public ProductEditDTO toEditDTO() {
        return new ProductEditDTO(
            this.id,
            this.name,
            this.thumbnail,
            this.description,
            this.brand,
            this.brandOrigin,
            this.madeIn,
            this.guarantee,
            this.weight,
            this.length,
            this.width,
            this.height,
            this.viewCount,
            this.likeCount,
            this.shareCount,
            this.buyCount,
            this.quantity,
            this.priceFrom,
            this.priceTo,
            this.video,
            this.status,
            this.reason,
            this.createdAt,
            this.updatedAt,
            this.category.getId()
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ProductEditDTO {
        private Long id;
        private String name;
        private String thumbnail;
        private String description;
        private String brand;
        private String brandOrigin;
        private String madeIn;
        private String guarantee;
        private Integer weight;
        private Integer length;
        private Integer width;
        private Integer height;
        private Long viewCount;
        private Long likeCount;
        private Long shareCount;
        private Long buyCount;
        private Integer quantity;
        private Double priceFrom;
        private Double priceTo;
        private String video;
        private ProductStatus status;
        private String reason;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private Long categoryId;
    }

    
    
}
