package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.repositories.interfaces.IDeliveryManRepository;

public interface DeliveryManRepository extends MongoRepository<DeliveryMan, Long>, IDeliveryManRepository {

	// Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot
}
