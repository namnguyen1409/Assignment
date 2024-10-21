package com.assignment.models.entities.ewallet;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.assignment.models.entities.auth.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ewallet")
public class Ewallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // số dư trong ví
    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    // lịch sử giao dịch
    @OneToMany(mappedBy = "ewallet", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TransactionHistory> transactionHistories = new ArrayList<>();

    // ngân hàng: 1 ví liên kết được nhiều ngân hàng
    @OneToMany(mappedBy = "ewallet", cascade= CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BankEwallet> bankEwallets = new ArrayList<>();

    // mã pin của ví (mã 6 chữ số được mã hóa bằng bcrypt)
    @Column(name = "pin", columnDefinition = "nvarchar(255)")
    private String pin;

    public void addBalance(double amount, TransactionType type, String content) {
        this.balance = this.balance.add(BigDecimal.valueOf(amount));
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(BigDecimal.valueOf(amount));
        transactionHistory.setBalance(this.balance);
        transactionHistory.setContent(content);
        transactionHistory.setTransactionCode(generateTransactionCode());
        transactionHistory.setType(type);
        transactionHistory.setEwallet(this);
        this.transactionHistories.add(transactionHistory);
    }


    public void addBalance(double amount, double fee, TransactionType type, String content) {
        this.balance = this.balance.add(BigDecimal.valueOf(amount));
        this.balance = this.balance.subtract(BigDecimal.valueOf(fee));
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(BigDecimal.valueOf(amount));
        transactionHistory.setBalance(this.balance);
        transactionHistory.setContent(content);
        transactionHistory.setTransactionCode(generateTransactionCode());
        transactionHistory.setType(type);
        transactionHistory.setFee(BigDecimal.valueOf(fee));
        transactionHistory.setEwallet(this);
        this.transactionHistories.add(transactionHistory);
    }

    
    public void subtractBalance(double amount, TransactionType type, String content) {
        if (this.balance.compareTo(BigDecimal.valueOf(amount)) < 0) {
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setAmount(BigDecimal.valueOf(amount).negate());
            transactionHistory.setBalance(this.balance);
            transactionHistory.setContent("Số dư không đủ để thực hiện giao dịch." + content);
            transactionHistory.setTransactionCode(generateTransactionCode());
            transactionHistory.setType(type);
            transactionHistory.setEwallet(this);
            transactionHistory.setStatus(false); // giao dịch thất bại
            this.transactionHistories.add(transactionHistory);
            return;
        }
        this.balance = this.balance.subtract(BigDecimal.valueOf(amount));
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(BigDecimal.valueOf(amount).negate());
        transactionHistory.setBalance(this.balance);
        transactionHistory.setContent(content);
        transactionHistory.setTransactionCode(generateTransactionCode());
        transactionHistory.setType(type);
        transactionHistory.setEwallet(this);
        this.transactionHistories.add(transactionHistory);
    }

    public void subtractBalance(double amount, double fee, TransactionType type, String content) {
        if (this.balance.compareTo(BigDecimal.valueOf(amount).add(BigDecimal.valueOf(fee))) < 0) {
            TransactionHistory transactionHistory = new TransactionHistory();
            transactionHistory.setAmount(BigDecimal.valueOf(amount).negate());
            transactionHistory.setBalance(this.balance);
            transactionHistory.setContent("Số dư không đủ để thực hiện giao dịch." + content);
            transactionHistory.setTransactionCode(generateTransactionCode());
            transactionHistory.setType(type);
            transactionHistory.setEwallet(this);
            transactionHistory.setStatus(false); // giao dịch thất bại
            this.transactionHistories.add(transactionHistory);
            return;
        }
        this.balance = this.balance.subtract(BigDecimal.valueOf(amount)).subtract(BigDecimal.valueOf(fee));
        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setAmount(BigDecimal.valueOf(amount).negate());
        transactionHistory.setBalance(this.balance);
        transactionHistory.setContent(content);
        transactionHistory.setTransactionCode(generateTransactionCode());
        transactionHistory.setType(type);
        transactionHistory.setFee(BigDecimal.valueOf(fee));
        transactionHistory.setEwallet(this);
        this.transactionHistories.add(transactionHistory);
    }
    
    public String generateTransactionCode() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = dateFormat.format(new Date());
        int randomNumber = new Random().nextInt(900000) + 100000;
        return "TXN-" + timestamp + "-" + randomNumber;
    }

}
