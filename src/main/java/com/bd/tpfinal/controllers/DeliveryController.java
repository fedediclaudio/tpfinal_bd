package com.bd.tpfinal.controllers;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class DeliveryController {

//    @Autowired
//    private DeliveryService service;

    @GetMapping("/test")
    public String test(){
        return "OK!!!!";
    }

    /*
    *       Controllador de la aplicacion, aqui se definen los endpoints
     */

}
