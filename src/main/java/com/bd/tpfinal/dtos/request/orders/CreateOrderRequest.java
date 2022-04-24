package com.bd.tpfinal.dtos.request.orders;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateOrderRequest {
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("order_id")
    private String orderId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
