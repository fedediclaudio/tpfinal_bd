package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.bd.tpfinal.model.Client;

public interface ClientRepository extends MongoRepository<Client, String>, QuerydslPredicateExecutor<Client> {

	Client getClientById(String id);
	
}
