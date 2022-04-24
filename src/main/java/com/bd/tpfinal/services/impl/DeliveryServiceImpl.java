package com.bd.tpfinal.services.impl;

import com.bd.tpfinal.dtos.common.DeliveryManDto;
import com.bd.tpfinal.dtos.request.DeliveryManRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.delivery.ListDeliveryResponseDto;
import com.bd.tpfinal.dtos.response.delivery.SingleDeliveryManResponseDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.proxy.repositories.DeliveryMenRepositoryProxy;
import com.bd.tpfinal.services.DeliveryService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    private final DeliveryMenRepositoryProxy deliveryMenRepositoryProxy;

    public DeliveryServiceImpl(DeliveryMenRepositoryProxy deliveryMenRepositoryProxy) {
        this.deliveryMenRepositoryProxy = deliveryMenRepositoryProxy;
    }

    @Override
    public BaseResponseDto getMostScoredDeliveryMen() {
        ListDeliveryResponseDto response = new ListDeliveryResponseDto();
        List<DeliveryManDto> data = deliveryMenRepositoryProxy.findMostScoredDeliveryMen();
        response.setData(data);
        response.setMessage("10 most success delivery men.");
        return response;
    }

    @Override
    public BaseResponseDto findAll() {
        ListDeliveryResponseDto response = new ListDeliveryResponseDto();
        List<DeliveryManDto> data = deliveryMenRepositoryProxy.findAll();
        response.setData(data);
        response.setMessage("All delivery men.");
        return response;
    }

    @Override
    public BaseResponseDto createDeliveryMan(DeliveryManRequestDto deliveryManRequestDto) {
        SingleDeliveryManResponseDto response = new SingleDeliveryManResponseDto();

        DeliveryManDto deliveryManDto = DeliveryManDto.builder()
                .free(true)
                .dateOfAdmission(new Date())
                .numberOfSuccessOrders(0)
                .name(deliveryManRequestDto.getName())
                .username(deliveryManRequestDto.getUsername())
                .password(deliveryManRequestDto.getPassword())
                .email(deliveryManRequestDto.getEmail())
                .build();

        try {
            deliveryManDto = deliveryMenRepositoryProxy.create(deliveryManDto);
            response.setData(deliveryManDto);
            response.setMessage("Delivery man created.");
        }catch (PersistenceEntityException e){
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }
}
