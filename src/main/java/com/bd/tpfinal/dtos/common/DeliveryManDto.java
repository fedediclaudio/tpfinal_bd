package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeliveryManDto extends UserDto {

    @JsonProperty(value = "number_of_success_orders")
    private int numberOfSuccessOrders;

    @JsonProperty(value = "score")
    private int score;
    private boolean free;

    @JsonProperty(value = "date_of_admission")
    private Date dateOfAdmission;

    @JsonProperty(value = "pending_order")
    private OrderDto pendingOrder;

    @Builder
    public DeliveryManDto(String id, String name, String username, String password, String email,
                          Date dateOfAdmission, int numberOfSuccessOrders, boolean free, OrderDto pendingOrder, int score) {
        super(id, name, username, password, email);
        this.dateOfAdmission = dateOfAdmission;
        this.numberOfSuccessOrders = numberOfSuccessOrders;
        this.free = free;
        this.pendingOrder = pendingOrder;
        this.score = score;
    }

}
