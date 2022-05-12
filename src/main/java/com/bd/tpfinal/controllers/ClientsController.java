package com.bd.tpfinal.controllers;

import com.bd.tpfinal.dtos.request.clients.CreateAddressRequest;
import com.bd.tpfinal.dtos.request.clients.CreateClientRequest;
import com.bd.tpfinal.dtos.response.BaseResponse;
import com.bd.tpfinal.services.impl.ClientsServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/clients")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class ClientsController extends BaseController {

    private final ClientsServiceImpl clientService;

    public ClientsController(ClientsServiceImpl clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<BaseResponse> create(@RequestBody CreateClientRequest request){
        BaseResponse response = clientService.create(request);
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse> addAddress(@PathVariable("id") String id, @RequestBody CreateAddressRequest request){
        BaseResponse response = clientService.addAddress(id,request);
        return new ResponseEntity<>(response, responseStatus(response));
    }

    @GetMapping
    public ResponseEntity<BaseResponse> retrieve(@RequestParam(value = "id", required = false) String id){
        BaseResponse response = null;
        if (id != null)
            response = clientService.retrieve(id);
        else
            response = clientService.retrieve();
        return new ResponseEntity<>(response, responseStatus(response));
    }
}
