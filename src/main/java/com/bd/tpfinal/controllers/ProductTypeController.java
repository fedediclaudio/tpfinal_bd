package com.bd.tpfinal.controllers;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.services.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/producttype")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ProductTypeController {
	@Autowired ProductTypeService productTypeService;
	
	@PostMapping("/")
    public String createProductType( @RequestBody ProductType productType) {
		try {
			productType = productTypeService.createNewProductType(productType);
			return (productType != null) ? productType.getId() : "";
		}
		catch (Exception e) {
			e.printStackTrace();
			return "";
		}
    }
	
	@GetMapping("/")
    public List<ProductType> getProductTypeList() {
		try {
			return productTypeService.getProductTypeList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

	@GetMapping("/getAveragePriceOfProductsByType")
	public List<ProductType> getAveragePriceOfProductsByType() {
		try {
			return productTypeService.getAveragePriceOfProductsByType();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
