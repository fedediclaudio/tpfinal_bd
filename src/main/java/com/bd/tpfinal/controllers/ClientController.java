package com.bd.tpfinal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.ClientService;

@RestController
@RequestMapping(value = "/api/client")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ClientController {
	@Autowired private ClientService clientService;
	
	
	@GetMapping("/getAllClients")
    public List<Client> getAllClients() {
    	try {
    		return clientService.getAllClients();
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @GetMapping("/getAllPendingOrders")
    public List<Order> getAllPendingOrders(@RequestParam(required = true, name = "idDeliveryMan") long idDeliveryMan) {
    	try {
    		return clientService.getAllPendingOrders(idDeliveryMan);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @GetMapping("/getNextPendingOrder")
    public Order getNextPendingOrder(@RequestParam(required = true, name = "idDeliveryMan") long idDeliveryMan) {
    	try {
    		return clientService.getNextPendingOrder(idDeliveryMan);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @PutMapping("/cancelPendingOrder")
    public boolean cancelPendingOrder(@RequestParam(required = true, name = "idDeliveryMan") long idDeliveryMan,
    								  @RequestParam(required = true, name = "idOrder") long idOrder) {
    	try {
    		return clientService.cancelPendingOrder(idDeliveryMan, idOrder);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
}
