package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.repositories.interfaces.IDeliveryManRepository;

public interface DeliveryManRepository extends JpaRepository<DeliveryMan, Long>, IDeliveryManRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
	
	DeliveryMan getDeliveryManById(long id);
	
}
