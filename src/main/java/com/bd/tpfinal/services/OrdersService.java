package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.request.ItemRequestDto;
import com.bd.tpfinal.dtos.request.OrderRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.enums.OrderStatusAction;

import java.util.Date;

public interface OrdersService {

    BaseResponseDto addItemToOrder(String orderId, ItemRequestDto itemDto);
    BaseResponseDto getMaximumPriceOrderByDate(Date date);
    BaseResponseDto getOrdersWithMaximumProductsBySupplier(String supplierId);
    BaseResponseDto qualifyOrder(String orderId, Float qualification, String qualificationMessage);
    BaseResponseDto changeOrderStatus(ChangeOrderStatusDto changeOrderStatusDto);

    BaseResponseDto create(String clientId, String addressId, String comments);

    BaseResponseDto update(String orderId, OrderRequestDto orderRequest);

    void delete(String orderId);

    BaseResponseDto retrieve(String orderId);

    BaseResponseDto retrieve(String status, Integer number);
}
