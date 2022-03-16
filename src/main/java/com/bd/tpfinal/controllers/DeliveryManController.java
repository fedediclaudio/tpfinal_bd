package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.services.DeliveryManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @PostMapping(value="/new")
    public void addDeliveryMan(@RequestBody DeliveryMan newDeliveryMan)
    {
        this.deliveryManService.addDeliveryMan(newDeliveryMan);
    }

    @GetMapping("/all")
    public List<DeliveryMan> getAll()
    {
        return this.deliveryManService.getAll();
    }

    @GetMapping("/test")
    public String test(){
        return "OK!";
    }

    /*
    *       Controllador de la aplicacion, aqui se definen los endpoints
     */

}
