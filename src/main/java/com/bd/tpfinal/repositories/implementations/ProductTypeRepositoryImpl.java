package com.bd.tpfinal.repositories.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.repositories.interfaces.IProductTypeRepository;

@Repository
public class ProductTypeRepositoryImpl implements IProductTypeRepository {
	@PersistenceContext private EntityManager em;
	
	
}
