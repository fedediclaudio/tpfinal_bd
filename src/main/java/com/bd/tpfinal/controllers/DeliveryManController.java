package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.services.DeliveryManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/deliveryman")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class DeliveryManController {

    @Autowired
    private DeliveryManService deliveryManService;

    // Obtener los diez repartidores con mayor puntaje.
    @GetMapping("/top-mayor-puntaje")
    public List<DeliveryMan> getTop10RepartidoresConMayorPuntaje(){
        return deliveryManService.getTop10RepartidoresMayorPuntaje();
    }

}
