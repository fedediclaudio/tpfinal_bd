package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.List;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.projections.ProductAndType;

public interface ProductService {
	
	Product createNewProduct(Product product) throws Exception;
	boolean updateProduct(Product product) throws Exception;
	boolean changeProductPrice(String idProduct, float newPrice) throws Exception;
	boolean deleteProduct(String idProduct) throws Exception;
	List<Product> getProductList() throws Exception;
	List<HistoricalProductPrice> getHistoricalPricesFromProduct(String idProduct) throws Exception;
	List<ProductAndType> getProductsFromSupplier(String idSupplier) throws Exception;
	List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates(LocalDate dateFrom, LocalDate dateTo, String idProduct) throws Exception;
}
