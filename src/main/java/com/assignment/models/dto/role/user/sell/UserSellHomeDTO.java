package com.assignment.models.dto.role.user.sell;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSellHomeDTO {
    private Long orderPending;
    private Long orderPendingDelivery;
    private Long orderDelivering;
    private Long orderCancelled;
    private Long productInactive;
    private Long productOutOfStock;
    private Long orderReturnPending;

}
