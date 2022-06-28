package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.Collection;

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
	
	ProductDTO createProduct(Long supplierId, Product product);
	
	Product readProductId(Long supplierId, String nameProducto);

	Product updateProduct(Long supplierId, String nameProducto, Product productDetails);

	void deleteProductID(Long supplierId, String nameProducto);
	
	Collection<Float> getPricesBetweenDates(Long supplierId, String nameProducto, LocalDate dateFrom, LocalDate dateTo);
	
		
	/* endpoints product type */
	
	ProductType createProductType(ProductType productType);

	ProductType readProductTypeId(Long productTypeId);
	

	/* endpoints supplier */
	
	Supplier createSupplier(Supplier supplier);
	
	Supplier readSupplierId(Long supplierId);

	Supplier addSupplierType(Long supplierId, Long supplierTypeId);
	
		
	/* endpoints supplier type */

	SupplierType createSupplierType(SupplierType supplierType);
	
	SupplierType readSupplierTypeId(Long supplierTypeId);
	
		
	/* endpoints product, product type, supplier, supplier type */ 
	
	Collection<OrderItemsDTO> getOrderMasProductsForSupplier(Long supplierId)throws Exception;
	
	Collection<SupplierDTO> getAllSuppliersForSupplierType(Long supplierTypeId);
	
	Collection<ProductDTO> getAllProductsAndTypesForSupplier(Long supplierId);
	
	Collection<SupplierOrderDTO> getDiezMayorOrderSent();
	
	Collection<SupplierProductsTypesDTO> getSuppliersWithProductsAllTypes();

	Collection<ProductTypePriceDTO> getPricesAvgForProducts();

	Collection<SupplierQualificationDTO> getQualificationForSuppliers();

}
