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
import com.bd.tpfinal.repositories.interfaces.IOrderRepository;

@Repository
public class OrderRepositoryImpl implements IOrderRepository {
	@PersistenceContext private EntityManager em;
	
	public List<Order> getAllPendingOrdersForClient(long idClient) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Client> root = cq.from(Client.class);
		Join<Client, Order> orders = root.join(Client_.ORDERS, JoinType.INNER);
		
		Predicate id = cb.equal(root.get(Client_.ID), idClient);
		cq.where(id);
		
		cq.select( orders );
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}

	public List<Order> getAllPendingOrdersForDeliveryMan(long idDeliveryMan) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<DeliveryMan> root = cq.from(DeliveryMan.class);
		Join<DeliveryMan, Order> orders = root.join(DeliveryMan_.ORDERS_PENDING, JoinType.INNER);
		
		Predicate id = cb.equal(root.get(DeliveryMan_.ID), idDeliveryMan);
		cq.where(id);
		
		cq.select( orders );
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}
	
	public Order getNextPendingOrderForClient(long idClient) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<Client> root = cq.from(Client.class);
		Join<Client, Order> orders = root.join(Client_.ORDERS, JoinType.INNER);
		
		Predicate id = cb.equal(root.get(Client_.ID), idClient);
		cq.where(id);
		
		cq.select( orders );
		
		TypedQuery<Order> typeQuery = em.createQuery(cq);
		typeQuery.setMaxResults(1);
		
		try {
			return typeQuery.getSingleResult();
		}
		catch (Exception e) {
			return null;
		}
	}

	public Order getNextPendingOrderDeliveryMan(long idDeliveryMan) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		
		CriteriaQuery<Order> cq = cb.createQuery(Order.class);
		Root<DeliveryMan> root = cq.from(DeliveryMan.class);
		Join<DeliveryMan, Order> orders = root.join(DeliveryMan_.ORDERS_PENDING, JoinType.INNER);
		
		Predicate id = cb.equal(root.get(DeliveryMan_.ID), idDeliveryMan);
		cq.where(id);
		
		cq.select( orders );
		
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
