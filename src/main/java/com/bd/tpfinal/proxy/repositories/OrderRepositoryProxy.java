package com.bd.tpfinal.proxy.repositories;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.ItemDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.enums.OrderStatusAction;
import com.bd.tpfinal.exceptions.general.ActionNotAllowedException;
import com.bd.tpfinal.exceptions.parameters.ParameterErrorException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;

import java.util.Date;
import java.util.List;

public interface OrderRepositoryProxy {

    OrderDto findById(String id);

    OrderDto findByNumber(int number);

    List<OrderDto> findByStatusName(String status);

    OrderDto save(OrderDto orderDto);

    OrderDto create(String clientId, OrderDto orderDto);

    List<OrderDto> findAll();

    OrderDto addItem(ItemDto itemDto);

    OrderDto findMaxTotalPriceBetweenDates(Date from, Date to);

    OrderDto qualifyOrder(String orderId, Float qualification, String qualificationMessage) throws  ParameterErrorException, ActionNotAllowedException;

    OrderDto getOrdersWithMaximumProductsBySupplier(String supplierId) ;

    OrderDto changeOrderStatus(ChangeOrderStatusDto request) throws PersistenceEntityException, ParameterErrorException;

    OrderDto update(String orderId, String addressId);

}
