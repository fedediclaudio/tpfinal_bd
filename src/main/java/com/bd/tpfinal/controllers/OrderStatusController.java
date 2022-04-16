package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.services.OrderStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderStatus")
public class OrderStatusController
{
    private final OrderStatusService orderStatusService;

    @Autowired
    public OrderStatusController(OrderStatusService orderStatusService)
    {
        this.orderStatusService = orderStatusService;
    }

    //////  POST
    @PostMapping(value = "/new")
    public void addOrderStatus(@RequestBody OrderStatus newOrderStatus)
    {
        this.orderStatusService.addOrderStatus(newOrderStatus);
    }

    //////  GET
    @GetMapping(value = "/all")
    public List<OrderStatus> getAll()
    {
        return this.orderStatusService.getAll();
    }
}
