package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;

public interface ProductService {
	
	Product createNewProduct(String name, float price, float weight, String description, long idSupplier, long idProductType) throws Exception;
	boolean changeProductPrice(long idProduct, float newPrice) throws Exception;
	List<Product> getProductList() throws Exception;
	List<HistoricalProductPrice> getHistoricalPricesFromProduct(long idProduct) throws Exception;
	
}
