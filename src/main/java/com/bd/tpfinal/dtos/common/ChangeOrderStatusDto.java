package com.bd.tpfinal.dtos.common;

import com.bd.tpfinal.enums.OrderStatusAction;

public class ChangeOrderStatusDto {
    private String orderId;
    private OrderStatusAction status;
    private Boolean canceledByClient;


    public ChangeOrderStatusDto(String orderId, String statusName) {
        this.orderId = orderId;
        this.status = OrderStatusAction.valueOf(statusName);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderStatusAction getStatus() {
        return status;
    }

    public void setStatus(OrderStatusAction status) {
        this.status = status;
    }

    public Boolean getCanceledByClient() {
        return canceledByClient;
    }

    public void setCanceledByClient(Boolean canceledByClient) {
        this.canceledByClient = canceledByClient;
    }

}
