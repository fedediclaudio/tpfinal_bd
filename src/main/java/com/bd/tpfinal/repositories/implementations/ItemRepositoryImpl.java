package com.bd.tpfinal.repositories.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.repositories.interfaces.IItemRepository;

@Repository
public class ItemRepositoryImpl implements IItemRepository {
	@PersistenceContext private EntityManager em;
	
	
}
