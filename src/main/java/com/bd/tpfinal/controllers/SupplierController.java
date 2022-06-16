package com.bd.tpfinal.controllers;

import java.util.List;
import java.util.Map;

import com.bd.tpfinal.model.dto.SupplierBadReputation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.services.SupplierService;

@RestController
@RequestMapping(value = "/api/supplier")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class SupplierController {
	@Autowired SupplierService supplierService;
	
	@PostMapping("/create")
    public String createSupplier( @RequestBody Supplier supplier) {
		try {
			supplier = supplierService.createNewSupplier( supplier );
			return (supplier != null) ? supplier.getId() : "";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
	
	@GetMapping("/list")
    public List<Supplier> getSupplierList() {
		try {
			return supplierService.getSupplierList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

	@GetMapping("/listOfType")
	public List<Supplier> getSupplierListFromType(@RequestParam(required = true, name = "idSupplierType") String idSupplierType) {
		try {
			return supplierService.getSupplierListFromType(idSupplierType);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GetMapping("/getTopTen")
    public Map<Supplier, Integer> getTopTen() {
		try {
			return supplierService.getTopTen();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	
	@GetMapping("/getSupplierWithAtLeastOneStar")
	public List<SupplierBadReputation> getSupplierWithAtLeastOneStar() {
		try {
			return supplierService.getSupplierWithAtLeastOneStar();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	@GetMapping("/getSupplierWhoOfferAllProducts")
	public List<Supplier> getSupplierWhoOfferAllProducts() {
		try {
			return supplierService.getSupplierWhoOfferAllProducts();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
