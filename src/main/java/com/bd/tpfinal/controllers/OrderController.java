package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value="/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})

public class OrderController {
    @Autowired
    private OrderService orderService;

    @PostMapping("/nuevoItemForOrder/{order_id}")
    public Item nuevoItemForOrder(@RequestBody Item item, @PathVariable long order_id) throws Exception {
        Optional<Item> itemAgregado = orderService.AgregarItemAOrdenCreada(order_id, item);
        return (itemAgregado.get());
    }
}
