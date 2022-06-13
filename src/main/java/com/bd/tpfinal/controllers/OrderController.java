package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/order")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class OrderController {
	@Autowired OrderService orderService;
	
	@PostMapping("/")
    public String createOrder( @RequestParam(name = "idClient") String idClient ) {
		try {
			Order order = orderService.createOrder(idClient);
			return (order != null) ? order.getNumber() : "";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
	
	@GetMapping("/")
    public Order getOrder( @RequestParam(name = "orderNumber") String orderNumber ) {
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
	public List<Order> getOrdersFromSupplier(@RequestParam(name = "idSupplier") String idSupplier){
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
    public boolean assignAddressToOrder( @RequestParam(name = "orderNumber") String orderNumber,
    									 @RequestParam(name = "idAddress") String idAddress) {
		try {
			return orderService.assignAddressToOrder(orderNumber, idAddress);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	@PutMapping("/addProduct")
	public boolean addProductToOrder( @RequestParam(name = "orderNumber") String orderNumber,
									  @RequestParam(name = "idProduct") String idProduct,
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
    public boolean cancelOrder( @RequestParam(name = "orderNumber") String orderNumber ) {
		try {	
			return orderService.cancel( orderNumber );
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	@PutMapping("/confirm")
    public boolean confirmOrder( @RequestParam(name = "orderNumber") String orderNumber ) {
		try {
			return orderService.confirmOrder(orderNumber);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
	
	@PutMapping("/setQualification")
    public boolean setQualification( @RequestParam(name = "orderNumber") String orderNumber, 
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

	@GetMapping("/getOrdersFromSupplierWithMaxProducts")
	public List<Order> getOrdersFromSupplierWithMaxProducts(@RequestParam(name = "idSupplier") String idSupplier){
		List<Order> orders = null;
		try {
			orders = orderService.getOrdersFromSupplierWithMaxProducts(idSupplier);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return orders;
	}
}
