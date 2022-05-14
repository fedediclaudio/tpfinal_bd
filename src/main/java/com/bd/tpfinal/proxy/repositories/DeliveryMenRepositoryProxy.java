package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.DeliveryManDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;

import java.util.List;

public interface DeliveryMenRepositoryProxy {

    List<DeliveryManDto> findAll();

    List<DeliveryManDto> findMostScoredDeliveryMen();

    DeliveryManDto create(DeliveryManDto deliveryManDto) throws PersistenceEntityException;

    DeliveryManDto findById(String id) throws PersistenceEntityException;

    List<DeliveryManDto> findByPendingOrderIsNull();
}
