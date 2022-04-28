package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.AddressService;
import com.bd.tpfinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    ///////     POST

    //TODO: necesito validar desde el repositorio que la id de Address pasada en la newOrder pertenece al id del Client
    //por ahora intento desde acá.
    @PostMapping(value = "/new")
    public Order addOrder(@RequestBody Order newOrder)
    {
        return this.orderService.newOrder(newOrder);
    }
    //public void newOrder(@RequestBody Order newOrder) throws AddressEquivocadaException
    //{
    //    Order order = newOrder;
    //    Address address_of_order = order.getAddress();Client client_of_order = order.getClient();
    //    Long idClient = client_of_order.getId();
    //    //Long idAddress = address_of_order.getId();
    //    List<Address> addresses_of_client = this.addressService.getAllByIdUser(idClient);

    //    if(addresses_of_client.contains(address_of_order))
    //        this.orderService.newOrder(newOrder);
    //    else
    //        throw new AddressEquivocadaException(address_of_order);

    //}

    //anda bien
    @PostMapping("/test")
    public int test()
    {
        //return newOrder.getNumber();
        return 1;
    }

    ///////     GET

    @GetMapping("/all")
    public List<Order> getAll()
    {
        return this.orderService.getAll();
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
