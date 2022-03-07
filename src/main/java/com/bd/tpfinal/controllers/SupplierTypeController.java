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

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.services.SupplierTypeService;

@RestController
@RequestMapping(value = "/api/suppliertype")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class SupplierTypeController {
	@Autowired SupplierTypeService supplierTypeService;
	
	@PostMapping("/create")
    public long createSupplierType( @RequestBody SupplierType supplierType) {
		try {
			supplierType = supplierTypeService.createNewSupplierType(supplierType);
			return (supplierType != null) ? supplierType.getId() : -1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }
	
	@GetMapping("/list")
    public List<SupplierType> getSupplierTypeList() {
		try {
			return supplierTypeService.getSupplierTypeList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
}
