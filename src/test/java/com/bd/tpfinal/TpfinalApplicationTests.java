package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.services.*;
import com.bd.tpfinal.utils.NoExisteProductoException;
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
        ProductType pT1 = new ProductType("ProductoTipo1", "descripción Producto Tipo 1");
        this.productTypeService.newProductType(pT1);

        ProductType pT2 = new ProductType("ProductoTipo2", "descripción Producto Tipo 2");
        this.productTypeService.newProductType(pT2);

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
    }

    public void crear_productos()
    {
        ProductType pT1 = new ProductType("ProductoTipo1", "descripción Producto Tipo 1");
        this.productTypeService.newProductType(pT1);
        ProductType pT2 = new ProductType("ProductoTipo2", "descripción Producto Tipo 2");
        this.productTypeService.newProductType(pT2);
        ProductType pT3 = new ProductType("ProductoTipo3", "descripción Producto Tipo 3");
        this.productTypeService.newProductType(pT3);

        SupplierType sT1 = new SupplierType("sT1", "Descripcion sT1");
        this.supplierTypeService.newSupplierType(sT1);
        SupplierType sT2 = new SupplierType("sT2", "descripcion st2");
        this.supplierTypeService.newSupplierType(sT2);
        SupplierType sT3 = new SupplierType("sT3", "descripcion st3");
        this.supplierTypeService.newSupplierType(sT3);

        float coords[] = new float[2];
        coords[0] = 1.0F;
        coords[1] = 2.0F;
        //por ahora no hay calificación
        float qual = 0.0F;

        pT1 = this.productTypeService.getProductTypeByName("ProductoTipo1");
        pT2 = this.productTypeService.getProductTypeByName("ProductoTipo2");
        pT3 = this.productTypeService.getProductTypeByName("ProductoTipo3");

        sT1 = this.supplierTypeService.getSupplierTypeByName("sT1");
        sT2 = this.supplierTypeService.getSupplierTypeByName("sT2");
        sT3 = this.supplierTypeService.getSupplierTypeByName("sT3");

        Supplier s1 = new Supplier("supplier1", "20123456784", "San Juan 123", coords, qual, sT1);
        Supplier s2 = new Supplier("supplier2", "20123456784", "San Juan 123", coords, qual, sT1);
        Supplier s3 = new Supplier("supplier3", "20123456784", "San Juan 123", coords, qual, sT2);
        Supplier s4 = new Supplier("supplier4", "20123456784", "San Juan 123", coords, qual, sT2);
        Supplier s5 = new Supplier("supplier5", "20123456784", "San Juan 123", coords, qual, sT3);
        Supplier s6 = new Supplier("supplier6", "20123456784", "San Juan 123", coords, qual, sT3);

        this.supplierService.newSupplier(s1);
        this.supplierService.newSupplier(s2);
        this.supplierService.newSupplier(s3);
        this.supplierService.newSupplier(s4);
        this.supplierService.newSupplier(s5);
        this.supplierService.newSupplier(s6);

        List<Supplier> suppliers = this.supplierService.getAll();
        s1 = suppliers.get(0);
        s2 = suppliers.get(1);
        s3 = suppliers.get(2);

        Product p1 = new Product("producto1", 25.8F, 12.0F, "descripcion producto 1", s1, pT1);
        this.productService.newProduct(p1);
        Product p2 = new Product("producto2", 40.8F, 12.0F, "descripcion producto 2", s1, pT2);
        this.productService.newProduct(p2);
        Product p3 = new Product("producto3", 5.8F, 12.0F, "descripcion producto 3", s1, pT3);
        this.productService.newProduct(p3);

        this.productService.newProduct(new Product("producto4", 67.8F, 12.0F, "descripcion producto 4", s2, pT1));
        this.productService.newProduct(new Product("producto5", 1000.8F, 12.0F, "descripcion producto 5", s2, pT2));
        this.productService.newProduct(new Product("producto6", 130.8F, 12.0F, "descripcion producto 6", s2, pT3));

        this.productService.newProduct(new Product("producto7", 18.8F, 12.0F, "descripcion producto 7", s3, pT1));
        this.productService.newProduct(new Product("producto8", 3.8F, 12.0F, "descripcion producto 8", s3, pT2));
        this.productService.newProduct(new Product("producto9", 414.8F, 12.0F, "descripcion producto 9", s3, pT3));

    }

    public Date crear_ordenes()
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

        Order o1 = this.orderService.newOrder(new Order(dateOfOrder, "comentario Orden 1", 0.0F, cliente, address));
        Order o2 = this.orderService.newOrder(new Order(dateOfOrder, "comentario Orden 2", 0.0F, cliente, address));
        Order o3 = this.orderService.newOrder(new Order(dateOfOrder, "comentario Orden 3", 0.0F, cliente, address));
        Order o4 = this.orderService.newOrder(new Order(dateOfOrder, "comentario Orden 4", 0.0F, cliente, address));
        Order o5 = this.orderService.newOrder(new Order(dateOfOrder, "comentario Orden 5", 0.0F, cliente, address));

        List<Order> ordenes = this.orderService.getAll();
        List<Product> productos = this.productService.getAll();
        agregar_Items(ordenes.get(0), productos.get(0));
        agregar_Items(ordenes.get(0), productos.get(1));
        agregar_Items(ordenes.get(0), productos.get(2));
        agregar_Items(ordenes.get(0), productos.get(3));

        agregar_Items(ordenes.get(1), productos.get(0));
        //agregar_Items(ordenes.get(1), productos.get(1));
        agregar_Items(ordenes.get(1), productos.get(4));
        agregar_Items(ordenes.get(1), productos.get(5));

        agregar_Items(ordenes.get(2), productos.get(0));
        agregar_Items(ordenes.get(2), productos.get(3));
        agregar_Items(ordenes.get(2), productos.get(6));
        agregar_Items(ordenes.get(2), productos.get(7));

        agregar_Items(ordenes.get(3), productos.get(0));
        agregar_Items(ordenes.get(3), productos.get(8));
        agregar_Items(ordenes.get(3), productos.get(7));
        agregar_Items(ordenes.get(3), productos.get(6));
        agregar_Items(ordenes.get(3), productos.get(5));

        agregar_Items(ordenes.get(4), productos.get(8));

        return dateOfOrder;
    }

    public void agregar_Items(Order o, Product p)
    {
        Item item = new Item(3, "descripcion item", o, p);

        this.itemService.newItem(item);
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
        catch (Exception e)
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
        catch (Exception e)
        {
            System.out.println("No sería Assigned");
        }
    }

    /**
     * 3) Añadir una calificación a una orden ya completada.
     * Tenga en cuenta que deberá actualizar la calificación del proveedor.
     * ver esto: https://docs.jboss.org/hibernate/orm/3.5/reference/es-ES/html/queryhql.html
     */
    //TODO: agregar varias ordenes con diferentes items y diferentes calificaciones, para probar el calculo del promedio
    //TODO: COMPLETAR
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
        // ahora se le agrega la Qualification a todas las ordenes del Client 1

        Iterator<Order> ordenes = this.orderService.getByClientId(1L).iterator();
        while (ordenes.hasNext())
        {
            int i = 0;
            float score = 1.00F;
            String comentario = "comentario_" + i;
            Order orden = (Order) ordenes.next();
            orden.setStatusByName();//tal vez no sea necesario para calificar
            Qualification qua = new Qualification(score, comentario, orden);
            orden.setQualification(qua);
            this.orderService.actualizarOrder(orden);
            System.out.println("cuenta  " + i);
            i = i + 1;
            score = score + 1.00F;
        }

        Order orden_a_calificar = this.orderService.getByNumber(number_de_ultima_orden);
        //Qualification qua = new Qualification(4.0F, "comentario1", orden_a_calificar);
        //orden_a_calificar.setQualification(qua);
        //this.orderService.actualizarOrder(orden_a_calificar);


        //Calificación de los proveedores, un proveedor por cada Item.
        //calificación, la cual será el promedio entre las calificaciones recibidas por los clientes
        //Para cada proveedor, encontrar todas las ordenes que contienen sus productos, y buscar la calificacion de cada una.
        //el promedio será la calificacion del proveedor.
        //Tengo en cuenta solamente la calificación a cada Supplier que está presente en la última Order
        Iterator<Item> items_iter = orden_a_calificar.getItems().iterator();
        HashMap<Long, Supplier> suppliers = new HashMap<Long, Supplier>();

        while (items_iter.hasNext())
        {
            Supplier proveedor = items_iter.next().getProduct().getSupplier();
            suppliers.putIfAbsent(proveedor.getId(), proveedor);

        }
        Iterator<Supplier> iter_supplier = suppliers.values().iterator();
        while (iter_supplier.hasNext())
        {
            Supplier supplier = iter_supplier.next();
            Iterator<Order> ordenes2 = this.orderService.getOrderByIdSupplier(supplier.getId()).iterator();
            while (ordenes2.hasNext())
            {
                System.out.println("numero de orden:  " + ordenes2.next().getNumber());
            }


            double promedio = this.orderService.getQualificationAverage(supplier.getId());
            System.out.println("promedio calificacion de ordenes del supplier: " + promedio);

        }

    }

    //4) Actualizar los datos de un producto. Tenga en cuenta que puede cambiar su precio.
    @Test
    void actualizar_datos_producto()
    {
        //Creo productos y luego los actualizo.
        ProductType productType1 = new ProductType("ProductoTipo1", "descripción Producto Tipo 1");
        this.productTypeService.newProductType(productType1);

        ProductType productType2 = new ProductType("ProductoTipo2", "descripción Producto Tipo 2");
        this.productTypeService.newProductType(productType2);

        SupplierType supplierType1 = new SupplierType("supplierType1", "Descripcion SupplierType1");
        this.supplierTypeService.newSupplierType(supplierType1);

        SupplierType supplierType2 = new SupplierType("supplierType2", "Descripcion SupplierType1");
        this.supplierTypeService.newSupplierType(supplierType2);


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
        Product product2 = new Product("producto2", 30.8F, 8.0F, "descripcion producto 2", unSupplier, productType);
        this.productService.newProduct(product2);

        // cambiar datos. Cambio el precio y la descripción
        Long id_producto_a_cambiar = product1.getId();
        float nuevoPrecio = 54.0F;
        String nuevaDescripcion = "nueva descripción producto 1";
        Product updatedProduct = new Product("producto1", nuevoPrecio, 12.0F, nuevaDescripcion, unSupplier, productType);

        try
        {
            this.productService.updateData(id_producto_a_cambiar, updatedProduct);
        }
        catch (NoExisteProductoException e)
        {
            e.printStackTrace();
        }
    }

    //5) Eliminar un producto de los ofrecidos por un proveedor
    @Test
    void test_eliminar_producto()
    {
        //Creo 2 productos
        ProductType productType1 = new ProductType("ProductoTipo1", "descripción Producto Tipo 1");
        this.productTypeService.newProductType(productType1);

        ProductType productType2 = new ProductType("ProductoTipo2", "descripción Producto Tipo 2");
        this.productTypeService.newProductType(productType2);

        SupplierType supplierType1 = new SupplierType("supplierType1", "Descripcion SupplierType1");
        this.supplierTypeService.newSupplierType(supplierType1);

        SupplierType supplierType2 = new SupplierType("supplierType2", "Descripcion SupplierType1");
        this.supplierTypeService.newSupplierType(supplierType2);


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
        Product product2 = new Product("producto2", 30.8F, 8.0F, "descripcion producto 2", unSupplier, productType);
        this.productService.newProduct(product2);

        //eliminar un producto ofrecido por un proveedor
        try
        {
            this.productService.eliminarProductoById(product2.getId());
        }
        catch (NoExisteProductoException n)
        {

        }
        Product p = this.productService.getProductById(product2.getId());
        if (p == null)
        {
            System.out.println("p :" + p);
        }
    }

    //6) Obtener todos los proveedores de un cierto tipo.
    @Test
    void test_obtener_proveedores_de_un_tipo()
    {
        SupplierType supplierType1 = new SupplierType("supplierType1", "Descripcion SupplierType1");
        this.supplierTypeService.newSupplierType(supplierType1);

        SupplierType supplierType2 = new SupplierType("supplierType2", "Descripcion SupplierType1");
        this.supplierTypeService.newSupplierType(supplierType2);

        float coords[] = new float[2];
        coords[0] = 1.0F;
        coords[1] = 2.0F;
        //por ahora no hay calificación
        float qual = 0.0F;
        SupplierType supplierType = this.supplierTypeService.getSupplierTypeByName("supplierType1");
        Supplier supplier1 = new Supplier("supplier1", "20123456784", "San Juan 123", coords, qual, supplierType);
        this.supplierService.newSupplier(supplier1);
        coords[0] = 4.0F;
        coords[1] = 5.0F;
        Supplier supplier2 = new Supplier("supplier2", "55123456784", "Luro 123", coords, qual, supplierType);
        this.supplierService.newSupplier(supplier2);

        Long id = supplierType.getId();
        Iterator<Supplier> suppliers = this.supplierService.getSupplierBySupplierTypeId(id).iterator();
        while (suppliers.hasNext())
        {
            Supplier ste = (Supplier) suppliers.next();
            System.out.println("supplier: " + ste.getName() + " id: " + ste.getId());
        }
    }

    //7) Obtener todos los productos y su tipo, de un proveedor específico.
    //TODO: tiene errores, corregir
    @Test
    void test_obtener_productos_por_proveedor()
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
        productType2 = this.productTypeService.getProductTypeByName("ProductoTipo2");

        List<Supplier> suppliers = this.supplierService.getSupplierByName("supplier1");
        Supplier unSupplier = suppliers.get(0);
        Product product1 = new Product("producto1", 25.8F, 12.0F, "descripcion producto 1", unSupplier, productType);
        this.productService.newProduct(product1);
        Product product2 = new Product("producto2", 35.8F, 12.0F, "descripcion producto 2", unSupplier, productType);
        this.productService.newProduct(product2);
        Product product3 = new Product("producto3", 15.8F, 12.0F, "descripcion producto 3", unSupplier, productType2);
        this.productService.newProduct(product3);

        Iterator<Product> productos = this.productService.getProductoByProductType(productType.getId()).iterator();
        while (productos.hasNext())
        {
            Product producto = productos.next();
            System.out.println("Producto id : " + producto.getId() + " Proveedor:  " + producto.getSupplier().getName() + "  " + producto.getType().getName());
        }
    }


    //8) Obtener las órdenes con más productos de un proveedor específico.
    //modularización
    //
    // primero obtengo las ordenes con con productos de un supplier
    @Test
    void test_ordenes_de_Suppier()
    {
        crear_productos();
        crear_ordenes();
        List<Supplier> suppliers = this.supplierService.getAll();
        Iterator<Supplier> iter_supplier = suppliers.iterator();
        System.out.println("cantidad de suppliers: "+suppliers.size());
        //while (iter_supplier.hasNext())
        //{
            Supplier supplier = (Supplier)iter_supplier.next();
            System.out.println("SUPPLIER ID: "+ supplier.getId());
            Iterator<Order> iter_ordenes = this.orderService.getBySupplierMaxCantItems(supplier.getId()).iterator();
            while (iter_ordenes.hasNext())
            {
                Order orden_aux = (Order) iter_ordenes.next();
                System.out.println("ORDEN: " + orden_aux.getNumber());
                Iterator<Item> items = orden_aux.getItems().iterator();
                while (items.hasNext())
                {
                    Item item = (Item) items.next();
                    System.out.println("ITEM: " + item.getId() + " Product id:" + item.getProduct().getId() + " Supplier id: " + item.getProduct().getSupplier().getId());
                }
                System.out.println("-----------");
            }
        //}
    }

    //9) Obtener la orden de mayor precio total de un día dado.
    //TODO: para que esto funcione debe estar la bd vacía. COrregir
    @Test
    void test_orden_mayor_precio_x_fecha()
    {
        crear_productos();
        Date fecha = crear_ordenes();
        //Date fecha = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Iterator<Order> ordenes = this.orderService.getOrderMaxPricePorFecha(fecha).iterator();
        while(ordenes.hasNext())
        {
            Order orden = ordenes.next();
            System.out.println("Orden id: "+orden.getNumber()+"  Orden precio:" + orden.getTotalPrice());
        }
    }

    //10) Obtener los diez repartidores con mayor puntaje.

    //11) Obtener los diez proveedores que más órdenes despacharon.
    //12) Obtener los precios de un producto entre dos fechas dadas.
    //13) Obtener el precio promedio de los productos de cada tipo, para todos los tipos.
    //14) Obtener la información de los proveedores que tengan al menos una calificación de una estrella (la más baja). Es necesario también el número de estas calificaciones que el proveedor posee.
    //15) Obtener los proveedores que ofrezcan productos de todos los tipos.


    @Test
    void prueba()
    {
        System.out.println("OK!");
    }

}
