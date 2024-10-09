package com.assignment.models.entities.shop.product;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.assignment.models.entities.auth.User;

import jakarta.persistence.Column;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_ratings")
public class ProductRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "comment", columnDefinition = "nvarchar(max)")
    private String comment;

    @Column(name = "created_at", nullable = false, columnDefinition = "datetime")
    private LocalDateTime createdAt = LocalDateTime.now();

    // cho phép người dùng up nhiều ảnh
    @OneToMany(mappedBy = "productRating", fetch = FetchType.LAZY)
    private List<ProductRatingImage> images = new ArrayList<>();

    // cho phép người dùng 1 video
    @Column(
        name = "video",
        nullable = true,
        columnDefinition = "nvarchar(255)"
    )
    private String video;

    // cho phép người bán phản hồi
    @Column(
        name = "response",
        nullable = true,
        columnDefinition = "nvarchar(max)"
    )
    private String response;

    // ngày đánh giá
    @Column(
        name = "reviewed_at",
        nullable = false,
        columnDefinition = "datetime"
    )
    private LocalDateTime reviewedAt = LocalDateTime.now();

    // ngày phản hồi
    @Column(
        name = "responded_at",
        nullable = true,
        columnDefinition = "datetime"
    )
    private LocalDateTime respondedAt;

}
