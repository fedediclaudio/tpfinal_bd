package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryManServiceImpl implements DeliveryManService {
    @Autowired
    DeliveryManRepository deliveryManRepository;
    @Override
    public List<DeliveryMan> getTop10RepartidoresMayorPuntaje() {
        return deliveryManRepository.getTop10RepartidoresMayorPuntaje();
    }

    @Override
    public List <DeliveryMan> getFreeAndActiveDeliveryMan() {
        return deliveryManRepository.findByFreeIsTrue();
    }

    @Override
    public void guardarDeliveryMan(DeliveryMan currentDeliveryMan) {
        deliveryManRepository.save(currentDeliveryMan);
    }
}
