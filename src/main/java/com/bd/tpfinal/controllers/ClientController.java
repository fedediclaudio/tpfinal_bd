package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/client")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ClientController {
	@Autowired private ClientService clientService;
	
	@PostMapping("/addNew")
	public String addNewClient( @RequestBody Client client ) {
    	try {
    		client = clientService.addNewClient(client);
    		return (client != null) ? client.getId() : "";
		}
    	catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
	
	@PostMapping("/addNewAddress")
	public String addNewAddress( @RequestBody Address address ) {
    	try {
    		address = clientService.addNewAddress(address);
    		return (address != null) ? String.valueOf(address.getId()) : "";
		}
    	catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
	
	@GetMapping("/")
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
    public List<Address> getAddresses(@RequestParam(required = true, name = "idClient") String idClient) {
    	try {
    		return clientService.getAddresses(idClient);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@GetMapping("/getAllOrders")
	public List<Order> getAllOrders(@RequestParam(required = true, name = "idClient") String idClient) {
		try {
			return clientService.getAllOrders(idClient);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

    @GetMapping("/getAllPendingOrders")
    public List<Order> getAllPendingOrders(@RequestParam(required = true, name = "idClient") String idClient) {
    	try {
    		return clientService.getAllPendingOrders(idClient);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @GetMapping("/getNextPendingOrder")
    public Order getNextPendingOrder(@RequestParam(required = true, name = "idClient") String idClient) {
    	try {
    		return clientService.getNextPendingOrder(idClient);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @PutMapping("/cancelPendingOrder")
    public boolean cancelPendingOrder(@RequestParam(required = true, name = "idClient") String idClient,
    								  @RequestParam(required = true, name = "orderNumber") String orderNumber) {
    	try {
    		return clientService.cancelPendingOrder(idClient, orderNumber);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
}
