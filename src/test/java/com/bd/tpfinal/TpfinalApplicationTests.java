package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.services.*;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.*;

@SpringBootTest
class TpfinalApplicationTests
{

    @Autowired
    private ProductTypeService productTypeService;
    @Autowired
    private SupplierTypeService supplierTypeService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private AddressService addressService;
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
    @Autowired
	private ItemService itemService;

//	private DeliveryService service;



    @Test
    void test_PRODUCTTYPE_CreacionProductType()
    {
        ProductType productType1 = new ProductType("ProductoTipo1", "descripción Producto Tipo 1");
        this.productTypeService.newProductType(productType1);

        ProductType productType2 = new ProductType("ProductoTipo2", "descripción Producto Tipo 2");
        this.productTypeService.newProductType(productType2);
    }

    @Test
    void test_SUPPLIERTYPE_CreacionSupplierType()
    {
        SupplierType supplierType1 = new SupplierType("supplierType1", "Descripcion SupplierType1");
        this.supplierTypeService.newSupplierType(supplierType1);
    }

    @Test
    void test_CLIENT_CreacionClient()
    {
        //El cliente ignora la dirección(JsonIgnore sobre la lista de address)
        Client client1 = new Client("Cliente1", "usuarioCliente1", "passCliente1", "email@email.com", new Date());
        Address newAddress1 = new Address("Direccion de la casa", "Calle1 Altura1", "Apartment1", new float[]{1F, 2F}, "domicilio particular 1", client1);
        Address newAddress2 = new Address("Trabajo", "Calle2 Altura2", "Apartment2", new float[]{1.4F, 2.6F}, "domicilio laboral", client1);
        List<Address> addressList = new ArrayList<Address>();
        addressList.add(newAddress1);
        addressList.add(newAddress2);
        //Client client1 = new Client("Cliente1", "usuarioCliente1", "passCliente1", "email@email.com", new Date(), addressList);
        this.clientService.newClient(client1);
        this.addressService.newAddress(newAddress1);
        this.addressService.newAddress(newAddress2);
    }

    @Test
    void test_DELIVERYMAN_CreacionDeliveryMan()
    {
        newDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
		newDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());
	}

    public void newDeliveryMan(String name, String username, String password, String email, Date dateOfBirth, boolean free, Date dateOfAdmission )
	{
		DeliveryMan deliveryMan = new DeliveryMan(name, username,password,email, dateOfBirth, free, dateOfAdmission);
		this.deliveryManService.newDeliveryMan(deliveryMan);
	}



    @Test
    void test_SUPPLIER_CreacionSupplier()
    {
        float coords[] = new float[2];
        coords[0] = 1.0F;
        coords[1] = 2.0F;
        //por ahora no hay calificación
        float qual = 0.0F;
        SupplierType supplierType = this.supplierTypeService.getSupplierTypeByName("supplierType1");
        Supplier supplier1 = new Supplier("supplier1", "20123456784", "San Juan 123", coords, qual, supplierType);
        this.supplierService.newSupplier(supplier1);
    }

	/**
	 * test
	 * crea product Type, SupplierType
	 */

    void new_PRODUCT_CreacionProduct()
    {
        ProductType productType1 = new ProductType("ProductoTipo1", "descripción Producto Tipo 1");
        this.productTypeService.newProductType(productType1);

        ProductType productType2 = new ProductType("ProductoTipo2", "descripción Producto Tipo 2");
        this.productTypeService.newProductType(productType2);

        SupplierType supplierType1 = new SupplierType("supplierType1", "Descripcion SupplierType1");
        this.supplierTypeService.newSupplierType(supplierType1);

		float coords[] = new float[2];
		coords[0] = 1.0F;
		coords[1] = 2.0F;
		//por ahora no hay calificación
		float qual = 0.0F;
		SupplierType supplierType = this.supplierTypeService.getSupplierTypeByName("supplierType1");
		Supplier supplier1 = new Supplier("supplier1", "20123456784", "San Juan 123", coords, qual, supplierType);
		this.supplierService.newSupplier(supplier1);

        ProductType productType = this.productTypeService.getProductTypeByName("ProductoTipo1");
        List<Supplier> suppliers = this.supplierService.getSupplierByName("supplier1");
        Supplier unSupplier = suppliers.get(0);
        Product product1 = new Product("producto1", 25.8F, 12.0F, "descripcion producto 1", unSupplier, productType);
        this.productService.newProduct(product1);
        Product product2 = new Product("producto2", 25.8F, 12.0F, "descripcion producto 2", unSupplier, productType);
        this.productService.newProduct(product2);

		Date startDate = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
		Date finishDate = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
		Product product = this.productService.getProductByName("producto1");
		HistoricalProductPrice hpp = new HistoricalProductPrice(123.00F, startDate, finishDate, product);
		this.historicalProductPriceService.newHistoricalProductPrice(hpp);
    }

    @Test
	void test_PRODUCT_CreacionProduct()
	{
		new_PRODUCT_CreacionProduct();
	}

    /**
     * asigna
     */
    public void new_ORDER_CreationOrder()
    {
        Client client1 = new Client("Cliente1", "usuarioCliente1", "passCliente1", "email@email.com", new Date());
        Address newAddress1 = new Address("Direccion de la casa", "Calle1 Altura1", "Apartment1", new float[]{1F, 2F}, "domicilio particular 1", client1);
        Address newAddress2 = new Address("Trabajo", "Calle2 Altura2", "Apartment2", new float[]{1.4F, 2.6F}, "domicilio laboral", client1);
        List<Address> addressList = new ArrayList<Address>();
        addressList.add(newAddress1);
        addressList.add(newAddress2);
        client1.setAddresses(addressList);
        this.clientService.newClient(client1);
        Date dateOfOrder = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Client cliente = (Client) this.clientService.getClientByName("Cliente1");
        Address address = client1.getAddresses().get(0);
        Order orden = new Order(dateOfOrder, "comentario Orden 1", 0.0F, cliente, address);
        Order ordenbis = this.orderService.newOrder(orden);
        OrderStatus os = ordenbis.getOrderStatus();
        Long number = ordenbis.getNumber();
        ordenbis = this.orderService.getByNumber(number);
        System.out.println("-----------------order status name otra vez: " + ordenbis.getOrderStatus().getName());


    }

    @Test
	public void test_ORDER_CreationOrder()
	{
		new_ORDER_CreationOrder();
	}

	/**
	 * 1) agregar un ítem a una orden ya creada.
	 */
	@Test
    public void test_ORDER_agregar_Item_a_Order_Creada()
    {
        new_ORDER_CreationOrder();
        List<Order> ordenes = this.orderService.getAll();
        Order orden_buscada = ordenes.get(0);
        //orden_buscada.setStatusByName();
        new_PRODUCT_CreacionProduct();
		Product producto_buscado = this.productService.getProductByName("producto1");
        Item item = new Item(3, "descripcion item", orden_buscada, producto_buscado);
        this.itemService.newItem(item);
        Product producto_buscado_2 = this.productService.getProductByName("producto2");
        Item item2 = new Item(2, "descripción item 2", orden_buscada, producto_buscado_2);
        this.itemService.newItem(item2);
    }

    /**
     * 2) Confirmar un pedido. Esto implica buscar un repartidor libre y asignarle dicho pedido.
     */
    @Test
    public void test_Confirmar_Pedido()
    {
        //Creación de la Orden (pedido)
        //Similar a agregar un ítem a una orden ya creada.
        new_ORDER_CreationOrder();
        List<Order> ordenes = this.orderService.getAll();//esto establece status en el OrderServiceImpl
        Order orden_buscada = ordenes.get(0);
        //orden_buscada.setStatusByName();//se establece el objeto status
        new_PRODUCT_CreacionProduct();
        Product producto_buscado = this.productService.getProductByName("producto1");
        Item item = new Item(3, "descripcion item", orden_buscada, producto_buscado);
        this.itemService.newItem(item);

        //Alta de un DeliveryMan
        newDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        newDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());

        //ASIGNACION
        //1 se busca el primer repartidor libre y se asigna
        List<DeliveryMan> deliveryMEN = this.deliveryManService.getAllDeliveryManFree();
        DeliveryMan deliveryManFree = deliveryMEN.get(0);

        boolean rta=this.orderService.assignOrderToDeliveryMan(orden_buscada, deliveryManFree);
    }


    @Test
    void prueba()
    {
        System.out.println("OK!");
    }

}
