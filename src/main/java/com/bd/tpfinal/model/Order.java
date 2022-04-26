package com.bd.tpfinal.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import com.bd.tpfinal.model.orderStatusTypes.Cancel;
import com.bd.tpfinal.model.orderStatusTypes.Delivered;
import com.bd.tpfinal.model.orderStatusTypes.Pending;
import com.bd.tpfinal.model.orderStatusTypes.Sent;

@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int number;

    @Column(nullable = false)
	private LocalDate dateOfOrder;

	@Column(length = 800)
	private String comments;

	@Column(nullable = false)
	private float totalPrice;

	@OneToOne( mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private OrderStatus status;

	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	@JoinColumn( name = "id_delivery_man" )
	private DeliveryMan deliveryMan;

	@ManyToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
    @JoinColumn( name = "id_client" )
	private Client client;

	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn( name="id_address" )
	private Address address;

	@OneToOne( fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn( name="id_qualification" )
	private Qualification qualification;

	@OneToMany( mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER )
	private List<Item> items;
	
	@Version
    @Column(name = "version")
    private int version;
    
    
	public Order() {}
	
	public Order(Client client) {
		// Por defecto la orden esta en Pendiente
		this.status = new Pending(this);
		this.dateOfOrder = LocalDate.now();
		this.client = client;
		
		// Inicializo la lista de Items
		this.items = new ArrayList<>();
	}
	
	
	
	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
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
	
	public void addQualification(Qualification qualification) {
		this.qualification = qualification;
		
		this.items.forEach( i -> i.getProduct().getSupplier().addQualification( qualification.getScore() ) );
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

	@Override
	public String toString() {
		return "Order [number=" + number + ", dateOfOrder=" + dateOfOrder + ", comments=" + comments + ", totalPrice="
				+ totalPrice + ", status=" + status + ", deliveryMan=" + deliveryMan + ", client=" + client
				+ ", address=" + address + ", qualification=" + qualification + ", items=" + items + ", version="
				+ version + "]";
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
}
