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
import com.bd.tpfinal.repositories.implementations.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.implementations.ProductRepository;
import com.bd.tpfinal.repositories.implementations.ProductTypeRepository;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;

@Service
public class ProductServiceImpl implements ProductService {
	@Autowired ProductRepository productRepository;
	@Autowired HistoricalProductPriceRepository historicalProductPriceRepository;
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
		
		// Corroboro que el Supplier haya sido proveido
		if (product.getSupplier() == null) {
			System.out.println("Supplier no provisto");
			return null;
		}

		// Corroboro que el ProductType haya sido proveido
		if (product.getType() == null) {
			System.out.println("ProductType no provisto");
			return null;
		}
		
		// Obtengo el Supplier de la BD
		Supplier supplier = supplierRepository.getSupplierById( product.getSupplier().getId() );
		// Si el supplier no existe, retorno null
		if (supplier == null) {
			System.out.println("El Supplier no existe");
			return null;
		}
		
		// Obtengo el ProductType de la BD
		ProductType productType = productTypeRepository.getProductTypeById( product.getType().getId() );
		// Si el productType no existe, retorno null
		if (productType == null) {
			System.out.println("El ProductType no existe");
			return null;
		}
		
		
		
		// Creo el nuevo Producto
		product.setSupplier( supplier );
		product.setType( productType );
		HistoricalProductPrice hp = new HistoricalProductPrice( product );
		product.addHistoricalPrice( hp );
		
		// Grabo el producto 
		product = productRepository.save( product );
		
		return product;
	}
	
	@Transactional
	public boolean updateProduct(Product product) throws Exception {
		if ((product.getId() < 0) ||
			(product.getName().isBlank()) ||
			(product.getDescription().isBlank()) ||
			(product.getPrice() < 0) ||
			(product.getWeight() < 0)) return false;

		// Corroboro que el producto exista
		Product dbProducto = productRepository.getProductById( product.getId() );
		if (dbProducto == null) {
			System.out.println("El producto no existe");
			return false;
		}
		if (dbProducto.isProductDeleted()) {
			System.out.println("El producto ya no esta a la disponible para la venta");
			return false;
		}
		
		// Actualizo el Producto
		dbProducto.setName( product.getName() );
		dbProducto.setPrice( product.getPrice() );
		dbProducto.setDescription( product.getDescription() );
		dbProducto.setWeight( product.getWeight() );
		
		// Grabo el producto 
		productRepository.save(dbProducto);
		
		return true;
	}
	
	@Transactional
	public boolean deleteProduct(long idProduct) throws Exception {
		// Obtengo el Product de la BD
		Product product = productRepository.getProductById( idProduct );
		// Si el Product no existe, retorno false
		if (product == null) {
			System.out.println("El Producto no existe");
			return false;
		}
		if (product.isProductDeleted()) {
			System.out.println("El producto ya no esta a la disponible para la venta");
			return false;
		}
		
		// Borro el producto
		product.setProductDeleted(true);
		productRepository.save(product);
		
		return true;
	}
	
	public boolean changeProductPrice(long idProduct, float newPrice) throws Exception {
		// Obtengo el Product de la BD
		Product product = productRepository.getProductById( idProduct );
		// Si el Product no existe, retorno false
		if (product == null) {
			System.out.println("El Producto no existe");
			return false;
		}
		if (product.isProductDeleted()) {
			System.out.println("El producto ya no esta a la disponible para la venta");
			return false;
		}
		
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
		return historicalProductPriceRepository.getHistoricalPricesListOrderByStartDate( idProduct );
	}
	
	public List<Product> getProductsFromSupplier(long idSupplier) throws Exception {
		return productRepository.getProductsFromSupplier(idSupplier);
	}
	
	public List<HistoricalProductPrice> getHistoricalPricesBetweenTwoDates(LocalDate dateFrom, LocalDate dateTo) throws Exception {
		return historicalProductPriceRepository.getHistoricalPricesBetweenTwoDates(dateFrom, dateTo);
	}
	
}
