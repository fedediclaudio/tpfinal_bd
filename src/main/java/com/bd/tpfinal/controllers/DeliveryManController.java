package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.services.DeliveryManService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    //////  POST

    @PostMapping(value="/new")
    public void addDeliveryMan(@RequestBody DeliveryMan newDeliveryMan)
    {
        this.deliveryManService.newDeliveryMan(newDeliveryMan);
    }

    //////  GET

    //10) Obtener los diez repartidores con mayor puntaje.
    @GetMapping("/TopTen")
    public List<DeliveryMan> getAllOrderByScore()
    {
        return this.deliveryManService.getAllOrderByScore();
    }


    @GetMapping("/all")
    public List<DeliveryMan> getAll()
    {
        return this.deliveryManService.getAll();
    }

    @GetMapping("/id/{id}")
    public Optional<DeliveryMan> getById(@PathVariable Long id)
    {
        return this.deliveryManService.getById(id);
    }
}
