package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Client_;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.DeliveryMan_;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.model.OrderStatus_;
import com.bd.tpfinal.model.Order_;
import com.bd.tpfinal.repositories.interfaces.IOrderRepository;

@Repository
public class OrderRepositoryImpl implements IOrderRepository {
	@PersistenceContext private EntityManager em;
	
	private CriteriaQuery<Order> getBasicOrderForClientFilter(long idClient, String status) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Client> root = cq.from(Client.class);
		Join<Client, Order> orders = root.join(Client_.ORDERS, JoinType.INNER);
		Join<Order, OrderStatus> ordersStatus = orders.join(Order_.STATUS, JoinType.INNER);
		
		Predicate id = cb.equal(root.get(Client_.ID), idClient);
		if (status != null) {
			Predicate statusFilter = cb.equal(ordersStatus.get(OrderStatus_.NAME), status);
			cq.where(id, statusFilter);
		}
		else
			cq.where(id);	
		
		cq.select( orders );
		
		return cq;
	}
	
	private CriteriaQuery<Order> getBasicOrderForDeliveryManFilter(long idDeliveryMan, String status) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<DeliveryMan> root = cq.from(DeliveryMan.class);
		Join<DeliveryMan, Order> orders = root.join(DeliveryMan_.ORDERS_PENDING, JoinType.INNER);
		Join<Order, OrderStatus> ordersStatus = orders.join(Order_.STATUS, JoinType.INNER);
		
		Predicate id = cb.equal(root.get(DeliveryMan_.ID), idDeliveryMan);
		if (status != null) {
			Predicate statusFilter = cb.equal(ordersStatus.get(OrderStatus_.NAME), status);
			cq.where(id, statusFilter);
		}
		else
			cq.where(id);
		
		cq.select( orders );
		
		return cq;
	}
	
	@Override
	public List<Order> getAllOrdersForClient(long idClient, String status) {
		CriteriaQuery<Order> cq = getBasicOrderForClientFilter(idClient, status);
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}

	@Override
	public List<Order> getAllOrdersForDeliveryMan(long idDeliveryMan, String status) {
		CriteriaQuery<Order> cq = getBasicOrderForDeliveryManFilter(idDeliveryMan, status);
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}
	
	@Override
	public Order getNextOrderForClient(long idClient, String status) {
		CriteriaQuery<Order> cq = getBasicOrderForClientFilter(idClient, status);
		
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		typeQuery.setMaxResults(1);
		
		try {
			return typeQuery.getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}

	@Override
	public Order getNextOrderDeliveryMan(long idDeliveryMan, String status) {
		CriteriaQuery<Order> cq = getBasicOrderForDeliveryManFilter(idDeliveryMan, status);
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		typeQuery.setMaxResults(1);
		
		try {
			return typeQuery.getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}
}
