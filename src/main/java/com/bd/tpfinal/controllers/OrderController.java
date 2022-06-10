package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.AddressService;
import com.bd.tpfinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController
{
    private final OrderService orderService;
    private final AddressService addressService;

    @Autowired
    public OrderController(OrderService orderService, AddressService addressService)
    {
        this.orderService = orderService;
        this.addressService = addressService;
    }

    /////// POST

    //1) Agregar un ítem a una orden ya creada.
    //ver ItemController

    @PostMapping(value = "/new")
    public void addOrder(@RequestBody Order newOrder)
    {
        this.orderService.newOrder(newOrder);
    }

    @PostMapping("/test")
    public int test()
    {
        //return newOrder.getNumber();
        return 1;
    }

    //////  PUT

    //2) Confirmar un pedido. Esto implica buscar un repartidor libre y asignarle dicho pedido
    @PutMapping("/{number}/assign")
    public boolean assignOrder(@PathVariable Long number)
    {
        return this.orderService.asignacionDeOrden(number);
    }

    //3) Añadir una calificación a una orden ya completada.
    // Tenga en cuenta que deberá actualizar la calificación del proveedor.
    @PutMapping("/score/{score}/comentario/{comentario}/orden/{orden}")
    public void calificarOrder(@PathVariable float score, @PathVariable String comentario, @PathVariable Long orden)
    {
        this.orderService.calificarOrden(score, comentario, orden);
    }

    //////     GET

    //8) Obtener las órdenes con más productos de un proveedor específico.

    @GetMapping("/supplier/{id_supplier}")
    public List<Order> getBySupplierMaxCantItems(@PathVariable Long id_supplier)
    {
        return this.orderService.getBySupplierMaxCantItems(id_supplier);
    }

    //9) Obtener la orden de mayor precio total de un día dado.
    @GetMapping("/fecha/{fecha}")
    public List<Order> getOrderMaxPricePorFecha(@PathVariable Date fecha)
    {
        return this.orderService.getOrderMaxPricePorFecha(fecha);
    }

    @GetMapping("/all")
    public List<Order> getAll()
    {
        return this.orderService.getAllWithStatus();
    }

    @GetMapping("/id/{id}")
    public Optional<Order> getById(@PathVariable Long id)
    {
        return this.orderService.getById(id);
    }

    @GetMapping("/number/{number}")
    public Order getByNumber(@PathVariable Long number)
    {
        return this.orderService.getByNumber(number);
    }
}
