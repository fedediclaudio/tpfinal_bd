package com.bd.tpfinal.repositories.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.repositories.interfaces.ISupplierRepository;

@Repository
public class SupplierRepositoryImpl implements ISupplierRepository {
	@PersistenceContext private EntityManager em;
	
	
}
