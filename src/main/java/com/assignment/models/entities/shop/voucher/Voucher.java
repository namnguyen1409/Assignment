package com.assignment.models.entities.shop.voucher;

import java.time.LocalDate;

import com.assignment.models.entities.shop.store.Store;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vouchers")
public class Voucher {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // vourcher có thể thuộc về 1 cửa hàng hoặc null: voucher toàn sàn (áp dụng cho
    // tất cả cửa hàng)
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    @Column(name = "type", nullable = false)
    private VoucherType type; // loại voucher 1: giảm phí vận chuyển, 2: giảm giá đơn hàng

    @Column(name = "discount", nullable = false)
    private int discount; // phần trăm giảm giá

    @Column(name = "max_discount", nullable = false)
    private Double maxDiscount; // giá trị giảm giá tối đa (đơn vị: VND)

    @Column(name = "min_order_value", nullable = false)
    private Double minOrderValue; // giá trị đơn hàng tối thiểu để sử dụng voucher (đơn vị: VND)

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate; // ngày bắt đầu sử dụng voucher

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate; // ngày kết thúc sử dụng voucher

    @Column(name = "quantity", nullable = false)
    private Integer quantity; // số lượng voucher có thể sử dụng

    @Column(name = "status", nullable = false)
    private Boolean status = true; // trạng thái voucher (true: còn hiệu lực, false: hết hiệu lực)

    @Column(name = "description", nullable = true, columnDefinition = "nvarchar(500)")
    private String description; // mô tả voucher

    public void defaultDescription() {
        if (this.type == VoucherType.SHIPPING) {
            this.description = """
                    Giảm %d phí vận chuyển, tối đa %,.0f VND
                    Áp dụng cho đơn hàng từ %,.0f VND trở lên
                    Thời gian sử dụng: %s - %s
                    """.formatted(this.discount, this.maxDiscount, this.minOrderValue, this.startDate, this.endDate);
        } else {
            this.description = """
                    Giảm %d phần trăm, tối đa %,.0f VND
                    Áp dụng cho đơn hàng từ %,.0f VND trở lên
                    Thời gian sử dụng: %s - %s
                    """.formatted(this.discount, this.maxDiscount, this.minOrderValue, this.startDate, this.endDate);
        }
    }

    public boolean isValid() {
        LocalDate today = LocalDate.now();
        return status && quantity > 0 && today.isAfter(startDate) && today.isBefore(endDate);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VoucherDTO {
        private Long id;
        private String code;
        private VoucherType type;
        @Min(value = 1, message = "Phần trăm giảm giá phải lớn hơn 0")
        @Max(value = 100, message = "Phần trăm giảm giá phải nhỏ hơn 100")
        private int discount;
        @DecimalMin(value = "0", message = "Giá trị giảm giá tối đa phải lớn hơn hoặc bằng 0")
        private Double maxDiscount;
        @DecimalMin(value = "0", message = "Giá trị đơn hàng tối thiểu phải lớn hơn hoặc bằng 0")
        private Double minOrderValue;
        private LocalDate startDate;
        @Future(message = "Ngày kết thúc phải sau ngày hiện tại")
        private LocalDate endDate;
        @Min(value = 1, message = "Số lượng voucher phải lớn hơn 0")
        private Integer quantity;
        private Boolean status;
        @Size(max = 500, message = "Mô tả voucher tối đa 500 ký tự")
        private String description;
    }

    public VoucherDTO toDTO() {
        return new VoucherDTO(id, code, type, discount, maxDiscount, minOrderValue, startDate, endDate, quantity,
                status, description);
    }

}
