package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.request.ItemRequestDto;
import com.bd.tpfinal.dtos.request.OrderRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;

import java.util.Date;

public interface OrdersService {

//    ListOrderResponseDto getOrders(SearchFilterRequestDto searchFilterRequestDto);
    BaseResponseDto addItemToOrder(String orderId, ItemRequestDto itemDto);
    BaseResponseDto getMaximumPriceOrderByDate(Date date);
    BaseResponseDto getOrdersWithMaximumProductsBySupplier(String supplierId);
    BaseResponseDto qualifyOrder(String orderId, Float qualification, String qualificationMessage);
    BaseResponseDto confirmOrder(String orderId);


    BaseResponseDto create(String clientId, String addressId, String comments);

    BaseResponseDto update(String orderId, OrderRequestDto orderRequest);

    void delete(String orderId);

    BaseResponseDto retrieve(String orderId);

    BaseResponseDto retrieve(String status, Integer number);
}
