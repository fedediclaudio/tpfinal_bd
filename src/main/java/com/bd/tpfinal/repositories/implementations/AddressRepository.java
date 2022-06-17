package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.repositories.interfaces.IAddressRepository;

public interface AddressRepository extends MongoRepository<Address, String>, IAddressRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Address getAddressById(String id);
	
}
