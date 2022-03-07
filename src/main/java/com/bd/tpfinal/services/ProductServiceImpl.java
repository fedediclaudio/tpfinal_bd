package com.bd.tpfinal.services;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.repositories.implementations.ProductRepository;
import com.bd.tpfinal.repositories.implementations.ProductTypeRepository;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired ProductRepository productRepository;
	@Autowired ProductTypeRepository productTypeRepository;
	@Autowired SupplierRepository supplierRepository;
	
	@Transactional
	public Product saveProduct(Product product) throws Exception {
		return productRepository.save(product);
	}
	
	public Product createNewProduct(Product product) throws Exception {
		if ((product.getName().isBlank()) ||
			(product.getDescription().isBlank()) ||
			(product.getPrice() < 0) ||
			(product.getWeight() < 0)) return null;
		
		// Obtengo el Supplier de la BD
		Supplier supplier = supplierRepository.getSupplierById( product.getSupplier().getId() );
		// Si el supplier no existe, retorno null
		if (supplier == null) return null;
		
		// Obtengo el ProductType de la BD
		ProductType productType = productTypeRepository.getProductTypeById( product.getType().getId() );
		// Si el productType no existe, retorno null
		if (productType == null) return null;
		
		// Creo el nuevo Producto
		product.setSupplier( supplier );
		product.setType( productType );
		HistoricalProductPrice hp = new HistoricalProductPrice( product );
		product.addHistoricalPrice( hp );
		
		// Grabo el producto 
		product = productRepository.save( product );
		
		return product;
	}
	
	public boolean changeProductPrice(long idProduct, float newPrice) throws Exception {
		// Obtengo el Product de la BD
		Product product = productRepository.getProductById( idProduct );
		// Si el Product no existe, retorno false
		if (product == null) return false;
		
		List<HistoricalProductPrice> historical = product.getPrices();
		
		// Obtengo el ultimo precio y le configura la fecha de fin
		int lastIndex = historical.size() - 1;
		HistoricalProductPrice last = historical.get( lastIndex );
		last.setFinishDate( LocalDate.now() );
		historical.set( lastIndex, last );
		
		// Configuro el nuevo precio
		product.setPrice( newPrice );
		HistoricalProductPrice hp = new HistoricalProductPrice( product );
		product.addHistoricalPrice( hp );
		
		// Actualizo el producto
		productRepository.save( product );
		
		return true;
	}
	
	public List<Product> getProductList() throws Exception {
		return productRepository.findAll();
	}
	
	public List<HistoricalProductPrice> getHistoricalPricesFromProduct(long idProduct) throws Exception {
		return productRepository.getHistoricalPricesListOrderByStartDate( idProduct );
	}
}
