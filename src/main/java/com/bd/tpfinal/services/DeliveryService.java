package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.request.delivery.CreateDeliveryManRequest;
import com.bd.tpfinal.dtos.response.BaseResponseDto;

public interface DeliveryService {
    BaseResponseDto getMostScoredDeliveryMen();

    BaseResponseDto findAll();

    BaseResponseDto createDeliveryMan(CreateDeliveryManRequest createDeliveryManRequest);
}
