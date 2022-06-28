package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bd.tpfinal.dto.FactoryDTO;
import com.bd.tpfinal.dto.OrderItemsDTO;
import com.bd.tpfinal.dto.ProductDTO;
import com.bd.tpfinal.dto.ProductTypePriceDTO;
import com.bd.tpfinal.dto.SupplierDTO;
import com.bd.tpfinal.dto.SupplierOrderDTO;
import com.bd.tpfinal.dto.SupplierProductsTypesDTO;
import com.bd.tpfinal.dto.SupplierQualificationDTO;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductPK;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.SupplierType;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import com.bd.tpfinal.repositories.SupplierTypeRepository;


@Service
public class SupplierProductServiceImpl implements SupplierProductService{
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	@Autowired
	private ProductTypeRepository productTypeRepository;
	
	@Autowired
	private SupplierTypeRepository supplierTypeRepository;
	
	@Autowired
    private OrderRepository orderRepository;
	
	 /**
	 * Es el objeto encargado de crear los DTOs.
	 */
    @Autowired
	private FactoryDTO dtoFactory;

        
	
    public SupplierProductServiceImpl() { /* empty for framework */	}    
    
    /* endpoints product */
    
	@Override
	@Transactional
	public ProductDTO createProduct(Long supplierId, Product product) {
		Set<ProductType> types = new HashSet<>();
		product.getTypes().forEach(type ->{
			if (type.getId() != null) {
				ProductType prodTypeP = this.productTypeRepository.findById(type.getId()).get();
				types.add(prodTypeP);
			}
		});
		/* Guardo el precio en Historical con la fecha de inicio */
		product.addHistoricalProductPrice(new HistoricalProductPrice(product.getPrice(), LocalDate.now()));
		product.setTypes(types);
		product.setActive(true);
		this.productRepository.save(product);
		/* Armado del DTO */
		List<String> colNamesType = new ArrayList<String>();
		product.getTypes().forEach( t ->{
			colNamesType.add(t.getName());
		});
		return new ProductDTO(product.getProductPK().getName(), product.getPrice(), product.getWeight(), product.getDescription(), supplierId, colNamesType);
	}
    
    @Override
    @Transactional(readOnly = true)
	public Product readProductId(Long supplierId, String nameProducto) {
		Product product = this.productRepository.findById(new ProductPK(nameProducto,supplierId)).orElseThrow(() -> new NullPointerException("Product not found for this name: " + nameProducto));
		return product;
	}

    @Override
	@Transactional
    public Product updateProduct(Long supplierId, String nameProducto, Product productDetails){		
    	Product product = this.readProductId(supplierId,nameProducto);	
    	/*  Si cambió el precio: agrego la fecha de fin del precio anterior y guardo el nuevo precio en el historial */
    	List<HistoricalProductPrice> prices = product.getPrices();
    	if(product.getPrice()!= productDetails.getPrice()){
			int n = prices.size();
			for (int i = 0; i < n; i++) {
				if (prices.get(i).getPrice() == product.getPrice()){
					prices.get(i).setFinishDate(LocalDate.now());  
					break;
				}
			}
			product.setPrice(productDetails.getPrice());
			product.addHistoricalProductPrice(new HistoricalProductPrice(product.getPrice(), LocalDate.now()));
			product.setPrices(prices);
		}
		product.setWeight(productDetails.getWeight());
		product.setDescription(productDetails.getDescription());
		/* Add producttypes new */
		Set<ProductType> types = new HashSet<>();
		types.addAll(product.getTypes());
		productDetails.getTypes().forEach(type ->{
			if (type.getId() != null) {
				ProductType prodTypeP = this.productTypeRepository.findById(type.getId()).get();
				types.add(prodTypeP); 
			}
		});
		product.setTypes(types);
		return this.productRepository.save(product);		
	}
    
	@Override
	@Transactional
	public void deleteProductID(Long supplierId, String nameProducto) {
		Product prod = this.readProductId(supplierId, nameProducto);	
		List<Long> aListIdOrders = this.orderRepository.getOrdersWithProduct(prod);
		if (aListIdOrders.isEmpty())
			this.productRepository.deleteById(new ProductPK(nameProducto,supplierId));
		else{
			prod.setActive(false);
			this.productRepository.save(prod);
		}
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<Float> getPricesBetweenDates(Long supplierId, String nameProducto,
			LocalDate dateFrom, LocalDate dateTo) {
		return this.productRepository.getPricesBetweenDates(supplierId, nameProducto, dateFrom, dateTo);
	}
	
	/* endpoints product type */

	@Override
	@Transactional
	public ProductType createProductType(ProductType productType) {
		return this.productTypeRepository.save(productType);
	}

	@Override
	@Transactional(readOnly = true)
	public ProductType readProductTypeId(Long productTypeId) {
		ProductType productType = this.productTypeRepository.findById(productTypeId).orElseThrow(() -> new NullPointerException("Product Type not found for this id: " + productTypeId));
		return productType;
	}
	
	
	/* endpoints supplier */
	
	@Override
	@Transactional
	public Supplier createSupplier(Supplier supplier) {
		Set<SupplierType> supplierstype = new HashSet<>();
		supplier.getTypes().forEach(type ->{
			if (type.getId() != null) {
				SupplierType supplierTypeP = this.supplierTypeRepository.findById(type.getId()).get();
				supplierstype.add(supplierTypeP);
			}
		});
		supplier.setTypes(supplierstype);
		return this.supplierRepository.save(supplier);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Supplier readSupplierId(Long supplierId) {
		Supplier supplier = this.supplierRepository.findById(supplierId).orElseThrow(() -> new NullPointerException("Supplier not found for this id: " + supplierId));
		return supplier;
	}
	
	@Override
	@Transactional
	public Supplier addSupplierType(Long supplierId, Long supplierTypeId) {
		Supplier supplier = this.readSupplierId(supplierId);
		SupplierType supplierType = this.readSupplierTypeId(supplierTypeId);
		supplier.addSupplierType(supplierType);
		return this.supplierRepository.save(supplier);
		
	}

	/* endpoints supplier type */
	
	@Override
	@Transactional
	public SupplierType createSupplierType(SupplierType supplierType) {
		return this.supplierTypeRepository.save(supplierType);
	}
	
	@Override
	@Transactional(readOnly = true)
	public SupplierType readSupplierTypeId(Long supplierTypeId) {
		SupplierType supplierType =  this.supplierTypeRepository.findById(supplierTypeId).orElseThrow(() -> new NullPointerException("Supplier Type not found for this id: " + supplierTypeId));
		return supplierType;
	}
	
	
	/* endpoints product, product type, supplier, supplier type */ 
	
	@Override
	@Transactional(readOnly = true) 
	public Collection<OrderItemsDTO> getOrderMasProductsForSupplier(Long supplierId)throws Exception {
		 return this.orderRepository.getOrdenesForSupplier(supplierId);
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<SupplierDTO> getAllSuppliersForSupplierType(Long supplierTypeId) {		
		SupplierType supplierType =  this.readSupplierTypeId(supplierTypeId);
		Collection<Supplier> aColSupplier = this.supplierRepository.findByTypesContains(supplierType);
		Collection<SupplierDTO> aColSupplierDTO = new HashSet<SupplierDTO>();
		aColSupplier.forEach(supplier->{
			aColSupplierDTO.add(this.dtoFactory.createSupplierDTO(supplier));
		});
		return aColSupplierDTO;
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<ProductDTO> getAllProductsAndTypesForSupplier(Long supplierId) {
		Collection<ProductDTO> productsDTO = new ArrayList<ProductDTO>();
		Collection<Product> products = this.productRepository.getAllBySupplier(supplierId);
		products.forEach( p->{
			List<String> colNamesType = new ArrayList<String>();
			p.getTypes().forEach( t ->{
				colNamesType.add(t.getName());
			});
			productsDTO.add(this.dtoFactory.createProductDTO(p, colNamesType));
		});
		return productsDTO;	
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<SupplierOrderDTO> getDiezMayorOrderSent() {
		return this.supplierRepository.getDiezMayorOrderSent();
	}

	@Override
	@Transactional(readOnly = true)
	public Collection<SupplierProductsTypesDTO> getSuppliersWithProductsAllTypes() {
		Long cantProductsType = this.productTypeRepository.count();
		return this.supplierRepository.getSuppliersWithProductsAllTypes(cantProductsType);
	}
	
	@Override
	@Transactional(readOnly = true)
	public Collection<ProductTypePriceDTO> getPricesAvgForProducts() {
		return this.productRepository.getPricesAvgForProducts();
	}
	@Override
	@Transactional(readOnly = true)
	public Collection<SupplierQualificationDTO> getQualificationForSuppliers() {
		return this.supplierRepository.getQualificationForSuppliers();
		
	}


}

