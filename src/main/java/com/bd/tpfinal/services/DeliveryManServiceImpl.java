package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryManServiceImpl implements DeliveryManService {
    @Override
    public List<DeliveryMan> getTop10RepartidoresMayorPuntaje() {
        return null;
    }

    @Override
    public Optional<DeliveryMan> getFreeAndActiveDeliveryMan() {
        return Optional.empty();
    }

    @Override
    public void guardarDeliveryMan(DeliveryMan currenteDeliveryMan) {

    }
}
