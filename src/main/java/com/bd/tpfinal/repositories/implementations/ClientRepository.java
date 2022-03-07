package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.repositories.interfaces.IClientRepository;

public interface ClientRepository extends JpaRepository<Client, Long>, IClientRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	Client getClientById(long id);
	
}
