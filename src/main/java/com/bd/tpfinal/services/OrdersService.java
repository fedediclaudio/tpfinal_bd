package com.bd.tpfinal.services;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.request.items.CreateItemRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;

import java.util.Date;

public interface OrdersService {

    BaseResponse addItemToOrder(String orderId, CreateItemRequest itemDto);
    BaseResponse getMaximumPriceOrderByDate(Date date);
    BaseResponse getOrdersWithMaximumProductsBySupplier(String supplierId);
    BaseResponse qualifyOrder(String orderId, Float qualification, String qualificationMessage);
    BaseResponse changeOrderStatus(ChangeOrderStatusDto changeOrderStatusDto);

    BaseResponse create(String clientId, String addressId, String comments);

    BaseResponse update(String orderId, String addressId);

    void delete(String orderId);

    BaseResponse retrieve(String orderId);

    BaseResponse retrieve(String status, Integer number);
}
