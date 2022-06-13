package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.services.SupplierTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/suppliertype")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class SupplierTypeController {
	@Autowired SupplierTypeService supplierTypeService;
	
	@PostMapping("/")
    public String createSupplierType( @RequestBody SupplierType supplierType) {
		try {
			supplierType = supplierTypeService.createNewSupplierType(supplierType);
			return (supplierType != null) ? supplierType.getId() : "";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
	
	@GetMapping("/")
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
