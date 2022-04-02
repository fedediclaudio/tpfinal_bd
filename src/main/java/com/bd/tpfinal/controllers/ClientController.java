package com.bd.tpfinal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.ClientService;

@RestController
@RequestMapping(value = "/api/client")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ClientController {
	@Autowired private ClientService clientService;
	
	@PostMapping("/addNew")
	public long addNewClient( @RequestBody Client client ) {
    	try {
    		client = clientService.addNewClient(client);
    		return (client != null) ? client.getId() : -1;
		}
    	catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }
	
	@PostMapping("/addNewAddress")
	public long addNewAddress( @RequestBody Address address ) {
    	try {
    		address = clientService.addNewAddress(address);
    		return (address != null) ? address.getId() : -1;
		}
    	catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }
	
	@GetMapping("/getAll")
    public List<Client> getAllClients() {
    	try {
    		return clientService.getAllClients();
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@GetMapping("/getAddresses")
    public List<Address> getAddresses(@RequestParam(required = true, name = "idClient") long idClient) {
    	try {
    		return clientService.getAddresses(idClient);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@GetMapping("/getAllOrders")
	public List<Order> getAllOrders(@RequestParam(required = true, name = "idClient") long idClient) {
		try {
			return clientService.getAllOrders(idClient);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    @GetMapping("/getAllPendingOrders")
    public List<Order> getAllPendingOrders(@RequestParam(required = true, name = "idClient") long idClient) {
    	try {
    		return clientService.getAllPendingOrders(idClient);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @GetMapping("/getNextPendingOrder")
    public Order getNextPendingOrder(@RequestParam(required = true, name = "idClient") long idClient) {
    	try {
    		return clientService.getNextPendingOrder(idClient);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @PutMapping("/cancelPendingOrder")
    public boolean cancelPendingOrder(@RequestParam(required = true, name = "idClient") long idClient,
    								  @RequestParam(required = true, name = "orderNumber") int orderNumber) {
    	try {
    		return clientService.cancelPendingOrder(idClient, orderNumber);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
}
