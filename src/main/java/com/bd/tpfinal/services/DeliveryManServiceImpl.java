package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public void newDeliveryMan(DeliveryMan newDeliveryMan)
    {
        this.deliveryManRepository.save(newDeliveryMan);
    }

    @Override
    public List<DeliveryMan> getAll()
    {
        return this.deliveryManRepository.findAll();
    }

    @Override
    public Optional<DeliveryMan> getById(Long id)
    {
        return this.deliveryManRepository.findById(id);
    }

    @Override
    public List<DeliveryMan> getAllDeliveryManFree()
    {
        return this.deliveryManRepository.findAllFree();
    }
}
