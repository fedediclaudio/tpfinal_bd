package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.List;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;

public interface ProductService {
	
	Product createNewProduct(Product product) throws Exception;
	boolean updateProduct(Product product) throws Exception;
	boolean changeProductPrice(long idProduct, float newPrice) throws Exception;
	boolean deleteProduct(long idProduct) throws Exception;
	List<Product> getProductList() throws Exception;
	List<HistoricalProductPrice> getHistoricalPricesFromProduct(long idProduct) throws Exception;
	List<Product> getProductsFromSupplier(long idSupplier) throws Exception;
	List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates(LocalDate dateFrom, LocalDate dateTo) throws Exception;
	
}
