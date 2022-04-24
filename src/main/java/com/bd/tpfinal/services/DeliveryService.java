package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.request.delivery.CreateDeliveryManRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;

public interface DeliveryService {
    BaseResponse getMostScoredDeliveryMen();

    BaseResponse findAll();

    BaseResponse createDeliveryMan(CreateDeliveryManRequest createDeliveryManRequest);
}
