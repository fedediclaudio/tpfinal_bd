package com.bd.tpfinal.dtos.request;

public class OrderRequestDto<T> {
    private T clientId;
    private T orderId;

    public T getClientId() {
        return clientId;
    }

    public void setClientId(T clientId) {
        this.clientId = clientId;
    }

    public T getOrderId() {
        return orderId;
    }

    public void setOrderId(T orderId) {
        this.orderId = orderId;
    }
}
