package com.bd.tpfinal.model;

import java.util.Date;
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

@Entity
@Table(name = "user_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private int number;

    @Column(nullable = false)
	private Date dateOfOrder;

	@Column(length = 500)
	private String comments;

	@Column(nullable = false)
	private float totalPrice;

	@OneToOne( mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private OrderStatus status;

	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
	@JoinColumn( name = "id_delivery_man" )
	private DeliveryMan deliveryMan;

	@ManyToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    @JoinColumn( name = "id_client" )
	private Client client;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn( name="id_address" )
	private Address address;

	@OneToOne( fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn( name="id_qualification" )
	private Qualification qualification;

	@OneToMany( mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Item> items;
	
	@Version
    @Column(name = "version")
    private int version;
    
    
	public Order() {}
	
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

	@Override
	public String toString() {
		return "Order [number=" + number + ", dateOfOrder=" + dateOfOrder + ", comments=" + comments + ", totalPrice="
				+ totalPrice + ", status=" + status + ", deliveryMan=" + deliveryMan + ", client=" + client
				+ ", address=" + address + ", qualification=" + qualification + ", items=" + items + ", version="
				+ version + "]";
	}
	
}
