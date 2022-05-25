package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.services.*;
import com.bd.tpfinal.utils.NoExisteProductoException;
import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
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
        new_PRODUCTTYPE_crear(6);
    }

    public void new_PRODUCTTYPE_crear(int cant)
    {
        int i = 0;
        for (i = 0; i < cant; i++)
        {
            ProductType productType = new ProductType("ProductoTipo" + i, "descripción Type" + i);
            this.productTypeService.newProductType(productType);
        }
    }


    @Test
    void test_CLIENT_CreacionClient()
    {
        new_CLIENT_crear(5);
    }

    public void new_CLIENT_crear(int cant)
    {
        //El cliente ignora la dirección(JsonIgnore sobre la lista de address)
        int i = 0;
        for (i = 0; i < cant; i++)
        {
            Client client1 = new Client("Cliente" + i, "usuarioNameCliente" + i, "passCliente" + i, "email@email.com" + i, new Date());
            Address newAddress1 = new Address("Casa" + i, "Calle_Altura " + i, "Apartment", new float[]{1F, 2F}, "domicilio particular " + i, client1);
            Address newAddress2 = new Address("Trabajo" + i, "Calle2 Altura2 " + i, "Apartment 2 " + i, new float[]{1.4F, 2.6F}, "domicilio laboral" + i, client1);
            List<Address> addressList = new ArrayList<Address>();
            addressList.add(newAddress1);
            addressList.add(newAddress2);
            //Client client1 = new Client("Cliente1", "usuarioCliente1", "passCliente1", "email@email.com", new Date(), addressList);
            this.clientService.newClient(client1);
            this.addressService.newAddress(newAddress1);
            this.addressService.newAddress(newAddress2);
        }

    }

    @Test
    void test_DELIVERYMAN_CreacionDeliveryMan()
    {
        new_DELIVERYMAN_CreacionDeliveryMan(6, new Date(), true, new Date());
    }

    public void new_DELIVERYMAN_CreacionDeliveryMan(int cant, Date dateOfBirth, boolean free, Date dateOfAdmission)
    {
        int i = 0;
        for (i = 0; i < cant; i++)
        {
            DeliveryMan deliveryMan = new DeliveryMan("DM_name_" + i, "usuario" + i, "pass" + i, "email" + i, dateOfBirth, free, dateOfAdmission);
            this.deliveryManService.newDeliveryMan(deliveryMan);
        }
    }

    @Test
    void test_SUPPLIERTYPE_CreacionSupplierType()
    {
        new_crear_SupplierType(6);
    }

    public void new_crear_SupplierType(int cant)
    {
        int i = 0;
        for (i = 0; i < cant; i++)
        {
            SupplierType supplierType = new SupplierType("supplierType" + i, "Descripcion SupplierType" + i);
            this.supplierTypeService.newSupplierType(supplierType);
        }
    }

    @Test
    void test_SUPPLIER_CreacionSupplier()
    {
        new_SUPPLIER_crear(5);
    }

    public void new_SUPPLIER_crear(int cant)
    {
        new_crear_SupplierType(3);
        List<SupplierType> supplierTypes = this.supplierTypeService.getAll();
        int cant_supplierTypes = supplierTypes.size();
        int i = 0;
        for (i = 0; i < cant; i++)
        {
            float coords[] = new float[2];
            coords[0] = 1.0F;
            coords[1] = 2.0F;
            //por ahora no hay calificación
            float qual = 0.0F;
            SupplierType supplierType = (SupplierType) supplierTypes.get(i % cant_supplierTypes);
            Supplier supplier1 = new Supplier("supplier" + i, "2012345678" + i, "San Juan 123" + i, coords, qual, supplierType);
            this.supplierService.newSupplier(supplier1);
        }
    }


    /**
     * test
     * crea product Type, SupplierType
     */
    public void new_PRODUCTOS_crear(int cant)
    {
        new_PRODUCTTYPE_crear(10);
        List<ProductType> pTi = this.productTypeService.getAll();
        int pTi_cant = pTi.size();

        new_SUPPLIER_crear(15);
        List<Supplier> suppliers = this.supplierService.getAll();
        int suppliers_cant = suppliers.size();

        Random precio_aleatorio = new Random();

        Random index_supplier = new Random();

        for (int i = 0; i < cant; i++)
        {
            float precio = precio_aleatorio.nextFloat() * 10000;
            int index = index_supplier.nextInt(suppliers_cant - 1);
            Product p1 = new Product("producto" + i, precio, 12.0F, "descripcion producto" + i, suppliers.get(index), pTi.get(i % pTi_cant));
            this.productService.newProduct(p1);
        }
    }

    public Date new_ORDENES_crear_5()
    {
        int cant = 5;
        new_PRODUCTOS_crear(10);
        new_CLIENT_crear(5);
        Date dateOfOrder = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Client cliente = (Client) this.clientService.getAll().get(0);
        Address address = cliente.getAddresses().get(0);
        List<Product> productos = this.productService.getAll();

        //crea ordenes sin items y las salva
        for (int i = 0; i < cant; i++)
        {
            this.orderService.newOrder(new Order(dateOfOrder, "comentario Orden " + i, 0.0F, cliente, address));//se crea y salva
        }

        //agrega items a las ordenes
        List<Order> ordenes = this.orderService.getAll();
        agregar_Items(ordenes.get(0), productos.get(0), 3);
        agregar_Items(ordenes.get(0), productos.get(1), 5);
        agregar_Items(ordenes.get(0), productos.get(2), 1);
        agregar_Items(ordenes.get(0), productos.get(3), 20);

        agregar_Items(ordenes.get(1), productos.get(0), 5);
        //agregar_Items(ordenes.get(1), productos.get(1),17);
        agregar_Items(ordenes.get(1), productos.get(4), 9);
        agregar_Items(ordenes.get(1), productos.get(5), 12);

        agregar_Items(ordenes.get(2), productos.get(0), 30);
        agregar_Items(ordenes.get(2), productos.get(3), 67);
        agregar_Items(ordenes.get(2), productos.get(6), 4);
        agregar_Items(ordenes.get(2), productos.get(7), 1);

        agregar_Items(ordenes.get(3), productos.get(0), 40);
        agregar_Items(ordenes.get(3), productos.get(8), 18);
        agregar_Items(ordenes.get(3), productos.get(7), 4);
        agregar_Items(ordenes.get(3), productos.get(6), 2);
        agregar_Items(ordenes.get(3), productos.get(5), 10);

        agregar_Items(ordenes.get(4), productos.get(8), 1);

        return dateOfOrder;
    }

    public Date new_ORDENES_crear_una_orden(List<Product> productos, Client cliente, Date dateOfOrder, String comentario)
    {
        Random cantidad = new Random();
        Random index = new Random();
        Random cant_items = new Random();
        int cant_productos = productos.size();
        Address address = cliente.getAddresses().get(0);

        Order orden = this.orderService.newOrder_seteado_state(new Order(dateOfOrder, comentario, 0.0F, cliente, address));//se crea y salva
        //TODO: recuperar la orden con el number (id) puesto. Recuperar el id.
        //Ver que la recuperación del repository o service se encargue de setear el Status
        //Order orden = this.orderService.getOrderByDateOfOrder(dateOfOrder); //retorna la misma orden con el id puesto y el status.
        int cantidad_items = cant_items.nextInt(6) + 1; //cantidad de items
        for (int i = 0; i < cantidad_items; i++)
        {
            int cant = cantidad.nextInt(10); //cantidad de productos del item
            int index_producto = index.nextInt(cant_productos);
            System.out.println("index producto " + index_producto);
            this.itemService.newItem(new Item(cant, "descripcion item de producto id: " + productos.get(index_producto).getId(), orden, productos.get(index_producto)));
        }
        return dateOfOrder;
    }

    public Date new_ORDENES_crear(int cant)
    {
        new_PRODUCTOS_crear(40);
        List<Product> productos = this.productService.getAll();
        new_CLIENT_crear(5);
        Date dateOfOrder = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        List<Client> clientes = this.clientService.getAll();
        int cantidad_clientes = clientes.size();
        //crea ordenes sin items y las salva
        for (int i = 0; i < cant; i++)
        {
            int index_cliente = (int) Math.random() * cantidad_clientes;
            Client cliente = (Client) clientes.get(index_cliente);
            Address address = cliente.getAddresses().get(index_cliente);
            String comentario = "comentario orden del cliente: " + cliente.getId();
            new_ORDENES_crear_una_orden(productos, cliente, dateOfOrder, comentario);
        }
        return dateOfOrder;
    }


    public void agregar_Items(Order o, Product p, int quanti)
    {
        this.itemService.newItem(new Item(quanti, "descripcion item de producto id: " + p.getId(), o, p));
    }


    @Test
    void test_PRODUCT_CreacionProduct()
    {
        //new_PRODUCT_CreacionProduct();
        new_PRODUCTOS_crear(5);
    }


    @Test
    public void test_ORDER_CreationOrder()
    {
        Date date = new_ORDENES_crear_5();

    }

    /**
     * 1) agregar un ítem a una orden ya creada.
     */
    @Test
    public void test_01_ORDER_agregar_Item_a_Order_Creada()
    {
        //new_ORDER_agregar_Item_a_Order_Creada();
        Date date = new_ORDENES_crear_5();
    }

    /**
     * 2) Confirmar un pedido. Esto implica buscar un repartidor libre y asignarle dicho pedido.
     * El estado queda en Assigned
     */
    @Test
    public void test_02_Confirmar_Pedido()
    {
        Date date = new_ORDENES_crear_5();
        List<Order> ordenes = this.orderService.getAll();
        Long number_de_ultima_orden = ordenes.get(ordenes.size() - 1).getNumber();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan(2, new Date(), true, new Date());
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
        new_ORDENES_crear_5();
        List<Order> ordenes = this.orderService.getAll();
        Long number_de_ultima_orden = ordenes.get(ordenes.size() - 1).getNumber();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan(2, new Date(), true, new Date());
        //new_DELIVERYMAN_CreacionDeliveryMan(2, "delivery", "usuario", "pass", "delivery@email.com", new Date(), true, new Date());
        //new_DELIVERYMAN_CreacionDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());
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
        new_ORDENES_crear_5();
        List<Order> ordenes = this.orderService.getAll();
        Long number_de_ultima_orden = ordenes.get(ordenes.size() - 1).getNumber();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan(2, new Date(), true, new Date());
        //new_DELIVERYMAN_CreacionDeliveryMan(3, "delivery", "usuario", "pass", "delivery@email.com", new Date(), true, new Date());

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
        new_ORDENES_crear_5();
        List<Order> ordenes = this.orderService.getAll();
        Long number_de_ultima_orden = ordenes.get(ordenes.size() - 1).getNumber();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan(2, new Date(), true, new Date());
        //new_DELIVERYMAN_CreacionDeliveryMan(4, "delivery", "usuario", "pass", "delivery@email.com", new Date(), true, new Date());
        //new_DELIVERYMAN_CreacionDeliveryMan("delivery1", "usuario1", "pass1", "delivery1@email.com", new Date(), true, new Date());
        // new_DELIVERYMAN_CreacionDeliveryMan("delivery2", "usuario2", "pass2", "delivery2@email.com", new Date(), true, new Date());
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
        new_ORDENES_crear_5();
        List<Order> ordenes = this.orderService.getAll();
        Long number_de_ultima_orden = ordenes.get(ordenes.size() - 1).getNumber();
        try
        {
            this.orderService.cancelacionDeOrden(number_de_ultima_orden);
        }
        catch (Exception e)
        {
            System.out.println("No sería Pending");
        }
    }

    @Test
    public void test_Cliente_cancela_orden_Assigned()
    {
        new_ORDENES_crear_5();
        List<Order> ordenes = this.orderService.getAll();
        Long number_de_ultima_orden = ordenes.get(ordenes.size() - 1).getNumber();

        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan(2, new Date(), true, new Date());
        //new_DELIVERYMAN_CreacionDeliveryMan(3, "delivery", "usuario", "pass", "delivery@email.com", new Date(), true, new Date());

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
            this.orderService.cancelacionDeOrden(number_de_ultima_orden);
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
    public void test_03_anadirCalificacion_a_Orden_completada()
    {
        new_CONTEXTO_INICIAL();
        List<Order> ordenes = this.orderService.getAllWithStatus();
        Iterator<Order> orderIterator = ordenes.iterator();
        while(orderIterator.hasNext())
        {
            Order orden_a_calificar = (Order) orderIterator.next();
            calificar_Orden_y_actualizar_calificacion_Supplier(orden_a_calificar);
        }

    }

    //califica una orden
    public void calificar_Orden_y_actualizar_calificacion_Supplier(Order orden_a_calificar)
    {
        //Calificación de los proveedores, un proveedor por cada Item.
        //calificación, la cual será el promedio entre las calificaciones recibidas por los clientes
        //Para cada proveedor, encontrar todas las ordenes que contienen sus productos, y buscar la calificacion de cada una.
        //el promedio será la calificacion del proveedor.
        //Tengo en cuenta solamente la calificación a cada Supplier que está presente en la última Order
        float score = (float) Math.random() * 5;
        String comentario = "comentario_" + score;
        //orden_a_calificar.setStatusByName();ya hecho por getAllWithStatus()
        Qualification qua = new Qualification(score, comentario, orden_a_calificar);
        orden_a_calificar.setQualification(qua);
        this.orderService.actualizarOrder(orden_a_calificar);

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
            supplier.setQualificationOfUsers((float) promedio);
            this.supplierService.newSupplier(supplier);
            System.out.println("promedio calificacion de ordenes del supplier id: " + supplier.getId()+ "promedio: " + promedio);
        }
    }



    //4) Actualizar los datos de un producto. Tenga en cuenta que puede cambiar su precio.
    @Test
    void test_04_actualizar_datos_producto()
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
    void test_05_eliminar_producto()
    {
        //Creo 2 productos
        new_PRODUCTOS_crear(2);
        List<Product> productos = this.productService.getAll();
        Long id_producto = productos.get(0).getId();
        //eliminar un producto ofrecido por un proveedor
        try
        {
            this.productService.eliminarProductoById(id_producto);
        }
        catch (NoExisteProductoException n)
        {

        }
        Product p = this.productService.getProductById(id_producto);
        if (p == null)
        {
            System.out.println("p :" + p);
        }
    }

    //6) Obtener todos los proveedores de un cierto tipo.
    @Test
    void test_06_obtener_proveedores_de_un_tipo()
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

        new_SUPPLIER_crear(10);
        Iterator<SupplierType> iter_ST = this.supplierTypeService.getAll().iterator();
        while (iter_ST.hasNext())
        {
            SupplierType sT = iter_ST.next();
            Long id = sT.getId();
            Iterator<Supplier> suppliers = this.supplierService.getSupplierBySupplierTypeId(id).iterator();
            while (suppliers.hasNext())
            {
                Supplier ste = (Supplier) suppliers.next();
                System.out.println("supplierType id : " + sT.getId() + " supplier: " + ste.getName() + " id: " + ste.getId());
            }
        }
    }

    //7) Obtener todos los productos y su tipo, de un proveedor específico.
    //TODO: tiene errores, corregir
    @Test
    void test_07_obtener_productos_por_proveedor()
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
    void test_08_ordenes_de_Suppier()
    {
        //crear_productos();
        new_PRODUCTOS_crear(10);
        new_ORDENES_crear_5();
        List<Supplier> suppliers = this.supplierService.getAll();
        Iterator<Supplier> iter_supplier = suppliers.iterator();
        System.out.println("cantidad de suppliers: " + suppliers.size());
        //while (iter_supplier.hasNext())
        //{
        Supplier supplier = (Supplier) iter_supplier.next();
        System.out.println("SUPPLIER ID: " + supplier.getId());
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
    void test_09_orden_mayor_precio_x_fecha()
    {
        //crear_productos();
        new_PRODUCTOS_crear(6);

        Date fecha = new_ORDENES_crear_5();
        //Date fecha = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Iterator<Order> ordenes = this.orderService.getOrderMaxPricePorFecha(fecha).iterator();
        while (ordenes.hasNext())
        {
            Order orden = ordenes.next();
            System.out.println("Orden id: " + orden.getNumber() + "  Orden precio:" + orden.getTotalPrice());
        }
    }

    /**
     * Crea 30 0rdenes, todas pending
     * Crea 15 DM y le asigna 15 ordenes que todos aceptan y finalizan, con lo cual quedan libres.
     * Luego asigna otras 15 ordenes a los mismos. En forma aleatoria algunas ordenes son aceptadas y otras rechazadas.
     * Esto actualiza las calificaciones de los clientes y DM
     */
    public void new_CONTEXTO_INICIAL()
    {
        Date dateOfBirth = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Date dateOfAdmision = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Date date = new_ORDENES_crear(30);//crea 30 ordenes con items. Todas Pending
        new_DELIVERYMAN_CreacionDeliveryMan(15, dateOfBirth, true, dateOfAdmision);//crea 15 deliveryMan
        List<Order> ordenes = this.orderService.getAll();
        List<DeliveryMan> deliveryMENFree = this.deliveryManService.getAllDeliveryManFree();
        //Asignación de Ordenes a los DeliveryMan
        Iterator<Order> iter_order = ordenes.iterator();
        Iterator<DeliveryMan> iter_deliveryMan = deliveryMENFree.iterator();
        //Pending -> Assigned -> Sent ->Delivered - 15 primeras ordenes.

        while (iter_order.hasNext() && iter_deliveryMan.hasNext())
        {
            Order orden = (Order) iter_order.next();
            DeliveryMan deliveryManFree = (DeliveryMan) iter_deliveryMan.next();
            this.orderService.assignOrderToDeliveryMan(orden.getNumber(), deliveryManFree.getId());
            try
            {
                this.orderService.aceptacionDeOrden(orden.getNumber());
                this.orderService.finalizacionDeOrden(orden.getNumber());
            }
            catch (Exception e)
            {
                System.out.println("La orden no estaba en el estado correcto");
            }
        }
        iter_deliveryMan = deliveryMENFree.iterator();//repaso los 15 deliveryMan y sigo agregando ordenes
        while (iter_order.hasNext() && iter_deliveryMan.hasNext())
        {
            Order orden = (Order) iter_order.next();
            DeliveryMan deliveryManFree = (DeliveryMan) iter_deliveryMan.next();
            this.orderService.assignOrderToDeliveryMan(orden.getNumber(), deliveryManFree.getId());
            try
            {
                if (Math.random() < 0.5)
                {
                    this.orderService.aceptacionDeOrden(orden.getNumber());
                    this.orderService.finalizacionDeOrden(orden.getNumber());
                } else
                    this.orderService.rechazoDeOrden(orden.getNumber());
            }
            catch (Exception e)
            {
                System.out.println("La orden no estaba en el estado correcto");
            }
        }
    }

    //10) Obtener los diez repartidores con mayor puntaje.
    // un repartidor suma un punto cuando completa una entrega mientras que
    // resta dos puntos cuando rechaza un pedido que le fue asignado.
    @Test
    void test_10_obtener_10_repartidores_mayor_puntaje()
    {
        //contexto inicial
        new_CONTEXTO_INICIAL();


        //iter_order = ordenes.iterator();
        //  while (iter_order.hasNext())
        //   {
        //        Order orden_a_calificar = (Order)iter_order.next();
        //       calificar_Orden_y_actualizar_calificacion_Supplier(orden_a_calificar);
        //   }

        Iterator<DeliveryMan> deliveryMEN_buscados = this.deliveryManService.getAllOrderByScore().iterator();
        while (deliveryMEN_buscados.hasNext())
        {
            DeliveryMan dm = (DeliveryMan) deliveryMEN_buscados.next();
            System.out.println("delivery man: " + dm.getId() + " ordenados por score: " + dm.getScore());
        }
    }

    public void DeliveryMan_asignacion_confirma_pedido()
    {
        Date date = new_ORDENES_crear_5();
        List<Order> ordenes = this.orderService.getAll();
        Long number_de_ultima_orden = ordenes.get(ordenes.size() - 1).getNumber();
        //Alta de un DeliveryMan
        new_DELIVERYMAN_CreacionDeliveryMan(2, new Date(), true, new Date());
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


    @Test
    void getOrder()
    {
        Iterator<Order> ordenes = this.supplierService.getOrderBySupplier().iterator();
        while (ordenes.hasNext())
        {
            Order orden = (Order) ordenes.next();
            //System.out.println("Order Status: "+item.getOrder().getOrderStatus().getName()+" id Item: "+item.getId()+" item.product.supplier: "+ item.getProduct().getSupplier().getId());
            System.out.println("Order " + orden.getNumber());
            Iterator<Item> items = orden.getItems().iterator();
            while (items.hasNext())
            {
                Item item = (Item) items.next();
                System.out.println("--supplier " + item.getProduct().getSupplier().getId());
            }


        }
    }

    //TODO: no andaaaaaaaa !!!
    //11) Obtener los diez proveedores que más órdenes despacharon.
    // vincular Order con Supplier
    //Se puede resolver con una unica consulta.
    //Te dejo algunas pistas: con el GROUP BY podes agrupar por un cierto parámetro, tenes el COUNT para contar la cantidad de elementos estos grupos y luego tenes el ORDER BY para ordenar estos resultados.
    //Asi podrias agrupar las ordenes por su deliveryMan y ver que cual es el grupo que mas tiene.
    //Vas a tener que usar el @Query por que el gruoup by no puede colocarse en la cabecera de un metodo.

    //https://stackoverflow.com/questions/7001226/how-to-order-by-count-in-jpa
    @Test
    void test_11_obtener_10_proveedores_con_mas_ordenes_despachadas()
    {
        Iterator<Supplier> suppliers = this.supplierService.getTop10Supplier().iterator();
        while (suppliers.hasNext())
        {
            Supplier supplier = (Supplier) suppliers.next();
            //System.out.println("Order Status: "+item.getOrder().getOrderStatus().getName()+" id Item: "+item.getId()+" item.product.supplier: "+ item.getProduct().getSupplier().getId());
            System.out.println("Supplier " + supplier.getId());
        }
    }

    public Date new_PRECIOS_CAMBIAR()
    {
        System.out.println("Cambiando precios----------------------------------------------");
        List<Product> productos = this.productService.getAll();
        Iterator<Product> productosIterator = productos.iterator();
        Date fecha_cambio = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Random precio_aleatorio = new Random();
        while (productosIterator.hasNext())
        {
            Product producto = (Product) productosIterator.next();
            float precio = precio_aleatorio.nextFloat() * 10000;
            producto.setPrice(precio);
            producto.setPrices(this.historicalProductPriceService.getAll(producto.getId()));
            HistoricalProductPrice hpp = new HistoricalProductPrice(precio, fecha_cambio, null, producto);
            producto.agregarNuevoHpp(hpp);
            this.historicalProductPriceService.saveListHistoricalProductoPrice(producto.getPrices());
        }

        return fecha_cambio;

    }


    //12) Obtener los precios de un producto entre dos fechas dadas.
    @Test
    void test_12_obtener_los_precios_un_producto_entre_fechas()
    {
        //new_CONTEXTO_INICIAL();

        Date fecha1 = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        //Date fecha2 = new_PRECIOS_CAMBIAR();
        Date fecha3 = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        //Date fecha4 = new_PRECIOS_CAMBIAR();
        Date fecha5 = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        // Date fecha6 = new_PRECIOS_CAMBIAR();
        Date fecha7 = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();

        List<Product> productos = this.productService.getAll();
        Product producto = productos.get(0);//tomo el primer producto
        Iterator<HistoricalProductPrice> hppIterator = this.historicalProductPriceService.getPrices(producto.getId(), fecha1, fecha7).iterator();
        while (hppIterator.hasNext())
        {
            HistoricalProductPrice hpp = (HistoricalProductPrice) hppIterator.next();
            System.out.println("Producto id: " + producto.getId() + " desde: " + hpp.getStartDate() + " hasta: " + hpp.getFinishDate() + " valor: " + hpp.getPrice());
        }
    }


    //13) Obtener el precio promedio de los productos de cada tipo, para todos los tipos.
    @Test
    void test_13_obtener_precio_promedio()
    {
        new_PRODUCTOS_crear(30);
        new_PRECIOS_CAMBIAR();
        List<ProductTypeAvgPrice_DTO> ptap = this.productService.getAvgPriceForProductType();
        Iterator<ProductTypeAvgPrice_DTO> ptapIterator = ptap.iterator();
        while (ptapIterator.hasNext())
        {
            ProductTypeAvgPrice_DTO productTypeAvgPrice_dto = (ProductTypeAvgPrice_DTO) ptapIterator.next();
            System.out.println("Product Type id: " + productTypeAvgPrice_dto.getId_product_type() +
                    " Precio Promedio: " + productTypeAvgPrice_dto.getPrecio_promedio());
        }

    }

    //14) Obtener la información de los proveedores que tengan al menos una calificación de una estrella (la más baja).
    // Es necesario también el número de estas calificaciones que el proveedor posee.
    // las calificaciones salen de la calificacion que da el cliente a cada orden. Cada item de la orden se queda con esa calificación
    // de esa forma se lo vincula con el supplier. Item->Product->Supplier  =  Item->Order->Qualification
    //TODO: COMPLETAR !!!
    @Test
    void test_14_suppliers_con_calificacion_1()
    {
        List<Supplier> suppliers = this.supplierService.getByQualification1();
        Iterator<Supplier> supplierIterator = suppliers.iterator();
        while (supplierIterator.hasNext())
        {
            Supplier supplier = (Supplier) supplierIterator.next();
            System.out.println("Supplier id: " +supplier.getId()+ " calificacion: " + supplier.getQualificationOfUsers());
        }
    }

    //15) Obtener los proveedores que ofrezcan productos de todos los tipos.
    @Test
    void test_15_obtener_proveedores_con_todos_types()
    {
        List<Supplier> suppliers = this.supplierService.getSupplierWithAllTypes();
        Iterator<Supplier> supplierIterator = suppliers.iterator();
        while(supplierIterator.hasNext())
        {
            Supplier supplier = supplierIterator.next();
            System.out.println("Supplier id: "+supplier.getId());
        }

    }

    //AUXILIARES

    @Test
    void verOrdenes()
    {
        Iterator<Order> orderIterator = this.orderService.getAll().iterator();
        while(orderIterator.hasNext())
        {
            Order orden = (Order)orderIterator.next();
            System.out.println("---ORDER ID: "+orden.getNumber());
            Iterator<Item> itemIterator = orden.getItems().iterator();
            while(itemIterator.hasNext())
            {
                Item item = itemIterator.next();
                System.out.println(item.toString());
            }
        }
    }

    @Test
    void contexto_inicial()
    {
        new_CONTEXTO_INICIAL();
    }
    @Test
    void prueba()
    {
        System.out.println("OK!");
    }

}
