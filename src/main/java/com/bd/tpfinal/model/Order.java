package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.bd.tpfinal.model.orderStatusTypes.Cancel;
import com.bd.tpfinal.model.orderStatusTypes.Delivered;
import com.bd.tpfinal.model.orderStatusTypes.Sent;

@Document(collection = "Order")
@Data
@AllArgsConstructor
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
        this.items = new ArrayList<Item>();
    }

    public Order(Client client) {
    	this.number = String.valueOf( this.getPositiveId() );
        this.dateOfOrder = LocalDate.now();
        this.client = client;
        this.items = new ArrayList<Item>();
    }

    public boolean addItem(Item item) throws Exception {
        if (this.status.canAddItem()) {
            this.items.add(item);
            return true;
        }
        return false;
    }

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
