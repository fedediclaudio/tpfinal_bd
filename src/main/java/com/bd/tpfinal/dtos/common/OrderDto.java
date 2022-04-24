package com.bd.tpfinal.dtos.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Data
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class OrderDto {
    private String id;
    private int number;
    @JsonProperty("date_of_order")
    private Date dateOfOrder;
    private String comments;
    @JsonProperty("total_price")
    private float totalPrice;
    private String status;
    private ClientDto client;
    private AddressDto address;
    @JsonProperty("qualification_score")
    private float qualificationScore;
    @JsonProperty("qualification_comments")
    private String qualificationComments;
    private List<ItemDto> items = new ArrayList<>();


}
