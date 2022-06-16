package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.Item;

public interface ItemRepository extends MongoRepository<Item, String> {

	Item getItemById(String id);
	
}
