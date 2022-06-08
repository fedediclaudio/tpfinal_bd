package com.bd.tpfinal;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.services.*;
import com.bd.tpfinal.utils.NoExisteProductoException;
import com.bd.tpfinal.utils.NoMasRandomException;
import com.bd.tpfinal.utils.RandomSinRepetir;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import java.util.*;

@SpringBootTest
public class TPfinalApplicationTests
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

    @BeforeTestClass
    public void setUp()
    {

    }

    @Test
    void test_00_contexto()
    {
        new_CONTEXTO_INICIAL();
        verOrdenes();
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

    public void new_crear_SupplierType(int cant)
    {
        int i = 0;
        for (i = 0; i < cant; i++)
        {
            SupplierType supplierType = new SupplierType("supplierType" + i, "Descripcion SupplierType" + i);
            this.supplierTypeService.newSupplierType(supplierType);
        }
    }

    /**
     * pre: crear supplierTypes antes
     * @param cant
     */
    public void new_SUPPLIER_crear(int cant, List<SupplierType> supplierTypes)
    {
        //new_crear_SupplierType(3);
        //List<SupplierType> supplierTypes = this.supplierTypeService.getAll();
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

    public void new_PRODUCTOS_crear(int cant, List<Supplier> suppliers, List<ProductType> productTypes)
    {
        //List<Supplier> suppliers = this.supplierService.getAll();
        int productTypes_cant = productTypes.size();
        int suppliers_cant = suppliers.size();
        Random precio_aleatorio = new Random();
        Random index_supplier = new Random();
        //asigna al primer supplier un producto de cada tipo
        new_PRODUCTOS_crear_Supplier_con_ProductTypes(suppliers.get(0), productTypes);
        for (int i = 0; i < cant; i++)
        {
            float precio = precio_aleatorio.nextFloat() * 10000;
            //int index = index_supplier.nextInt(suppliers_cant);
            Product p1 = new Product("producto" + i, precio, 12.0F,
                    "descripcion producto" + i,
                    suppliers.get(i % suppliers_cant),
                    productTypes.get(i % productTypes_cant));
            this.productService.newProduct(p1);
        }
    }

    public void new_PRODUCTOS_crear_Supplier_con_ProductTypes(Supplier supplier, List<ProductType> productTypes)
    {
        int productTypes_cant = productTypes.size();
        Random precio_aleatorio = new Random();
        //creo tantos productos como types existen, para un mismo proveedor
        for (int i = 0; i < productTypes_cant; i++)
        {
            float precio = precio_aleatorio.nextFloat() * 10000;
            //int index = index_supplier.nextInt(suppliers_cant);
            Product p1 = new Product("producto" + i, precio, 20.0F,
                    "descripcion producto" + i,
                    supplier,
                    productTypes.get(i));
            this.productService.newProduct(p1);
        }
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

    public void new_DELIVERYMAN_CreacionDeliveryMan(int cant, Date dateOfBirth, boolean free, Date dateOfAdmission)
    {
        int i = 0;
        for (i = 0; i < cant; i++)
        {
            DeliveryMan deliveryMan = new DeliveryMan("DM_name_" + i, "usuario" + i, "pass" + i, "email" + i, dateOfBirth, free, dateOfAdmission);
            this.deliveryManService.newDeliveryMan(deliveryMan);
        }
    }

    public Date new_ORDENES_crear(int cant)
    {
        //new_PRODUCTOS_crear(100);
        List<Supplier> suppliers = this.supplierService.getAll();
        int cantSuppliers = suppliers.size();
        Random prox_index_supplier = new Random();
        //new_CLIENT_crear(5);
        Date dateOfOrder = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        List<Client> clientes = this.clientService.getAll();
        int cantidad_clientes = clientes.size();
        //crea ordenes
        for (int i = 0; i < cant; i++)
        {
            int index_cliente = (int) Math.random() * cantidad_clientes;
            Client cliente = (Client) clientes.get(index_cliente);
            Address address = cliente.getAddresses().get(index_cliente);
            String comentario = "comentario orden del cliente: " + cliente.getId();
            //elijo los productos de un supplier Supplier
            int index = prox_index_supplier.nextInt(cantSuppliers);
            new_ORDENES_crear_una_orden(suppliers.get(index).getId(), cliente, dateOfOrder, comentario);
        }
        return dateOfOrder;
    }

    public Date new_ORDENES_crear_una_orden(Long supplier_id, Client cliente, Date dateOfOrder, String comentario)
    {
        List<Product> productos = this.productService.getBySupplierId(supplier_id);
        System.out.println("+++Creación de una orden");
        Random cantidad = new Random();
        //Random index = new Random();
        Random cant_items = new Random();
        int cant_productos = productos.size();
        Address address = cliente.getAddresses().get(0);

        RandomSinRepetir rsp = new RandomSinRepetir(0, cant_productos);
        int index_producto=0;

        Order orden = this.orderService.newOrder_seteado_state(new Order(dateOfOrder, comentario, 0.0F, cliente, address));//se crea y salva
        System.out.println("orden creada: "+orden.getNumber());
        int cantidad_items = 2 + cant_items.nextInt(10); //cantidad de items
        for (int i = 0; i < cantidad_items && i < cant_productos; i++)
        {
            int cant = 1+ cantidad.nextInt(10); //cantidad de productos del item
            try
            {
                index_producto = rsp.siguiente();
            }
            catch (NoMasRandomException e)
            {
                e.printStackTrace();
            }
            System.out.println("index producto " + index_producto);
            this.itemService.newItem(new Item(cant, "descripcion item de producto id: " + productos.get(index_producto).getId(), orden, productos.get(index_producto)));
        }
        return dateOfOrder;
    }

    /**
     * Crea 30 0rdenes, todas pending
     * Crea 15 DM y le asigna 15 ordenes que todos aceptan y finalizan, con lo cual quedan libres.
     * Luego asigna otras 15 ordenes a los mismos. En forma aleatoria algunas ordenes son aceptadas y otras rechazadas.
     * Esto actualiza las calificaciones de los clientes y DM
     */
    public void new_CONTEXTO_INICIAL()
    {
        new_PRODUCTTYPE_crear(5);
        new_crear_SupplierType(5);
        List<SupplierType> supplierTypes = this.supplierTypeService.getAll();
        new_SUPPLIER_crear(15, supplierTypes);
        List<Supplier> suppliers = this.supplierService.getAll();
        List<ProductType> productTypes = this.productTypeService.getAll();
        new_PRODUCTOS_crear(500, suppliers, productTypes);
        new_CLIENT_crear(5);
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
            this.orderService.asignacionDeOrden(orden.getNumber(), deliveryManFree.getId());
            try
            {
                //estado pasa a Sent
                this.orderService.aceptacionDeOrden(orden.getNumber());
                //estado pasa a Delivered
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
            //estado pasa a Assigned
            this.orderService.asignacionDeOrden(orden.getNumber(), deliveryManFree.getId());
            try
            {
                if (Math.random() < 0.5)
                {
                    //estado pasa a Sent
                    this.orderService.aceptacionDeOrden(orden.getNumber());
                    //estado pasa a Delivered
                    this.orderService.finalizacionDeOrden(orden.getNumber());
                } else
                    //estado pasa a Cancelled por parte del DM
                    this.orderService.rechazoDeOrden(orden.getNumber());
            }
            catch (Exception e)
            {
                System.out.println("La orden no estaba en el estado correcto");
            }
        }
    }

    /**
     * 1) agregar un ítem a una orden ya creada.
     * Se resuelve en el contexto inicial
     */

    /**
     * 2) Confirmar un pedido. Esto implica buscar un repartidor libre y asignarle dicho pedido.
     * El estado queda en Assigned
     * Se resuelve en el contexto inicial
     */


    /**
     * Cliente cancela Order (Order en Pending o Assigned)
     * La Order pasa a Cancelled
     */
    @Test
    public void test_Cliente_cancela_orden_Pending()
    {
        //se crea una Order con Items.
        //El estado es Pending
        new_ORDENES_crear(1);
        List<Order> ordenes = this.orderService.getAll();
        Long number_de_ultima_orden = ordenes.get(ordenes.size() - 1).getNumber();
        try
        {
            //estado pasa a Cancelled
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
        new_ORDENES_crear(1);
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
        this.orderService.asignacionDeOrden(orden_buscada.getNumber(), deliveryManFree.getId());
        //Long number_de_ultima_orden = new_ORDER_agregar_Item_a_Order_Creada();
        try
        {
            //estado pasa a Cancelled
            this.orderService.cancelacionDeOrden(number_de_ultima_orden);
            Order orden = this.orderService.getByNumber(number_de_ultima_orden);
            System.out.println("Estado de la orden: "+ orden.getOrderStatus().getName() );
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
    @Test
    public void test_03_anadirCalificacion_a_Orden_completada()
    {
        //1) obtener todas las ordenes con calificación == 0;
        List<Order> ordenes = this.orderService.getAllWithoutQual();
        Iterator<Order> orderIterator = ordenes.iterator();
        Random calif = new Random();
        while (orderIterator.hasNext())
        {
            Order orden_a_calificar = (Order) orderIterator.next();
            float score = calif.nextInt(5);
            String comentario = "comentario_" + score;
            System.out.println("+++ se califica la orden: "+orden_a_calificar.getNumber());
            //test_03_2_calificar_Orden_y_Supplier(orden_a_calificar, score, comentario);
            //Qualification qua = new Qualification(score, comentario, orden_a_calificar);
            //orden_a_calificar.setQualification(qua);
            // esto tal vez lo debería hacer el service directamente.
            // o sea, orderService.calificar(...)
            //this.orderService.actualizarOrder(orden_a_calificar);  //actualiza la orden con la calificación.
            this.orderService.calificarOrden(score, comentario, orden_a_calificar.getNumber());
        }
        //test_03_2_calificar_Suppliers();
    }
    //califica todos los suppliers
    public void test_03_2_calificar_Suppliers()
    {
        Iterator<Supplier> suppliers = this.supplierService.getAll().iterator();
        while(suppliers.hasNext())
        {
            Supplier proveedor = (Supplier) suppliers.next();
            Iterator<Order> ordenes_de_supplier = this.orderService.getOrderByIdSupplier(proveedor.getId()).iterator();
            while (ordenes_de_supplier.hasNext())
            {
                System.out.println("supplier: " + proveedor.getId() + " numero de orden:  " + ordenes_de_supplier.next().getNumber());
            }
            float promedio = this.orderService.getQualificationAverage(proveedor.getId());
            proveedor.setQualificationOfUsers(promedio);
            this.supplierService.newSupplier(proveedor);//Esto actualiza la información del proveedor, con su calificación
            System.out.println("promedio calificacion de ordenes del supplier id : " + proveedor.getId() + "  promedio: " + promedio);
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
        List<SupplierType> supplierTypes = this.supplierTypeService.getAll();
        new_SUPPLIER_crear(15, supplierTypes);
        List<Supplier> suppliers = this.supplierService.getAll();
        List<ProductType> productTypes = this.productTypeService.getAll();
        new_PRODUCTOS_crear(2, suppliers, productTypes);
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
        /*
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

         */
        Iterator<SupplierType> iter_ST = this.supplierTypeService.getAll().iterator();
        while (iter_ST.hasNext())
        {
            SupplierType sT = iter_ST.next();
            Long id_type = sT.getId();
            Iterator<Supplier> suppliers = this.supplierService.getSupplierBySupplierTypeId(id_type).iterator();
            while (suppliers.hasNext())
            {
                Supplier ste = (Supplier) suppliers.next();
                System.out.println("supplierType id : " + sT.getId() + " ++ supplier id: " + ste.getId());
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

        //Iterator<Product> productos = this.productService.getProductoByProductType(productType.getId()).iterator();
        Iterator<Product> productos = this.productService.getBySupplierId(unSupplier.getId()).iterator();

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
        //new_PRODUCTOS_crear(10);
        //new_ORDENES_crear_5();
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
    @Test
    void test_09_orden_mayor_precio_x_fecha()
    {
        Date fecha = new_ORDENES_crear(10);
        //Date fecha = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Iterator<Order> ordenes = this.orderService.getOrderMaxPricePorFecha(fecha).iterator();
        while (ordenes.hasNext())
        {
            Order orden = ordenes.next();
            System.out.println("Orden id: " + orden.getNumber() + "  Orden precio:" + orden.getTotalPrice());
        }
    }

    //10) Obtener los diez repartidores con mayor puntaje.
    // un repartidor suma un punto cuando completa una entrega mientras que
    // resta dos puntos cuando rechaza un pedido que le fue asignado.
    @Test
    void test_10_obtener_10_repartidores_mayor_puntaje()
    {
        Iterator<DeliveryMan> deliveryMEN_buscados = this.deliveryManService.getAllOrderByScore().iterator();
        while (deliveryMEN_buscados.hasNext())
        {
            DeliveryMan dm = (DeliveryMan) deliveryMEN_buscados.next();
            System.out.println("delivery man: " + dm.getId() + " ordenados por score: " + dm.getScore());
        }
    }

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
        Iterator<Supplier_Order_DTO> suppliers = this.supplierService.getTopTenSupplierWithOrders().iterator();
        while (suppliers.hasNext())
        {
            Supplier_Order_DTO supplier = (Supplier_Order_DTO) suppliers.next();
            //System.out.println("Order Status: "+item.getOrder().getOrderStatus().getName()+" id Item: "+item.getId()+" item.product.supplier: "+ item.getProduct().getSupplier().getId());
            System.out.println("Supplier " + supplier.getId_supplier() + " cantidad de ordenes: " + supplier.getCantidad_ordenes());

        }
    }

    public Date new_PRODUCTOS_PRECIOS_CAMBIAR()
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
        Date fecha1 = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Date fecha2 = new_PRODUCTOS_PRECIOS_CAMBIAR();
        Date fecha3 = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Date fecha4 = new_PRODUCTOS_PRECIOS_CAMBIAR();
        Date fecha5 = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Date fecha6 = new_PRODUCTOS_PRECIOS_CAMBIAR();
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
        new_PRODUCTOS_PRECIOS_CAMBIAR();
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

    @Test
    void test_14_suppliers_con_calificacion_1()
    {
        List<Supplier_Qualif_DTO> suppliers = this.supplierService.getByQualification1(1F);
        Iterator<Supplier_Qualif_DTO> supplierIterator = suppliers.iterator();
        while (supplierIterator.hasNext())
        {
            Supplier_Qualif_DTO supplier = (Supplier_Qualif_DTO) supplierIterator.next();
            System.out.println("Supplier id: " + supplier.getId_supplier() +

                    " cantidad: " + supplier.getCantidad_1());
        }
    }

    //15) Obtener los proveedores que ofrezcan productos de todos los tipos.
    @Test
    void test_15_obtener_proveedores_con_todos_types()
    {
        List<Supplier> suppliers = this.supplierService.getSupplierWithAllTypes();
        Iterator<Supplier> supplierIterator = suppliers.iterator();
        while (supplierIterator.hasNext())
        {
            Supplier supplier = supplierIterator.next();
            System.out.println("Supplier id: " + supplier.getId());
        }

    }

    //AUXILIARES

    @Test
    void verOrdenes()
    {
        Iterator<Order> orderIterator = this.orderService.getAll().iterator();
        //Iterator<Order> orderIterator = this.supplierService.getOrderBySupplier().iterator();
        while (orderIterator.hasNext())
        {
            Order orden = (Order) orderIterator.next();
            System.out.println("---ORDER ID: " + orden.getNumber());
            Iterator<Item> itemIterator = orden.getItems().iterator();
            while (itemIterator.hasNext())
            {
                Item item = itemIterator.next();
                System.out.println(item.toString());
            }
        }
    }

    @Test
    void test_00_getOrder()
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



}
