package com.bd.tpfinal.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.services.SupplierService;

@RestController
@RequestMapping(value = "/api/supplier")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class SupplierController {
	@Autowired SupplierService supplierService;
	
	@PostMapping("/create")
    public long createSupplier( @RequestBody Supplier supplier) {
		try {
			supplier = supplierService.createNewSupplier( supplier );
			return (supplier != null) ? supplier.getId() : -1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
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
}
