
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

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.services.DeliveryManService;

@RestController
@RequestMapping(value = "/api/DeliveryMan")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class DeliveryManController {
	@Autowired private DeliveryManService deliveryManService;

    @PostMapping("/addNew")
	public long addNewDeliveryMan( @RequestBody DeliveryMan deliveryMan ) {
    	try {
    		deliveryMan = deliveryManService.addNewDeliveryMan(deliveryMan);
    		return (deliveryMan != null) ? deliveryMan.getId() : -1;
		}
    	catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }
    
    @GetMapping("/getAll")
    public List<DeliveryMan> getAllDeliveryMan() {
    	try {
    		return deliveryManService.getAllDeliveryMan();
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

    @GetMapping("/getAllPendingOrders")
    public List<Order> getAllPendingOrders(@RequestParam(required = true, name = "idDeliveryMan") long idDeliveryMan) {
    	try {
    		return deliveryManService.getAllPendingOrders(idDeliveryMan);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @GetMapping("/getNextPendingOrder")
    public Order getNextPendingOrder(@RequestParam(required = true, name = "idDeliveryMan") long idDeliveryMan) {
    	try {
    		return deliveryManService.getNextPendingOrder(idDeliveryMan);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @GetMapping("/getTopTen")
    public List<DeliveryMan> getTopTen() {
    	try {
    		return deliveryManService.getTopTen();
		}
    	catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
    @PutMapping("/deliverNextPendingOrder")
    public boolean deliverNextPendingOrder(@RequestParam(required = true, name = "idDeliveryMan") long idDeliveryMan) {
    	try {
    		return deliveryManService.deliverNextPendingOrder(idDeliveryMan);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
    
    
    @PutMapping("/refuseNextPendingOrder")
    public boolean refuseNextPendingOrder(@RequestParam(required = true, name = "idDeliveryMan") long idDeliveryMan) {
    	try {
    		return deliveryManService.refuseNextPendingOrder(idDeliveryMan);
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    		return false;
    	}
    }
    
    @PutMapping("/finishActualOrder")
    public boolean finishActualOrder(@RequestParam(required = true, name = "idDeliveryMan") long idDeliveryMan) {
    	try {
    		return deliveryManService.finishActualOrder(idDeliveryMan);
		}
    	catch (Exception e) {
			e.printStackTrace();
			return false;
		}
    }
}
