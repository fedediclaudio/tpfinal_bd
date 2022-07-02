package com.bd.tpfinal.model;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonIgnore;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.bd.tpfinal.utils.DeliveryException;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


public abstract class OrderStatus {
	
	@MongoId
	@JsonSerialize(using= ToStringSerializer.class)
    private ObjectId id;
	
	@Field 
	private String name;
	
	@Field
	protected LocalDate startDate;
	
	@DBRef
	protected Order order;    
	
	public OrderStatus(){ /* empty for framework */ }

    public OrderStatus(Order order, String name) {
        this.order = order;
        this.name = name;
        this.startDate = LocalDate.now();
    }        
	public OrderStatus(Order order, String name, LocalDate startDate) {
		this.name = name;
		this.startDate = startDate;
		this.order = order;
	}
	
	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public boolean canAddItem() { return false; }

    public boolean canAssigned() { return false; }

    public boolean canRefused() { return false; }

    public boolean canDelivered() { return false; }

    public boolean canFinished() { return false; }

    public boolean canCancelled() { return false; }
    
    public boolean isSent() { return (this.name == "Sent");}    
    
    public boolean addItem(Item item){
        throw new DeliveryException("This action can't be performed");
    }

    public boolean assign(DeliveryMan deliveryMan){
        throw new DeliveryException("The order can't be assigned");
    }

    public boolean refuse(){
        throw new DeliveryException("The order can't be refused");
    }

    public boolean deliver(){
        throw new DeliveryException("The order can't be delivered");
    }

    public boolean cancel(){
        throw new DeliveryException("The order can't be cancelled");
    }

    public boolean finish(){
        throw new DeliveryException("The order can't be finish");
    }

    
}
