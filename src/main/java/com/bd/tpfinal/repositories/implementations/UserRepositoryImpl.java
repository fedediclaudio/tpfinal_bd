package com.bd.tpfinal.repositories.implementations;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.bd.tpfinal.model.User;
import com.bd.tpfinal.repositories.interfaces.IUserRepository;

@Repository
public class UserRepositoryImpl implements IUserRepository {
	@PersistenceContext private EntityManager em;
	
	@Override
	public User getReference(long idUser) {
		return em.find(User.class, idUser);
	}
	
}
