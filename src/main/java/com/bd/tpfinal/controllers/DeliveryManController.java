package com.bd.tpfinal.controllers;

import com.bd.tpfinal.services.DeliveryManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("")
@RequestMapping(value = "/deliveryMan")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class DeliveryManController
{

    private final DeliveryManService deliveryManService;
    @Autowired

    public DeliveryManController(DeliveryManService deliveryManService)
    {
        this.deliveryManService = deliveryManService;
    }




    @GetMapping("/test")
    public String test(){
        return "OK!";
    }

    /*
    *       Controllador de la aplicacion, aqui se definen los endpoints
     */

}
