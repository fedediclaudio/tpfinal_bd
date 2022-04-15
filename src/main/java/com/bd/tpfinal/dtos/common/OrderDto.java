package com.bd.tpfinal.dtos.common;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public OrderDto() {
    }

    public OrderDto(int number, Date dateOfOrder, String comments, float totalPrice, String status) {
        this.number = number;
        this.dateOfOrder = dateOfOrder;
        this.comments = comments;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(Date dateOfOrder) {
        this.dateOfOrder = dateOfOrder;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setClient(ClientDto clientDto) {
        this.client = clientDto;
    }

    public ClientDto getClient() {
        return client;
    }

    public AddressDto getAddress() {
        return address;
    }

    public void setAddress(AddressDto address) {
        this.address = address;
    }

    public float getQualificationScore() {
        return qualificationScore;
    }

    public void setQualificationScore(float qualificationScore) {
        this.qualificationScore = qualificationScore;
    }

    public String getQualificationComments() {
        return qualificationComments;
    }

    public void setQualificationComments(String qualificationComments) {
        this.qualificationComments = qualificationComments;
    }

    public List<ItemDto> getItems() {
        return items;
    }

    public void setItems(List<ItemDto> items) {
        this.items = items;
    }

    public void addItem(ItemDto item){
        this.items.add(item);
    }
}
