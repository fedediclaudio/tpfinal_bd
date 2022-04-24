package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.request.items.CreateItemRequest;
import com.bd.tpfinal.dtos.request.orders.CreateOrderRequest;
import com.bd.tpfinal.dtos.response.BaseResponseDto;

import java.util.Date;

public interface OrdersService {

    BaseResponseDto addItemToOrder(String orderId, CreateItemRequest itemDto);
    BaseResponseDto getMaximumPriceOrderByDate(Date date);
    BaseResponseDto getOrdersWithMaximumProductsBySupplier(String supplierId);
    BaseResponseDto qualifyOrder(String orderId, Float qualification, String qualificationMessage);
    BaseResponseDto changeOrderStatus(ChangeOrderStatusDto changeOrderStatusDto);

    BaseResponseDto create(String clientId, String addressId, String comments);

    BaseResponseDto update(String orderId, CreateOrderRequest orderRequest);

    void delete(String orderId);

    BaseResponseDto retrieve(String orderId);

    BaseResponseDto retrieve(String status, Integer number);
}
