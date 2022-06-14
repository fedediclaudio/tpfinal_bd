package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;

import java.util.List;
import java.util.Optional;

public interface DeliveryManService {
    List<DeliveryMan> getTop10RepartidoresMayorPuntaje();
    Optional<DeliveryMan> getFreeAndActiveDeliveryMan();
    void guardarDeliveryMan(DeliveryMan currenteDeliveryMan);
}