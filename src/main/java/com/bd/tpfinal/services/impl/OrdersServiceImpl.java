package com.bd.tpfinal.services.impl;


import com.bd.tpfinal.dtos.common.*;
import com.bd.tpfinal.dtos.request.items.CreateItemRequest;
import com.bd.tpfinal.dtos.request.orders.UpdateOrderRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.dtos.response.ResponseStatus;
import com.bd.tpfinal.dtos.response.orders.ListOrderResponse;
import com.bd.tpfinal.dtos.response.orders.SingleOrderResponse;
import com.bd.tpfinal.exceptions.parameters.ParameterErrorException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.proxy.repositories.ClientRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.OrderRepositoryProxy;
import com.bd.tpfinal.services.OrdersService;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
    public BaseResponse addItemToOrder(String orderId, CreateItemRequest createItemRequest) {
        BaseResponse response = new SingleOrderResponse();
        try {
            ItemDto itemDto = ItemDto.builder()
                    .orderId(orderId)
                    .productId(createItemRequest.getProductId())
                    .quantity(createItemRequest.getQuantity())
                    .build();

            OrderDto orderDto = orderRepository.addItem(itemDto);
            response.setData(orderDto);
            response.setMessage("Item added to order.");
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }

        return response;
    }

    @Override
    public BaseResponse getMaximumPriceOrderByDate(Date date) {
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
        BaseResponse response = new SingleOrderResponse();
        response.setMessage("Order with maximum price of date '" + new SimpleDateFormat("yyyy-MM-dd").format(date) + "'");
        response.setData(orderDto);
        return response;
    }

    @Override
    public BaseResponse getOrdersWithMaximumProductsBySupplier(String supplierId) {
        BaseResponse response = new SingleOrderResponse();
        try {
            OrderDto orderDto = orderRepository.getOrdersWithMaximumProductsBySupplier(supplierId);
            response.setData(orderDto);
            response.setMessage("Orders with maximum products by supplier.");
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponse qualifyOrder(String orderId, Float qualification, String qualificationMessage) {
        BaseResponse response = new SingleOrderResponse();
        try {
            OrderDto orderDto = orderRepository.qualifyOrder(orderId, qualification, qualificationMessage);
            response.setData(orderDto);
            response.setMessage("Order qualified.");
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        } catch (ParameterErrorException e) {
            throw new RuntimeException(e);
        }
        return response;
    }

    @Override
    public BaseResponse changeOrderStatus(ChangeOrderStatusDto changeOrderStatusDto) {
        BaseResponse response = new SingleOrderResponse();
        try {
            OrderDto orderDto = orderRepository.changeOrderStatus(changeOrderStatusDto) ;
            response.setData(orderDto);
            response.setMessage("Status changed for order id: " + changeOrderStatusDto.getOrderId());
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
    public BaseResponse create(String clientId, String addressId, String comments) {
        BaseResponse response = new SingleOrderResponse();
        try {
             ClientDto clientDto = clientRepository.findById(clientId);
             OrderDto orderDto = OrderDto.builder().build();
             orderDto.setClient(clientDto);
             orderDto.setComments(comments);
             AddressDto addressDto = new AddressDto(addressId, "");
             orderDto.setAddress(addressDto);
             orderDto = orderRepository.create(clientId,orderDto);
             response.setData(orderDto);
             response.setMessage("Order created.");
        } catch (PersistenceEntityException e) {
            response.setStatus(ResponseStatus.ERROR);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponse update(String orderId, String addressId) {
        BaseResponse response = new SingleOrderResponse();
        try {
            OrderDto orderDto = orderRepository.update(orderId, addressId);
            response.setData(orderDto);
            response.setMessage("Order address updated.");
        } catch (PersistenceEntityException e) {
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }

    @Override
    public void delete(String orderId) {

    }

    @Override
    public BaseResponse retrieve(String orderId) {
        BaseResponse response = new SingleOrderResponse();
        try {
            OrderDto order = orderRepository.findById(orderId);
            response.setData(order);
            response.setMessage("Order with id " + orderId + " has been found.");
        } catch (PersistenceEntityException e) {
            response.setData(null);
            response.setMessage(e.getMessage());
            response.setStatus(ResponseStatus.ERROR);
        }
        return response;
    }
    @Override
    public BaseResponse retrieve(String status, Integer number) {
        BaseResponse response;
        if (status != null) {
            List orders = orderRepository.findByStatusName(status);
            response = new ListOrderResponse();
            response.setData(orders);
            response.setMessage("Orders with status '" + status);
        }else if (number != null && number > 0) {
            response = new SingleOrderResponse();
            try {
                OrderDto order = orderRepository.findByNumber(number);
                response.setData(order);
                response.setMessage("Order number '" + number + "' found.");
            } catch (PersistenceEntityException e) {
                response.setData(null);
                response.setMessage(e.getMessage());
                response.setStatus(ResponseStatus.ERROR);
            }
        }  else {
                response = new ListOrderResponse();
                response.setData(orderRepository.findAll());
                response.setMessage("Orders list.");
        }
        return response;
    }
}
