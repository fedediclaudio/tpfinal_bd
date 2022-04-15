package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.response.delivery.DeliveryResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/delivery")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class DeliveryController {

    //TODO implementar las siguientes acciones:
    //Obtener los 10 repartidores con mayor puntaje


    @GetMapping("/best_ranked")
    public ResponseEntity<DeliveryResponseDto> maxScore(){
        return null;
    }
}
