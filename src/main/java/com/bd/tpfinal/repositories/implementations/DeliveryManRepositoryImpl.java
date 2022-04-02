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

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.DeliveryMan_;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.model.OrderStatus_;
import com.bd.tpfinal.model.Order_;
import com.bd.tpfinal.repositories.interfaces.IDeliveryManRepository;

@Repository
public class DeliveryManRepositoryImpl implements IDeliveryManRepository {
	@PersistenceContext private EntityManager em;
	
	
	public List<DeliveryMan> getFreeDeliveryManList() {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<DeliveryMan> cq = cb.createQuery(DeliveryMan.class);
		Root<DeliveryMan> root = cq.from(DeliveryMan.class);
		
		Predicate free = cb.equal(root.get(DeliveryMan_.FREE), true);
		Predicate active = cb.equal(root.get(DeliveryMan_.ACTIVE), true);
		cq.where(free, active);
		
		cq.select( root );
		TypedQuery<DeliveryMan> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}
	
	public List<Order> getAllPendingOrders(long idDeliveryMan) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<DeliveryMan> root = cq.from(DeliveryMan.class);
		Join<DeliveryMan, Order> orders = root.join(DeliveryMan_.ORDERS_PENDING, JoinType.INNER);
		Join<Order, OrderStatus> ordersStatus = orders.join(Order_.STATUS, JoinType.INNER);
		
		Predicate id = cb.equal(root.get(DeliveryMan_.ID), idDeliveryMan);
		Predicate pending = cb.equal(ordersStatus.get(OrderStatus_.NAME), "Assigned");
		cq.where(id, pending);
		
		cq.select( orders );
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}
	
	public Order getNextPendingOrder(long idDeliveryMan) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<DeliveryMan> root = cq.from(DeliveryMan.class);
		Join<DeliveryMan, Order> orders = root.join(DeliveryMan_.ORDERS_PENDING, JoinType.INNER);
		Join<Order, OrderStatus> ordersStatus = orders.join(Order_.STATUS, JoinType.INNER);
		
		Predicate id = cb.equal(root.get(DeliveryMan_.ID), idDeliveryMan);
		Predicate pending = cb.equal(ordersStatus.get(OrderStatus_.NAME), "Assigned");
		cq.where(id, pending);
		
		cq.select( orders );
		
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		typeQuery.setMaxResults(1);
		
		return typeQuery.getSingleResult();
	}
	
}
