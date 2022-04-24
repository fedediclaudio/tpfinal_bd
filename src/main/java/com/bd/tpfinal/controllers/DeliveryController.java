package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.request.delivery.CreateDeliveryManRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.services.DeliveryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/delivery")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class DeliveryController extends  BaseController{

    //TODO implementar las siguientes acciones:
    //Obtener los 10 repartidores con mayor puntaje

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @GetMapping
    public ResponseEntity<BaseResponse> getDeliveryMen(@RequestParam(value = "best_ranked", required = false) Boolean bestRanked){
        BaseResponse response = null;
        if (bestRanked == null || !bestRanked){
            response = deliveryService.findAll();
        }else{
            response = deliveryService.getMostScoredDeliveryMen();
        }
        return new ResponseEntity<>(response, responseStatus(response));
    }


    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateDeliveryManRequest createDeliveryManRequest){
        BaseResponse response = null;
        response = deliveryService.createDeliveryMan(createDeliveryManRequest);
        return new ResponseEntity<>(response, responseStatus(response));
    }
}
