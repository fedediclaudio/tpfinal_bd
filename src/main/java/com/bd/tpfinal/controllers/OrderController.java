package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController
{
    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService)
    {
        this.orderService = orderService;
    }

    @PostMapping(value = "/new")
    public void addOrder(@RequestBody Order newOrder)
    {
        this.orderService.addOrder(newOrder);
    }

    @GetMapping("/all")
    public List<Order> getAll()
    {
        return this.orderService.getAll();
    }

    //anda bien
    @PostMapping("/test")
    public int test()
    {
        //return newOrder.getNumber();
        return 1;
    }
}
