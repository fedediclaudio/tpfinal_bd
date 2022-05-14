package com.bd.tpfinal.dtos.request.orders;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CreateOrderRequest {
    @JsonProperty("client_id")
    private String clientId;
    @JsonProperty("address_id")
    private String addressId;
    private String comments;
}
