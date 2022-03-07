package com.bd.tpfinal.repositories.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.repositories.interfaces.ISupplierTypeRepository;

@Repository
public class SupplierTypeRepositoryImpl implements ISupplierTypeRepository {
	@PersistenceContext private EntityManager em;
	
	
}
