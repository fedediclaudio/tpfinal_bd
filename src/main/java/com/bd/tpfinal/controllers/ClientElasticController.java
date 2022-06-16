package com.bd.tpfinal.controllers;

import com.bd.tpfinal.services.ClientElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "/api/clientElastic")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ClientElasticController {

    @Autowired
    ClientElasticService clientElasticService;

    @GetMapping("/")
    public Collection<?> getAllClientFromElastic() {
        return clientElasticService.getAll();
    }
}
