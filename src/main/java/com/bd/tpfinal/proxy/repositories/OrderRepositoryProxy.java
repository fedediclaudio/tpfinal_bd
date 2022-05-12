package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.ItemDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.enums.OrderStatusAction;
import com.bd.tpfinal.exceptions.parameters.ParameterErrorException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;

import java.util.Date;
import java.util.List;

public interface OrderRepositoryProxy {

    OrderDto findById(String id) throws PersistenceEntityException;

    OrderDto findByNumber(int number) throws PersistenceEntityException;

    List<OrderDto> findByStatusName(String status);

    OrderDto save(OrderDto orderDto) throws PersistenceEntityException;

    OrderDto create(String clientId, OrderDto orderDto) throws PersistenceEntityException;

    List<OrderDto> findAll();

    OrderDto findByIdWithItems(String orderId) throws PersistenceEntityException;

    boolean exists(String orderId) throws PersistenceEntityException;

    OrderDto addItem(ItemDto itemDto) throws PersistenceEntityException;

    OrderDto findMaxTotalPriceBetweenDates(Date from, Date to);

    OrderDto qualifyOrder(String orderId, Float qualification, String qualificationMessage) throws PersistenceEntityException, ParameterErrorException;

    OrderDto getOrdersWithMaximumProductsBySupplier(String supplierId) throws PersistenceEntityException;

    OrderDto changeOrderStatus(ChangeOrderStatusDto request) throws PersistenceEntityException, ParameterErrorException;

    OrderDto update(String orderId, String addressId) throws PersistenceEntityException;

}
