package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Prueba;
import com.bd.tpfinal.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prueba")
public class PruebaController
{
    private final PruebaService pruebaService;
@Autowired
    public PruebaController(PruebaService pruebaService)
    {
        this.pruebaService = pruebaService;
    }

    @PostMapping(value = "/new")
    public void addPrueba(@RequestBody Prueba newPrueba)
    {
        this.pruebaService.addPrueba(newPrueba);
    }

    @GetMapping("/all")
    public List<Prueba> getAll()
    {
        return this.pruebaService.getAll();
    }

}
