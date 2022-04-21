package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.services.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

@SpringBootTest
class TpfinalApplicationTests {

	@Autowired
	private ProductTypeService productTypeService;
	@Autowired
	private SupplierTypeService supplierTypeService;
	@Autowired
	private ClientService clientService;
	@Autowired
	private DeliveryManService deliveryManService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private ProductService productService;
	@Autowired
	private HistoricalProductPriceService historicalProductPriceService;
	@Autowired
	private OrderService orderService;

//	private DeliveryService service;

	@Test
	void testCreacionProductType()
	{
		ProductType productType1 = new ProductType("ProductoTipo1", "descripción Producto Tipo 1");
		this.productTypeService.addProductType(productType1);

		ProductType productType2 = new ProductType("ProductoTipo2", "descripción Producto Tipo 2");
		this.productTypeService.addProductType(productType2);
	}

	@Test
	void testCreacionSupplierType()
	{
		SupplierType supplierType1 = new SupplierType("supplierType1", "Descripcion SupplierType1");
		this.supplierTypeService.addSupplierType(supplierType1);
	}

	@Test
	void testCreacionClient()
	{
		Client client1 = new Client("Cliente1", "usuarioCliente1", "passCliente1", "email@email.com", new Date());
		this.clientService.addClient(client1);
	}

	@Test
	void testCreacionDeliveryMan()
	{
		Date cumple = new Date();
		Date dia_admision = new Date();
		DeliveryMan deliveryMan1 = new DeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", cumple, true, dia_admision );
		this.deliveryManService.addDeliveryMan(deliveryMan1);
	}

	@Test
	void testCreacionSupplier()
	{
		float coords[] = new float[2];
		coords[0] = 1.0F;
		coords[1] = 2.0F;
		//por ahora no hay calificación
		float qual = 0.0F;
		SupplierType supplierType = this.supplierTypeService.getSupplierTypeByName("supplierType1");
		Supplier supplier1 = new Supplier("supplier1", "20123456784", "San Juan 123", coords, qual, supplierType);
	}

	@Test
	void testCreacionProduct()
	{
		ProductType productType = this.productTypeService.getProductTypeByName("ProductoTipo1");
		Supplier supplier = this.supplierService.getSupplierByName("supplier1");
		Product product1 = new Product("producto1", 25.8F, 12.0F, "descripcion producto 1", supplier, productType);
	}

	@Test
	void testHistoricalProductPrice()
	{
		Date startDate = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
		Date finishDate = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
		Product product = this.productService.getProductByName("producto1");
		HistoricalProductPrice hpp = new HistoricalProductPrice(123.00F,startDate, finishDate, product);
		this.historicalProductPriceService.addHistoricalProductPrice(hpp);
	}

	@Test
	void prueba() {
		System.out.println("OK!");
	}

}
