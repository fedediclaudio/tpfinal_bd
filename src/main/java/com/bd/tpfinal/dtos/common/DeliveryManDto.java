package com.bd.tpfinal.dtos.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class DeliveryManDto extends UserDto {

    @JsonProperty(value = "number_of_success_orders")
    private int numberOfSuccessOrders;

    private boolean free;

    @JsonProperty(value = "date_of_admission")
    private Date dateOfAdmission;

    @JsonProperty(value = "orders_pending")
    private List<OrderDto> ordersPending = new ArrayList<>();
    @Builder
    public DeliveryManDto(String id, String name, String username, String password, String email,
                          Date dateOfAdmission, int numberOfSuccessOrders, boolean free, List<OrderDto> ordersPending) {
        super(id, name, username, password, email);
        this.dateOfAdmission = dateOfAdmission;
        this.numberOfSuccessOrders = numberOfSuccessOrders;
        this.free = this.free;
        this.ordersPending = ordersPending;
    }

}
