package com.bd.tpfinal.repositories.implementations;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Product_;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.Supplier_;
import com.bd.tpfinal.repositories.interfaces.IProductRepository;

@Repository
public class ProductRepositoryImpl implements IProductRepository {
	@PersistenceContext private EntityManager em;
	
	public List<Product> getProductsFromSupplier(long idSupplier) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Product> cq = cb.createQuery(Product.class);
		Root<Product> root = cq.from(Product.class);
		Join<Product, Supplier> supplier = root.join(Product_.SUPPLIER);
		
		cq.where( cb.equal(supplier.get(Supplier_.ID), idSupplier) );
		
		cq.select( root );
		TypedQuery<Product> typeQuery = em.createQuery(cq);
		
		return typeQuery.getResultList();
	}
}
