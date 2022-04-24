package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.request.DeliveryManRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;

public interface DeliveryService {
    BaseResponseDto getMostScoredDeliveryMen();

    BaseResponseDto findAll();

    BaseResponseDto createDeliveryMan(DeliveryManRequestDto deliveryManRequestDto);
}
