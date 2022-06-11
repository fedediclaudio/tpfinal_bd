package com.bd.tpfinal.utils;

import com.bd.tpfinal.controllers.HistoricalProductPriceController;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.util.*;

public class ApplicationRunner implements CommandLineRunner
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

    @Override
    public void run(String... args) throws Exception
    {
        new_CONTEXTO_INICIAL();
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
        Random prox_index_cliente = new Random();
        //new_CLIENT_crear(5);
        Date dateOfOrder = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        List<Client> clientes = this.clientService.getAll();
        int cantidad_clientes = clientes.size();
        //crea ordenes
        for (int i = 0; i < cant; i++)
        {
            int index_cliente = prox_index_cliente.nextInt(cantidad_clientes);
            Client cliente = (Client) clientes.get(index_cliente);
            Address address = cliente.getAddresses().get(0);
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
     * Crea 40 ordenes que comienzan en Pending y luego evolucionan hacia los otros estados.
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
        Date date = new_ORDENES_crear(40);//crea 40 ordenes con items. Todas Pending

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
        Date date2 = new_ORDENES_crear(10);//crea 10 ordenes con items. Todas quedan Pending
    }
}
