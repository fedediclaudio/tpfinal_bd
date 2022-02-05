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
	
	@PostMapping("/createOrder")
    public int createOrder( @RequestParam(name = "idClient") long idClient ) {
		try {
			Order order = orderService.createOrder(idClient);
			return order.getNumber();
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }
	
	@PutMapping("/assignAddressToOrder")
    public boolean assignAddressToOrder( @RequestParam(name = "idOrder") long idOrder,
    									 @RequestParam(name = "idAddress") long idAddress) {
		try {
			return orderService.assignAddressToOrder(idOrder, idAddress);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	@PutMapping("/addProductToOrder")
	public boolean addProductToOrder( @RequestParam(name = "idOrder") long idOrder,
									  @RequestParam(name = "idProduct") long idProduct,
									  @RequestParam(name = "quantity") int quantity, 
									  @RequestParam(name = "description") String description) {
		try {
			return orderService.addProductToOrder(idOrder, idProduct, quantity, description);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@PutMapping("/cancel")
    public boolean cancelOrder( @RequestParam(name = "idOrder") long idOrder ) {
		try {	
			return orderService.cancel( idOrder );
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	@PutMapping("/confirmOrder")
    public boolean confirmOrder( @RequestParam(name = "idOrder") long idOrder ) {
		try {
			return orderService.confirmOrder(idOrder);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
}
