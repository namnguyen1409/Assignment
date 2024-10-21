package com.assignment.models.entities.ewallet;

import java.time.LocalDate;

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
@Table(name = "bank_ewallet")
public class BankEwallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // kết nối với ngân hàng
    @ManyToOne
    @JoinColumn(name = "bank_id", nullable = false)
    private Bank bank;

    // kết nối với ví
    @ManyToOne
    @JoinColumn(name = "ewallet_id", nullable = false)
    private Ewallet ewallet;

    // số tài khoản
    @Column(name = "account_number", nullable = false, columnDefinition = "nvarchar(50)")
    private String accountNumber;

    // tên chủ tài khoản
    @Column(name = "account_name", nullable = false, columnDefinition = "nvarchar(255)")
    private String accountName;

    // ngày hết hạn
    @Column(name = "expired_date", nullable = false, columnDefinition = "nvarchar(50)")
    private String expiredDate;

    // trạng thái
    @Column(name = "status", nullable = false)
    private boolean status = true;
    
}
