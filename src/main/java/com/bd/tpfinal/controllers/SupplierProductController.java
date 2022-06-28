package com.bd.tpfinal.controllers;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import com.bd.tpfinal.services.SupplierProductService;

/**
 * @author Sandra Zocchi
 *
 */
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@Validated
public class SupplierProductController {
	
	@Autowired
	private SupplierProductService supplierService;
	
		
	/* endpoints product */
		
	/**Crea un product para un proveedor
	 * @param supplierId
	 * @param product
	 * @return un productDTO
	 */
	@PostMapping ("/suppliers/{id}/product")
    public ProductDTO createProduct(@PathVariable(value = "id") Long supplierId, @RequestBody Product product){		
		return this.supplierService.createProduct(supplierId, product);
	}
	
	/** Recupera un product de un proveedor específico por medio de su nombre.
	 * @param productId
	 * @return un product
	 */
	@GetMapping("/suppliers/{id}/product/{name}")
	public Product readProduct(@PathVariable(value = "id") Long supplierId, @PathVariable(value = "name") String nameProducto){
		return this.supplierService.readProductId(supplierId, nameProducto);				
	}
	
	/**Actualiza un product de un proveedor específico.
	 * @param productDetails
	 * @param productId
	 * @return un product
	 */
	@PutMapping ("/suppliers/{id}/product/{name}")
	public Product updateProduct(@RequestBody Product productDetails, @PathVariable(value = "id") Long supplierId,@PathVariable(value = "name") String nameProducto){
		 return this.supplierService.updateProduct(supplierId, nameProducto, productDetails);
	}
	
	/** Elimina un product de un proveedor específico
	 * @param supplierId
	 * @param nameProducto
	 * @return el product eliminado
	 */
	@DeleteMapping("/suppliers/{id}/product/{name}")
	public void deleteProduct(@PathVariable(value = "id") Long supplierId, @PathVariable(value = "name") String nameProducto){
		 this.supplierService.deleteProductID(supplierId, nameProducto);	
	}
	
	/**Recupero los precios de un producto entre dos fechas dadas.
	 * @param dateFrom
	 * @param dateTo
	 * @param supplierId
	 * @param nameProducto
	 * @return
	 */
	@GetMapping("suppliers/{id}/product/{name}/pricebetweendates")
	public Collection<Float> getPricesBetweenDates(@RequestParam (name = "dateFrom") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom, 
			@RequestParam (name = "dateTo") @DateTimeFormat (iso = DateTimeFormat.ISO.DATE) LocalDate dateTo,
			@PathVariable(value = "id") Long supplierId, @PathVariable(value = "name") String nameProducto ) {
		return this.supplierService.getPricesBetweenDates(supplierId, nameProducto, dateFrom, dateTo);		
	}
	
	/* endpoints product type */
	
	/** Crea un product type
	 * @param productType
	 * @return productType
	 */
	@PostMapping ("/productstype")
    public ProductType createProductType(@RequestBody ProductType productType){				
		return this.supplierService.createProductType(productType);
	}	
	
	/** Recupera un productType por medio de su id
	 * @param productTypeId
	 * @return un productType o una excepción
	 */
	@GetMapping("/productstype/{id}")
	public ProductType readProductType(@PathVariable(value = "id") Long productTypeId){
		return this.supplierService.readProductTypeId(productTypeId);		
	}
	
		
	/* endpoints supplier */
	
	/**Crea un supplier
	 * @param supplier
	 * @return un supplier
	 */
	@PostMapping ("/suppliers")
    public Supplier createSupplier(@RequestBody Supplier supplier){		
		return this.supplierService.createSupplier(supplier);
	}
	
	/**Recupera un supplier por medio de su id
	 * @param supplierId
	 * @return un supplier o una excepción
	 */
	@GetMapping("/suppliers/{id}")
	public Supplier readSupplier(@PathVariable(value = "id") Long supplierId){
		return this.supplierService.readSupplierId(supplierId);		
	}
	
	/**Agrega a un supplier un supplierType
	 * @param supplierId
	 * @param supplierTypeId
	 * @return supplier
	 */
	@PostMapping ("/suppliers/{id}/supplierType/{idST}")
	 public Supplier addSupplierType(@PathVariable(value = "id") Long supplierId, @PathVariable(value = "idST") Long supplierTypeId){		
		return this.supplierService.addSupplierType(supplierId, supplierTypeId);
	}
	
	
	/* endpoints supplier type */
	
	/**Crea un supplierstype
	 * @param supplierType
	 * @return supplierType
	 */
	@PostMapping ("/supplierstype")
    public SupplierType createSupplierType(@RequestBody SupplierType supplierType){		
		return this.supplierService.createSupplierType(supplierType);
	}
	
	/**Recupera un supplierType por medio de su id
	 * @param supplierTypeId
	 * @return un supplierType o una excepción
	 */
	@GetMapping("/supplierstype/{id}")
	public SupplierType readSupplierType(@PathVariable(value = "id") Long supplierTypeId){
		return this.supplierService.readSupplierTypeId(supplierTypeId);		
	}
	
	/* endpoints product, product type, supplier, supplier type */ 
	

	/**Recupera la orden con más productos para un proveedor específico
	 * @param supplierId
	 * @return la orden
	 */
	@GetMapping("suppliers/{id}/masproducts")
	public Collection<OrderItemsDTO> getOrderMasProductsForSupplier(@PathVariable(value = "id") Long supplierId)throws Exception{
			return this.supplierService.getOrderMasProductsForSupplier(supplierId);
	}
		
	/**Recupera todos los proveedores para un cierto tipo de proveedor
	 * @param supplierTypeId
	 * @return una Collección 
	 */
	@GetMapping("/supplierstype/{id}/allsuppliers")
	public Collection<SupplierDTO> getAllSuppliersForSupplierType(@PathVariable(value = "id") Long supplierTypeId){
		return this.supplierService.getAllSuppliersForSupplierType(supplierTypeId);
	}	

	/**Recupera todos los productos y su tipo, para un proveedor específico
	 * @param supplierId
	 * @return una Collección
	 */
	@GetMapping("suppliers/{id}/allproductsandtypes")
	public Collection<ProductDTO> getAllProductsAndTypeForSupplier(@PathVariable(value = "id") Long supplierId){
		return this.supplierService.getAllProductsAndTypesForSupplier(supplierId);
	}
	
	/**Recupero los diez proveedores que más órdenes despacharon/enviaron
	 * @return una Collección
	 */
	@GetMapping("/suppliers/masordenesenviadas")
	public Collection<SupplierOrderDTO> getDiezMayorOrderSent() {
		return this.supplierService.getDiezMayorOrderSent();		
	}
	
	/**Recupera todos los proveedores que ofrecen productos de todos los tipos
	 * @return
	 */
	@GetMapping("suppliers/allproductsandtypes")
	public Collection<SupplierProductsTypesDTO> getSuppliersWithProductsAllTypes(){
		return this.supplierService.getSuppliersWithProductsAllTypes();
	}
	
	/**Recupero para cada tipo de producto el precio promedio de sus productos 
	 * @return
	 */
	@GetMapping("/productstype/pricesavgforproducts")
	public Collection<ProductTypePriceDTO> getPricesAvgForProducts(){
		return this.supplierService.getPricesAvgForProducts();		
	}
	
	/**Recupero los proveedores que tienen al menos una calificación de una estrella. 
	 * Junto con el nro. de calificaciones del proveedor.
	 * @return
	 */
	@GetMapping("/suppliers/almenosunaestrella")
	public Collection<SupplierQualificationDTO> getQualificationForSuppliers(){
		return this.supplierService.getQualificationForSuppliers();		
	}
	
	
}
