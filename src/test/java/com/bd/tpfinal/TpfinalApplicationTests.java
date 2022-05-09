package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.services.*;
import org.junit.jupiter.api.Test;
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
        new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());
    }

    public void new_DELIVERYMAN_CreacionDeliveryMan(String name, String username, String password, String email, Date dateOfBirth, boolean free, Date dateOfAdmission)
    {
        DeliveryMan deliveryMan = new DeliveryMan(name, username, password, email, dateOfBirth, free, dateOfAdmission);
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
     * Creación de Cliente y Orden
     * La Order queda en Pending
     */
    public Long new_ORDER_CreationOrder()
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
        return number;

    }

    @Test
    public void test_ORDER_CreationOrder()
    {
        new_ORDER_CreationOrder();
    }

    public Long new_ORDER_agregar_Item_a_Order_Creada()
    {
        Long number = new_ORDER_CreationOrder();//esto crea bien el orderStatus
        Order orden_buscada = this.orderService.getByNumber(number);
        new_PRODUCT_CreacionProduct();
        Product producto_buscado = this.productService.getProductByName("producto1");
        Item item = new Item(3, "descripcion item", orden_buscada, producto_buscado);
        this.itemService.newItem(item);
        Product producto_buscado_2 = this.productService.getProductByName("producto2");
        Item item2 = new Item(2, "descripción item 2", orden_buscada, producto_buscado_2);
        this.itemService.newItem(item2);
        return number;
    }

    /**
     * 1) agregar un ítem a una orden ya creada.
     */
    @Test
    public void test_ORDER_agregar_Item_a_Order_Creada()
    {
        new_ORDER_agregar_Item_a_Order_Creada();
    }

    /**
     * 2) Confirmar un pedido. Esto implica buscar un repartidor libre y asignarle dicho pedido.
     * El estado queda en Assigned
     */
    @Test
    public void test_Confirmar_Pedido()
    {
        Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();

        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        new_DELIVERYMAN_CreacionDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());

        //ASIGNACION
        //1 se busca el primer repartidor libre
        List<DeliveryMan> deliveryMEN = this.deliveryManService.getAllDeliveryManFree();
        DeliveryMan deliveryManFree = deliveryMEN.get(0);
        //asigna el primer repartidor libre encontrado a la orden recien creada.
        Order orden_buscada = this.orderService.getByNumber(number_de_ultima_orden);
        //orden_buscada.assignDeliveryMan(deliveryManFree);
        //this.orderService.actualizarOrder(orden_buscada);
        this.orderService.assignOrderToDeliveryMan(orden_buscada.getNumber(), deliveryManFree.getId());
    }

    /**
     * DeliveryMan rechaza una Orden (ya está Assigned)
     * La Order pasa a Cancelled
     * El repartidor descuenta puntaje
     */
    @Test
    public void test_DeliveryMan_Rechaza_Order()
    {
        Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        new_DELIVERYMAN_CreacionDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());
        //1 se busca un repartidor libre
        List<DeliveryMan> deliveryMEN = this.deliveryManService.getAllDeliveryManFree();
        DeliveryMan deliveryManFree = deliveryMEN.get(0);
        //asigna el primer repartidor libre encontrado a la orden recien creada.
        Order orden_buscada = this.orderService.getByNumber(number_de_ultima_orden);
        orden_buscada.assignDeliveryMan(deliveryManFree);
        this.orderService.actualizarOrder(orden_buscada);
        //la orden_buscada está en Assigned.
        //el deliveryManFree ya no esta free.
        try
        {
            orden_buscada.refuse();
            this.orderService.actualizarOrder(orden_buscada);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No sería una Order Assigned");
        }
    }

    /**
     * DeliveryMan acepta Order (estaba en Assigned)
     * La Order pasa a Sent
     */
    @Test
    public void test_DeliveryMan_acepta_Order()
    {
        Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        new_DELIVERYMAN_CreacionDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());

        //1 se busca el primer repartidor libre
        List<DeliveryMan> deliveryMEN = this.deliveryManService.getAllDeliveryManFree();
        DeliveryMan deliveryManFree = deliveryMEN.get(0);
        //asigna el primer repartidor libre encontrado a la orden recien creada.
        Order orden_buscada = this.orderService.getByNumber(number_de_ultima_orden);
        orden_buscada.assignDeliveryMan(deliveryManFree);
        this.orderService.actualizarOrder(orden_buscada);
        //la orden_buscada está en Assigned.
        //el deliveryManFree ya no esta free.
        try
        {
            orden_buscada.deliver(); //la orden pasa a SENT
            this.orderService.actualizarOrder(orden_buscada);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No sería una Order Assigned");
        }
    }

    /**
     * DeliveryMan entrega pedido (Order estaba en Sent)
     * La Order pasa a Finished
     * Se actualizan los puntajes
     */
    @Test
    public void test_DeliveryMan_Finishe_Pedido()
    {
        //crea una orden y progresa hasta la aceptación
        Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        new_DELIVERYMAN_CreacionDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());
        //1 se busca el primer repartidor libre
        List<DeliveryMan> deliveryMEN = this.deliveryManService.getAllDeliveryManFree();
        DeliveryMan deliveryManFree = deliveryMEN.get(0);
        //asigna el primer repartidor libre encontrado a la orden recien creada.
        Order orden_buscada = this.orderService.getByNumber(number_de_ultima_orden);
        orden_buscada.assignDeliveryMan(deliveryManFree);
        this.orderService.actualizarOrder(orden_buscada);
        //la orden_buscada está en Assigned.
        //el deliveryManFree ya no esta free.
        try
        {
            orden_buscada.deliver();//la Order pasa a Sent
            this.orderService.actualizarOrder(orden_buscada);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No sería una Order Assigned");
        }

        //ahora viene la parte de finalizar un pedido
        Order orden_sent = this.orderService.getByNumber(number_de_ultima_orden);
        try
        {
            orden_sent.finish();// la Order para a Delivered
            this.orderService.actualizarOrder(orden_sent);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No sería una Order Sent");
        }
    }

    /**
     * Cliente cancela Order (Order en Pending o Assigned)
     * La Order pasa a Cancelled
     */
    @Test
    public void test_Cliente_cancela_orden_Pending()
    {
        //se crea una Order con Items.
        //El estado es Pending
        Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();
        try
        {
            this.orderService.cancelarOrder(number_de_ultima_orden);
        }
        catch(Exception e)
        {
            System.out.println("No sería Pending");
        }
    }

    @Test
    public void test_Cliente_cancela_orden_Assigned()
    {
        Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();

        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        new_DELIVERYMAN_CreacionDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());

        //ASIGNACION
        //1 se busca el primer repartidor libre
        List<DeliveryMan> deliveryMEN = this.deliveryManService.getAllDeliveryManFree();
        DeliveryMan deliveryManFree = deliveryMEN.get(0);
        //asigna el primer repartidor libre encontrado a la orden recien creada.
        Order orden_buscada = this.orderService.getByNumber(number_de_ultima_orden);
        //orden_buscada.assignDeliveryMan(deliveryManFree);
        //this.orderService.actualizarOrder(orden_buscada);
        this.orderService.assignOrderToDeliveryMan(orden_buscada.getNumber(), deliveryManFree.getId());
        //Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();
        try
        {
            this.orderService.cancelarOrder(number_de_ultima_orden);
        }
        catch(Exception e)
        {
            System.out.println("No sería Assigned");
        }
    }

    /**
     * 3) Añadir una calificación a una orden ya completada.
     * Tenga en cuenta que deberá actualizar la calificación del proveedor.
     * ver esto: https://docs.jboss.org/hibernate/orm/3.5/reference/es-ES/html/queryhql.html
     */
    @Test
    public void test_anadirCalificacion_a_Orden_completada()
    {
        // SE CREA UNA ORDEN Y SE SIGUE EL PROCESO HASTA FINALIZAR LA ENTREGA POR PARTE DEL
        // DELIVERYMAN
        //crea una orden y progresa hasta la aceptación
        Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        new_DELIVERYMAN_CreacionDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());
        //1 se busca el primer repartidor libre
        List<DeliveryMan> deliveryMEN = this.deliveryManService.getAllDeliveryManFree();
        DeliveryMan deliveryManFree = deliveryMEN.get(0);
        //asigna el primer repartidor libre encontrado a la orden recien creada.
        Order orden_buscada = this.orderService.getByNumber(number_de_ultima_orden);
        orden_buscada.assignDeliveryMan(deliveryManFree);
        this.orderService.actualizarOrder(orden_buscada);
        //la orden_buscada está en Assigned.
        //el deliveryManFree ya no esta free.
        try
        {
            orden_buscada.deliver();//la Order pasa a Sent
            this.orderService.actualizarOrder(orden_buscada);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No sería una Order Assigned");
        }

        //ahora viene la parte de finalizar un pedido
        Order orden_sent = this.orderService.getByNumber(number_de_ultima_orden);
        try
        {
            orden_sent.finish();// la Order para a Delivered
            this.orderService.actualizarOrder(orden_sent);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.out.println("No sería una Order Sent");
        }
        // HASTA ACÁ CREACIÓN DE ORDEN Y ENTREGA
        // ahora se le agrega la Qualification a la orden
        Order orden_a_calificar = this.orderService.getByNumber(number_de_ultima_orden);
        Qualification qua = new Qualification(4.0F, "comentario1",orden_a_calificar );
        orden_a_calificar.setQualification(qua);
        this.orderService.actualizarOrder(orden_a_calificar);
        //Calificación de los proveedores, un proveedor por cada Item.
        //calificación, la cual será el promedio entre las calificaciones recibidas por los clientes
        //Para cada proveedor, encontrar todas las ordenes que contienen sus productos, y buscar la calificacion de cada una.
        //el promedio será la calificacion del proveedor.
        Iterator<Item> items_iter = orden_a_calificar.getItems().iterator();
        HashMap<Long ,Supplier> suppliers = new HashMap<Long, Supplier>();

        while(items_iter.hasNext())
        {
            while (items_iter.hasNext())
            {
                Supplier proveedor = items_iter.next().getProduct().getSupplier();
                suppliers.putIfAbsent(proveedor.getId(), proveedor);

            }
            Iterator<Supplier> iter_supplier = suppliers.values().iterator();
            while(iter_supplier.hasNext())
            {
                Supplier supplier = iter_supplier.next();
                Iterator<Order> ordenes = this.orderService.getOrderByIdSupplier(supplier.getId()).iterator();
                while (ordenes.hasNext())
                {
                    System.out.println("numero de orden:  " + ordenes.next().getNumber());
                }

            }
        }
    }

    @Test
    void prueba()
    {
        System.out.println("OK!");
    }

}
