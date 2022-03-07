package com.bd.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.OrderService;

@RestController
@RequestMapping(value = "/api/order")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class OrderController {
	@Autowired OrderService orderService;
	
	@PostMapping("/create")
    public int createOrder( @RequestParam(name = "idClient") long idClient ) {
		try {
			Order order = orderService.createOrder(idClient);
			return (order != null) ? order.getNumber() : -1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }
	
	@PutMapping("/assignAddress")
    public boolean assignAddressToOrder( @RequestParam(name = "orderNumber") int orderNumber,
    									 @RequestParam(name = "idAddress") long idAddress) {
		try {
			return orderService.assignAddressToOrder(orderNumber, idAddress);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	@PutMapping("/addProduct")
	public boolean addProductToOrder( @RequestParam(name = "orderNumber") int orderNumber,
									  @RequestParam(name = "idProduct") long idProduct,
									  @RequestParam(name = "quantity") int quantity, 
									  @RequestParam(name = "description") String description) {
		try {
			return orderService.addProductToOrder(orderNumber, idProduct, quantity, description);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@PutMapping("/cancel")
    public boolean cancelOrder( @RequestParam(name = "orderNumber") int orderNumber ) {
		try {	
			return orderService.cancel( orderNumber );
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	@PutMapping("/confirm")
    public boolean confirmOrder( @RequestParam(name = "orderNumber") int orderNumber ) {
		try {
			return orderService.confirmOrder(orderNumber);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
}
