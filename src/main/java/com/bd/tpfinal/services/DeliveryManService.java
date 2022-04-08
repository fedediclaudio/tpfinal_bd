package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;

import java.util.List;
import java.util.Optional;

public interface DeliveryManService
{
    public void addDeliveryMan(DeliveryMan newDeliveryMan);
    public List<DeliveryMan> getAll();
    public Optional<DeliveryMan> getById(Long id);
}
