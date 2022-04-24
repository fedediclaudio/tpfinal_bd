package com.bd.tpfinal.dtos.common;

import com.bd.tpfinal.enums.OrderStatusAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ChangeOrderStatusDto {
    private String orderId;
    private OrderStatusAction status;
    private Boolean canceledByClient;

}
