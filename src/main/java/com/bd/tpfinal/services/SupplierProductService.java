package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.bson.types.ObjectId;

import com.bd.tpfinal.dto.DateDTO;
import com.bd.tpfinal.dto.OrderItemsDTO;
import com.bd.tpfinal.dto.ProductDTO;
import com.bd.tpfinal.dto.ProductTypePriceDTO;
import com.bd.tpfinal.dto.SupplierDTO;
import com.bd.tpfinal.dto.SupplierOrderDTO;
import com.bd.tpfinal.dto.SupplierProductsTypesDTO;
import com.bd.tpfinal.dto.SupplierQualificationDTO;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;

public interface SupplierProductService {

	/* endpoints product */
	
	ProductDTO createProduct(ObjectId supplierId, Product product);
	
	Product readProduct(ObjectId supplierId, String nameProducto);

	Product updateProduct(ObjectId supplierId, String nameProducto, Product productDetails);

	void deleteProductID(ObjectId supplierId, String nameProducto);
	
	Collection<DateDTO> getPricesBetweenDates(ObjectId supplierId, String nameProducto, LocalDate dateFrom, LocalDate dateTo);
	
		
	/* endpoints product type */
	
	ProductType createProductType(ProductType productType);

	ProductType readProductTypeId(ObjectId productTypeId);
	

	/* endpoints supplier */
	
	Supplier createSupplier(Supplier supplier);
	
	Supplier readSupplierId(ObjectId supplierId);

	Supplier addSupplierType(ObjectId supplierId, ObjectId supplierTypeId);
	
		
	/* endpoints supplier type */

	SupplierType createSupplierType(SupplierType supplierType);
	
	SupplierType readSupplierTypeId(ObjectId supplierTypeId);
	
		
	/* endpoints product, product type, supplier, supplier type */ 
	
	OrderItemsDTO getOrderMasProductsForSupplier(ObjectId supplierId)throws Exception;
	
	Collection<SupplierDTO> getAllSuppliersForSupplierType(ObjectId supplierTypeId);
	
	Collection<ProductDTO> getAllProductsAndTypesForSupplier(ObjectId supplierId);
	
	Collection<SupplierOrderDTO> getDiezMayorOrderSent();
	
	Collection<SupplierProductsTypesDTO> getSuppliersWithProductsAllTypes();

	List<ProductTypePriceDTO> getPricesAvgForProducts();

	Collection<SupplierQualificationDTO> getQualificationForSuppliers();

}
