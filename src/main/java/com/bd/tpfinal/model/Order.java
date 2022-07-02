package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;


@Document
public class Order {
	
	@MongoId
	@JsonSerialize(using= ToStringSerializer.class)
    private ObjectId number;
	
	@Field
    private LocalDate dateOfOrder;
	
	@Field
	private String comments;
	
	@Field
    private float totalPrice;
	
	@Field
    private OrderStatus orderStatus;
    
	@DBRef
    private DeliveryMan deliveryMan;
	
	@DBRef
    private Client client;
	
	@DBRef
	private Address address;
	
	@Field
    private Qualification qualification;
	
	@Field
    private List<Item> items = new ArrayList<Item>();
	
	@Version
	private int version;
    
        
    public Order() { /* empty for framework */ } 
    
    public Order(LocalDate dateOfOrder, String comments, float totalPrice, 
				 DeliveryMan deliveryMan, Client client, Address address, 
				 Qualification qualification, OrderStatus orderStatus) {
    	
		this.dateOfOrder = dateOfOrder;
		this.comments = comments;
		this.totalPrice = totalPrice;
		this.orderStatus = new Pending(this);
		this.deliveryMan = null;
		this.client = client;
		this.address = address;
		this.qualification = qualification;
		this.items = new ArrayList<Item>();
	}

	public ObjectId getNumber() {
        return number;
    }
	
	public void setNumber(ObjectId number) {
        this.number = number;
    }
	

    public LocalDate getDateOfOrder() {
        return dateOfOrder;
    }

    public void setDateOfOrder(LocalDate dateOfOrder) {
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

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Qualification getQualification() {
        return qualification;
    }

    public void setQualification(Qualification qualification) {
        this.qualification = qualification;
    }      

	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}

	public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
    
    public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public boolean canAssigned(){ return this.orderStatus.canAssigned(); }
    
    public boolean canCancelled() { return this.orderStatus.canCancelled();}        
      
    public boolean canRefused() { return this.orderStatus.canRefused(); }       
   
    public boolean canDelivered() { return this.orderStatus.canDelivered(); }
   
    public boolean canFinished() { return this.orderStatus.canFinished(); }
     
    public boolean canAddItem() { return this.orderStatus.canAddItem(); }
    
    public boolean isSent() { return (this.orderStatus.isSent()); }    
    
    public void assign(DeliveryMan deliveryMan){ this.orderStatus.assign(deliveryMan); }     
       
    public void cancel(){ this.orderStatus.cancel(); }
     
    public void refuse(){ this.orderStatus.refuse(); }
       
    public void deliver(){ this.orderStatus.deliver(); }  
    
    public void finish(){ this.orderStatus.finish(); }       
       
    public void addItem(Item item){	this.getItems().add(item); }  	
        
          
    /*
     * Debido a la incompatibilidad de Hibernet y JPA con embebeber la clases hijas, una solucion es instanciar
     * el estado de manera manual.
     * La clase que se recupera, si bien es un OrderStatus, no es una clase concreta.
     */
    public void setStatusByName() {
    	
    	switch (orderStatus.getName()) {
    	
    		case "Pending":
    			this.setOrderStatus(new Pending(this, this.orderStatus.getStartDate()));
    			break;    			
    		case "Assigned":
    			this.setOrderStatus(new Assigned(this, this.orderStatus.getStartDate()));
    			break;  
    		case "Sent":
    			this.setOrderStatus(new Sent(this, this.orderStatus.getStartDate()));
    			break; 
    		case"Delivered":
    			this.setOrderStatus(new Delivered(this, this.orderStatus.getStartDate()));
    			break; 
    		case "Cancelled":
    			this.setOrderStatus(new Cancelled(this, this.orderStatus.getStartDate()));
    			break;  	
    	 	
    	}
    }
    	
    }  	

