package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.repositories.interfaces.IItemRepository;

public interface ItemRepository extends MongoRepository<Item, String>, IItemRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	
}
