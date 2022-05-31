package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value="/api/orders")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})

public class OrderController {
    @Autowired
    private OrderService orderService;

    // Agregar un item a una orden ya creada.
    @PostMapping("/nuevo-item-Order/{order_id}")
    public Item nuevoItemForOrder(@RequestBody Item item, @PathVariable long order_id) throws Exception {
        Optional<Item> itemAgregado = orderService.agregarItemAOrdenCreada(order_id, item);
        if(itemAgregado.isPresent()) {
            return itemAgregado.get();
        }
        return null;
    }
    // Obtener las ordenes con mas productos de un proveedor especifico
    @GetMapping("/mayor-cantidad-productos-supplier/{supplier_id}")
    public List<Order> getMayorCantidadProductosPorSupplier(@PathVariable long supplier_id) {
        return orderService.getOrdersConMasProductosDeSupplier(supplier_id);
    }
    // Obtener la orden de mayor precio total de un dia dado
    @GetMapping("/mayor-precio-del-dia/{fecha_dia}")
    public Order getOrderMayorPrecioDelDia(@PathVariable @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate fecha_dia) {
        Optional<Order> order = orderService.getOrderConMayorPrecioDelDia(fecha_dia);
        if(order.isPresent())
            return order.get();
        return null;
    }
}
