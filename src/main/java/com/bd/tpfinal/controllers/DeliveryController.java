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
    public ResponseEntity<BaseResponse> getDeliveryMen(@RequestParam(value = "delivery_man_id", required = false) String id,
                                                       @RequestParam(value = "best_ranked", required = false) Boolean bestRanked,
                                                       @RequestParam(value = "free", required = false) Boolean free){
        BaseResponse response = null;
        if (id != null){
            response = deliveryService.retrieve(id);
        } else if (free != null && free) {
            response = deliveryService.retrieveFree();
        } else if (bestRanked != null && bestRanked) {
            response = deliveryService.getMostScoredDeliveryMen();
        } else {
            response = deliveryService.retrieve();
        }
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @GetMapping("/{delivery_man_id}")
    public ResponseEntity<BaseResponse> getDeliveryMan(@PathVariable(value = "delivery_man_id") String id){
        BaseResponse response = null;
        response = deliveryService.retrieve(id);
        return new ResponseEntity<>(response, responseStatus(response));
    }


    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateDeliveryManRequest createDeliveryManRequest){
        BaseResponse response = null;
        response = deliveryService.createDeliveryMan(createDeliveryManRequest);
        return new ResponseEntity<>(response, responseStatus(response));
    }
}
