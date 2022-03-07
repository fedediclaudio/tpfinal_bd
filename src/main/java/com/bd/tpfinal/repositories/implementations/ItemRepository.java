package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.repositories.interfaces.IItemRepository;

public interface ItemRepository extends JpaRepository<Item, Long>, IItemRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Item getItemById(long id);
	
}
