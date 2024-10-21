package com.assignment.models.entities.shop.order;

import com.assignment.models.entities.shop.product.Product;
import com.assignment.models.entities.shop.store.Store;

import jakarta.persistence.Column;
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
@Table(name = "order_returns")
public class OrderReturn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // liên kết cưa hàng: mối quan hệ nhiều - một
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    // liên kết với đơn hàng: mối quan hệ nhiều - một
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
    
    // sản phẩm trả về: mối quan hệ nhiều - một
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    // số lượng sản phẩm trả về
    @Column(name = "quantity", nullable = false)
    private int quantity;

    // lý do trả hàng
    @Column(name = "reason", nullable = false, columnDefinition = "nvarchar(255)")
    private String reason;


    // trạng thái của đơn hàng
    @Column(name = "status")
    private ReturnStatus returnStatus = ReturnStatus.PENDING; // mặc định là đang chờ xử lý

    
    
}
