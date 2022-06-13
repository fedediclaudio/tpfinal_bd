package com.bd.tpfinal.services;

import com.bd.tpfinal.model.ProductType;

import java.util.List;

public interface ProductTypeService {
	
	ProductType createNewProductType(ProductType productType) throws Exception;
	List<ProductType> getProductTypeList() throws Exception;
	List<ProductType> getAveragePriceOfProductsByType() throws Exception;
	
}
