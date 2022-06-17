package com.bd.tpfinal.controllers;

import java.util.List;

import com.bd.tpfinal.model.dto.ProductAvgDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.services.ProductTypeService;

@RestController
@RequestMapping(value = "/api/producttype")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ProductTypeController {
	@Autowired ProductTypeService productTypeService;
	
	@PostMapping("/create")
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
	
	@GetMapping("/list")
    public List<ProductType> getProductTypeList() {
		try {
			return productTypeService.getProductTypeList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }

	@GetMapping("/Query13")
	public List<ProductAvgDTO> getAveragePriceOfProducts() {
		try {
			return productTypeService.getAveragePriceOfProducts();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
