package com.assignment.models.entities.ewallet;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
@Table(name = "transaction_history")
public class TransactionHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // id của ví
    @ManyToOne
    @JoinColumn(name = "ewallet_id", nullable = false)
    private Ewallet ewallet;

    // số tiền giao dịch
    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    // loại giao dịch
    @Column(name = "type", nullable = false)
    private TransactionType type;

    // thời gian giao dịch
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    // mã giao dịch
    @Column(name = "transaction_code", nullable = false, columnDefinition = "nvarchar(255)")
    private String transactionCode;

    // phí giao dịch
    @Column(name = "fee", nullable = false)
    private BigDecimal fee = BigDecimal.ZERO;

    // số dư sau giao dịch
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    // nội dung giao dịch
    @Column(name = "content", nullable = false, columnDefinition = "nvarchar(255)")
    private String content;

    // trạng thái giao dịch
    @Column(name = "status", nullable = false)
    private boolean status = true; // true: thành công, false: thất bại


    
}
