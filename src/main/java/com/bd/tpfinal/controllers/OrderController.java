package com.bd.tpfinal.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
	
	@GetMapping("/get")
    public Order getOrder( @RequestParam(name = "orderNumber") int orderNumber ) {
		Order order = null;
		try {
			order = orderService.getOrder(orderNumber);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return order;
    }
	
	@GetMapping("/getOrdersFromSupplier")
	public List<Order> getOrdersFromSupplier(@RequestParam(name = "idSupplier") long idSupplier){
		List<Order> orders = null;
		try {
			orders = orderService.getOrdersFromSupplier(idSupplier);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
	
	@GetMapping("/getHighestPriceOrderOfDate")
	public Order getOrdersFromSupplier(@RequestParam(name = "date") 
										@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
										LocalDate date){
		Order order = null;
		try {
			order = orderService.getHighestPriceOrderOfDate(date);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return order;
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
	
	@PutMapping("/setQualification")
    public boolean setQualification( @RequestParam(name = "orderNumber") int orderNumber, 
    								 @RequestParam(name = "score") int score, 
    								 @RequestParam(name = "comment") String comment) {
		try {
			return orderService.setQualification(orderNumber, score, comment);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
}
