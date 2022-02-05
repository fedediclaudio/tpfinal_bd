package com.bd.tpfinal.repositories.interfaces;

import java.util.List;

import com.bd.tpfinal.model.DeliveryMan;

public interface IDeliveryManRepository {
	
	// Los metodos que se vayan a porponerse aca, tienen que estar implementados en su implementacion (DeliveryManRepositoryImpl)
	
	List<DeliveryMan> getFreeDeliveryManList();
}
