package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.DeliveryManDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.helpers.IdConvertionHelper;
import com.bd.tpfinal.mappers.delivery.DeliveryManMapper;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.proxy.repositories.DeliveryMenRepositoryProxy;
import com.bd.tpfinal.repositories.DeliveryManRepository;

import java.util.List;
import java.util.stream.Collectors;

public class DeliveryMenRepositoryProxyImpl implements DeliveryMenRepositoryProxy {

    private final DeliveryManRepository deliveryManRepository;

    private final DeliveryManMapper deliveryManMapper;

    public DeliveryMenRepositoryProxyImpl(DeliveryManRepository deliveryManRepository, DeliveryManMapper deliveryManMapper) {
        this.deliveryManRepository = deliveryManRepository;
        this.deliveryManMapper = deliveryManMapper;
    }

    @Override
    public List<DeliveryManDto> findAll() {
        return deliveryManRepository
                .findAll()
                .stream()
                .map(d -> deliveryManMapper.toDeliveryManDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public List<DeliveryManDto> findMostScoredDeliveryMen() {
        return deliveryManRepository
                .findFirst10ByOrderByScoreDesc()
                .stream()
                .map(d -> deliveryManMapper.toDeliveryManDto(d))
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryManDto create(DeliveryManDto deliveryManDto) throws PersistenceEntityException {
        DeliveryManDto dto = null;
        try {
            DeliveryMan deliveryMan = deliveryManMapper.toDeliveryMan(deliveryManDto);
            deliveryMan = deliveryManRepository.save(deliveryMan);
            dto = deliveryManMapper.toDeliveryManDto(deliveryMan);
        } catch (Exception e){
            e.printStackTrace();
            throw new PersistenceEntityException("Can't save delivery man with name '" + deliveryManDto.getName() + "'");
        }
        return dto;
    }

    @Override
    public DeliveryManDto findById(String id) throws PersistenceEntityException {
        DeliveryMan deliveryMan = deliveryManRepository.findById(IdConvertionHelper.convert(id))
                .orElseThrow(()-> new PersistenceEntityException("Can't find delivery man with id: " + id));
        return deliveryManMapper.toDeliveryManDto(deliveryMan);
    }

    @Override
    public List<DeliveryManDto> findByPendingOrderIsNull() {
        List<DeliveryMan> deliveryMen = deliveryManRepository.findByPendingOrderIsNull();
        return deliveryMen.stream().map(d -> deliveryManMapper.toDeliveryManDto(d)).collect(Collectors.toList());
    }
}
