package com.bd.tpfinal.services;

import java.util.List;

import com.bd.tpfinal.model.ProductType;

public interface ProductTypeService {
	
	ProductType createNewProductType(ProductType productType) throws Exception;
	List<ProductType> getProductTypeList() throws Exception;
	
}
