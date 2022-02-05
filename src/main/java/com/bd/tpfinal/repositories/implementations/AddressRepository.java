package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.repositories.interfaces.IAddressRepository;

public interface AddressRepository extends JpaRepository<Address, Long>, IAddressRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Address getAddressById(Long id);
	
}
