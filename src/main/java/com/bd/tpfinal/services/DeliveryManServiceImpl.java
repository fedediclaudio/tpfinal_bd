package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.EjemploRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DeliveryManServiceImpl implements DeliveryManService
{
    private DeliveryManRepository deliveryManRepository;
    @Autowired
    public DeliveryManServiceImpl(DeliveryManRepository deliveryManRepository)
    {
        this.deliveryManRepository = deliveryManRepository;
    }

    @Override
    public void addDeliveryMan(DeliveryMan newDeliveryMan)
    {
        this.deliveryManRepository.save(newDeliveryMan);
    }

    @Override
    public List<DeliveryMan> getAll()
    {
        return this.deliveryManRepository.findAll();
    }
}
