package com.bd.tpfinal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.OrderService;

@RestController
@RequestMapping(value = "/api/order")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT ,RequestMethod.DELETE})
public class OrderController {
	@Autowired OrderService orderService;
	
	@GetMapping("/createOrder")
    public Order createOrder() {
		return new Order();
    }

	@GetMapping("/assign")
    public boolean assignOrder() {
		return true;
    }
	
	@GetMapping("/cancel")
    public boolean cancelOrder( @RequestParam(name = "idOrder") long idOrder ) {
		try {	
			return orderService.cancel( idOrder );
		}
		catch (Exception e) {
			// TODO: handle exception
			return false;
		}
    }
}
