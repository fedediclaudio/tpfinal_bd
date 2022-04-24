package com.bd.tpfinal.services.impl;

import com.bd.tpfinal.dtos.common.DeliveryManDto;
import com.bd.tpfinal.dtos.request.delivery.CreateDeliveryManRequest;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.delivery.ListDeliveryResponse;
import com.bd.tpfinal.dtos.response.delivery.SingleDeliveryManResponse;
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
        ListDeliveryResponse response = new ListDeliveryResponse();
        List<DeliveryManDto> data = deliveryMenRepositoryProxy.findMostScoredDeliveryMen();
        response.setData(data);
        response.setMessage("10 most success delivery men.");
        return response;
    }

    @Override
    public BaseResponseDto findAll() {
        ListDeliveryResponse response = new ListDeliveryResponse();
        List<DeliveryManDto> data = deliveryMenRepositoryProxy.findAll();
        response.setData(data);
        response.setMessage("All delivery men.");
        return response;
    }

    @Override
    public BaseResponseDto createDeliveryMan(CreateDeliveryManRequest createDeliveryManRequest) {
        SingleDeliveryManResponse response = new SingleDeliveryManResponse();

        DeliveryManDto deliveryManDto = DeliveryManDto.builder()
                .free(true)
                .dateOfAdmission(new Date())
                .numberOfSuccessOrders(0)
                .name(createDeliveryManRequest.getName())
                .username(createDeliveryManRequest.getUsername())
                .password(createDeliveryManRequest.getPassword())
                .email(createDeliveryManRequest.getEmail())
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
