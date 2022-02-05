package com.bd.tpfinal.repositories.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.repositories.interfaces.IAddressRepository;

@Repository
public class AddressRepositoryImpl implements IAddressRepository {
	@PersistenceContext private EntityManager em;
	
	
}
