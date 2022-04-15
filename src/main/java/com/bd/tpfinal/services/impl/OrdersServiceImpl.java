package com.bd.tpfinal.services.impl;


import com.bd.tpfinal.dtos.common.*;
import com.bd.tpfinal.dtos.request.ItemRequestDto;
import com.bd.tpfinal.dtos.request.OrderRequestDto;
import com.bd.tpfinal.dtos.response.BaseResponseDto;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.orders.ListOrderResponseDto;
import com.bd.tpfinal.dtos.response.orders.SingleOrderResponseDto;
import com.bd.tpfinal.exceptions.parameters.ParameterErrorException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.proxy.repositories.ClientRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.OrderRepositoryProxy;
import com.bd.tpfinal.services.OrdersService;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrdersServiceImpl implements OrdersService {

    private final OrderRepositoryProxy orderRepository;

    private final ClientRepositoryProxy clientRepository;

    public OrdersServiceImpl(OrderRepositoryProxy orderRepository, ClientRepositoryProxy clientRepository) {
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
    }


    @Override
    public BaseResponseDto addItemToOrder(String orderId, ItemRequestDto itemRequestDto) {
        SingleOrderResponseDto response = new SingleOrderResponseDto();
        try {
            ItemDto itemDto = new ItemDto(orderId, itemRequestDto.getProductId(), itemRequestDto.getQuantity());
            OrderDto orderDto = orderRepository.addItem(itemDto);
            response.setData(orderDto);
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public BaseResponseDto getMaximumPriceOrderByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        Date from = cal.getTime();
        cal.add(Calendar.DATE, 1);
        Date to = cal.getTime();

        OrderDto orderDto = orderRepository.findMaxTotalPriceBetweenDates(from, to);
        SingleOrderResponseDto response = new SingleOrderResponseDto();
        response.setData(orderDto);
        return response;
    }

    @Override
    public BaseResponseDto getOrdersWithMaximumProductsBySupplier(String supplierId) {
        SingleOrderResponseDto response = new SingleOrderResponseDto();
        try {
            OrderDto orderDto = orderRepository.getOrdersWithMaximumProductsBySupplier(supplierId);
            response.setData(orderDto);
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponseDto qualifyOrder(String orderId, Float qualification, String qualificationMessage) {
        SingleOrderResponseDto response = new SingleOrderResponseDto();
        try {
            OrderDto orderDto = orderRepository.qualifyOrder(orderId, qualification, qualificationMessage);
            response.setData(orderDto);
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        } catch (ParameterErrorException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public BaseResponseDto changeOrderStatus(ChangeOrderStatusDto changeOrderStatusDto) {
        SingleOrderResponseDto response = new SingleOrderResponseDto();
        try {
            OrderDto orderDto = orderRepository.changeOrderStatus(changeOrderStatusDto) ;
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        } catch (ParameterErrorException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponseDto create(String clientId, String addressId, String comments) {
        SingleOrderResponseDto response = new SingleOrderResponseDto();
        try {
             ClientDto clientDto = clientRepository.findById(clientId);
             OrderDto orderDto = new OrderDto();
             orderDto.setClient(clientDto);
             orderDto.setComments(comments);
             AddressDto addressDto = new AddressDto(addressId, "");
             orderDto.setAddress(addressDto);
             orderDto = orderRepository.create(clientId,orderDto);
             response.setData(orderDto);
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponseDto update(String orderId, OrderRequestDto orderRequest) {
        SingleOrderResponseDto response = new SingleOrderResponseDto();
        try {
            OrderDto orderDto = orderRepository.findById(orderId);
        } catch (PersistenceEntityException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public void delete(String orderId) {

    }

    @Override
    public BaseResponseDto retrieve(String orderId) {
        BaseResponseDto response = new SingleOrderResponseDto();
        try {
            OrderDto order = orderRepository.findById(orderId);
            response.setData(order);
        } catch (PersistenceEntityException e) {
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }
    @Override
    public BaseResponseDto retrieve(String status, Integer number) {
        BaseResponseDto response;
        if (status != null) {
            List orders = orderRepository.findByStatusName(status);
            response = new ListOrderResponseDto();
            response.setData(orders);
        }else if (number != null && number > 0) {
            response = new SingleOrderResponseDto();
            try {
                OrderDto order = orderRepository.findByNumber(number);
                response.setData(order);
            } catch (PersistenceEntityException e) {
                response.setData(null);
                response.setMessage(e.getMessage());
                response.setStatus(ResponseStatus.ERROR);
            }
        }  else {
                response = new ListOrderResponseDto();
                response.setData(orderRepository.findAll());
        }
        return response;
    }
}
