package com.bd.tpfinal.repositories.implementations;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.HistoricalProductPrice_;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Product_;
import com.bd.tpfinal.repositories.interfaces.IHistoricalProductPriceRepository;

@Repository
public class HistoricalProductPriceRepositoryImpl implements IHistoricalProductPriceRepository {
	@PersistenceContext private EntityManager em;

	public List<HistoricalProductPrice> getHistoricalPricesListOrderByStartDate(long idProduct) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<HistoricalProductPrice> cq = cb.createQuery(HistoricalProductPrice.class);
		Root<Product> root = cq.from(Product.class);
		Join<Product, HistoricalProductPrice> history = root.join(Product_.PRICES);
		
		cq.where( cb.equal(root.get(Product_.ID), idProduct) );
		
		cq.select( history );
		TypedQuery<HistoricalProductPrice> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}
	
	
	public List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates(LocalDate startDate, LocalDate endDate) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<HistoricalProductPrice> cq = cb.createQuery(HistoricalProductPrice.class);
		Root<HistoricalProductPrice> root = cq.from(HistoricalProductPrice.class);
		
		Predicate onStart = cb.lessThanOrEqualTo(root.get(HistoricalProductPrice_.START_DATE), startDate);
		Predicate onEnd = cb.or(cb.isNull(root.get(HistoricalProductPrice_.FINISH_DATE)), 
								cb.greaterThan(root.get(HistoricalProductPrice_.FINISH_DATE), endDate));
		cq.where(onStart, onEnd);
		
		cq.select( root );
		TypedQuery<HistoricalProductPrice> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}
}
