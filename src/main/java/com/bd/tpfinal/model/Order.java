package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bd.tpfinal.model.orderStatusTypes.Cancel;
import com.bd.tpfinal.model.orderStatusTypes.Delivered;
import com.bd.tpfinal.model.orderStatusTypes.Sent;

@Document(collection = "Order")
public class Order {

	@Id
	private String id;
	
    private String number;

    private LocalDate dateOfOrder;

    private String comments;

    private float totalPrice;

    private OrderStatus status;

    @DBRef(lazy = true)
    private DeliveryMan deliveryMan;

    private Client client;

    private Address address;

    private Qualification qualification;

    private List<Item> items;

    @Version
    private int version;

    private int getPositiveId() {
    	int id = new Random().nextInt();
    	while (id <= 0) id = new Random().nextInt();
    	return id;
    }
    
    public Order() {
    	this.number = String.valueOf( this.getPositiveId() );
    	this.dateOfOrder = LocalDate.now();
    	// Inicializo la lista de Items
        this.items = new ArrayList<Item>();
    }

    public Order(Client client) {
    	this.number = String.valueOf( this.getPositiveId() );
        this.dateOfOrder = LocalDate.now();
        this.client = client;

        // Inicializo la lista de Items
        this.items = new ArrayList<Item>();
    }


    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
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

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    /////////////////////////
    // Metodos adicionales //
    /////////////////////////

    public boolean addItem(Item item) throws Exception {
        if (this.status.canAddItem()) {
            this.items.add(item);
            return true;
        }
        return false;
    }

    //////////////////////////////////////
    // Cambio de estado de las ordenes  //
    //////////////////////////////////////
    public void cancelOrder() throws Exception {
        this.status = new Cancel(this);
    }

    public void refuseOrder() throws Exception {
        this.status = new Cancel(this);
    }

    public void deliverOrder() throws Exception {
        this.status = new Sent(this);
    }

    public void finishOrder() throws Exception {
        this.status = new Delivered(this);
    }

    //////////////////////////////////////
    // FIN Cambio de estado de las ordenes  //
    //////////////////////////////////////


    public void deductClientScore() throws Exception {
        this.client.deductScore();
    }

    public void addClientScore() throws Exception {
        this.client.addScore();
    }

    public void setDeliveryManBusyTo(boolean isBusy) {
        this.deliveryMan.setFree(!isBusy);
    }

    public void deductDeliveryManScore() throws Exception {
        this.deliveryMan.deductScore();
    }

    public void addDeliveryManScore() throws Exception {
        this.deliveryMan.addScore();
    }

	@Override
	public String toString() {
		return "Order [id=" + id + ", number=" + number + ", dateOfOrder=" + dateOfOrder + ", comments=" + comments
				+ ", totalPrice=" + totalPrice + ", status=" + status + ", deliveryMan=" + deliveryMan + ", client="
				+ client + ", address=" + address + ", qualification=" + qualification 
				+ ", version=" + version + "]";
	}
    
}
