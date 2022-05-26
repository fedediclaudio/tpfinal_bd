package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.DeliveryManService;
import com.bd.tpfinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/deliveryman")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class DeliveryManController {

    @Autowired
    private DeliveryManService deliveryManService;
    @Autowired
    private OrderService orderService;

    // Obtener los diez repartidores con mayor puntaje.
    @GetMapping("/top-mayor-puntaje")
    public List<DeliveryMan> getTop10RepartidoresConMayorPuntaje(){
        return deliveryManService.getTop10RepartidoresMayorPuntaje();
    }
    // Confirmar un pedido.
    @PutMapping("/confirmar-pedido/{order_id}")
    public boolean confirmarPedido(@PathVariable long order_id) {
        Optional<DeliveryMan> deliveryMan = deliveryManService.getFreeAndActiveDeliveryMan();
        if(deliveryMan.isPresent()) {
            DeliveryMan currentDeliveryMan = deliveryMan.get();
            Optional<Order> order = orderService.findOrderById(order_id);
            if(order.isPresent()) {
                order.get().setDeliveryMan(currentDeliveryMan);
                currentDeliveryMan.setFree(false);
                orderService.guardarOrder(order.get());
                deliveryManService.guardarDeliveryMan(currentDeliveryMan);
                return true;
            }
        }
        return false;
    }
}
