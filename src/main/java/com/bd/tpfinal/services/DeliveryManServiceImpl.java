package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
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

    @Override
    //https://tedblob.com/spring-data-jpa-findall-order-by/
    public List<DeliveryMan> getAllOrderByScore()
    {
        //return this.deliveryManRepository.findAllOrderByScore(Sort.by(Sort.Order.desc("score")));
        //return this.deliveryManRepository.findAllOrderByScore();
        //return this.deliveryManRepository.findTop10OrderByScore(Sort.by(Sort.Order.desc("score")), PageRequest.of(0, 10));
        return this.deliveryManRepository.findTop10OrderByScore(PageRequest.of(0, 10));
    }
}
