package com.bd.tpfinal.services;

import com.bd.tpfinal.model.OrderStatus;

public interface OrderStatusService {

    OrderStatus createOrderStatus(OrderStatus orderStatus) throws Exception;

    OrderStatus getOrderStatus(String name) throws Exception;

}
