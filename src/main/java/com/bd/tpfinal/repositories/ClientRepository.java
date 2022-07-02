package com.bd.tpfinal.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bd.tpfinal.model.Client;


@Repository
public interface ClientRepository extends MongoRepository<Client, ObjectId>{
	
	public Optional<Client> findByUsername(String aUsername);

}
