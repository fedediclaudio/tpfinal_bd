package com.bd.tpfinal.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.services.ProductService;

@RestController
@RequestMapping(value = "/api/product")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
public class ProductController {
	@Autowired ProductService productService;
	
	@PostMapping("/create")
    public long createProduct(@RequestBody Product product) {
		try {
			product = productService.createNewProduct(product);
			return (product != null) ? product.getId() : -1;
		}
		catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
    }
	
	@PutMapping("/updateProduct")
	public boolean updateProduct(@RequestBody Product product) {
		try {
			return productService.updateProduct(product);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@PutMapping("/changePrice")
	public boolean changeProductPrice(@RequestParam(name = "idProduct") long idProduct, @RequestParam(name = "newPrice") float newPrice) {
		try {
			return productService.changeProductPrice(idProduct, newPrice);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@DeleteMapping("/deleteProduct")
	public boolean deleteProduct(@RequestParam(name = "idProduct") long idProduct) {
		try {
			return productService.deleteProduct(idProduct);
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@GetMapping("/list")
    public List<Product> getProductList() {
		try {
			return productService.getProductList();
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@GetMapping("/getHistoricalPrices")
    public List<HistoricalProductPrice> getHistoricalPricesFromProduct( @RequestParam(name = "idProduct") long idProduct ) {
		try {
			return productService.getHistoricalPricesFromProduct(idProduct);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
	
	@GetMapping("/getProductsFromSupplier")
	public List<Product> getProductsFromSupplier( @RequestParam(name = "idSupplier") long idSupplier) {
		try {
			return productService.getProductsFromSupplier(idSupplier);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@GetMapping("/getHistoricalPricesBetweenTwoDates")
	public List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates( @RequestParam(name = "dateFrom") 
																			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
																			LocalDate dateFrom,
																			@RequestParam(name = "dateTo") 
																			@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
																			LocalDate dateTo) {
		try {
			return productService.getHistoricalPricesBetweenTwoDates(dateFrom, dateTo);
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
