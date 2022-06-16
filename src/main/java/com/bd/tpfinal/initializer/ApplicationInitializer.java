package com.bd.tpfinal.initializer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.bd.tpfinal.elastic.ClientElasticRepository;
import com.bd.tpfinal.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.bd.tpfinal.repositories.implementations.ClientRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;
import com.bd.tpfinal.services.AddressService;
import com.bd.tpfinal.services.ClientService;
import com.bd.tpfinal.services.DeliveryManService;
import com.bd.tpfinal.services.OrderService;
import com.bd.tpfinal.services.ProductService;
import com.bd.tpfinal.services.ProductTypeService;
import com.bd.tpfinal.services.SupplierService;
import com.bd.tpfinal.services.SupplierTypeService;
import com.querydsl.core.types.Predicate;

@Component
public class ApplicationInitializer implements CommandLineRunner {
    @Autowired AddressService addressService;
    @Autowired ClientService clientService;
    @Autowired DeliveryManService deliveryManService;
    @Autowired OrderService orderService;
    @Autowired ProductService productService;
    @Autowired ProductTypeService productTypeService;
    @Autowired SupplierService supplierService;
    @Autowired SupplierTypeService supplierTypeService;
    
    
    @Autowired OrderRepository orderRepository;
    @Autowired ClientRepository clientRepository;
    @Autowired SupplierRepository supplierRepository;

    @Autowired
    ClientElasticRepository clientElasticRepository;
    ////////
    // URL Swagger: http://localhost:8081/swagger-ui/index.html
    ////////

    @Override
    public void run(String... args) throws Exception {
        if (clientService.clientCount() > 0) {
        	query();
        	return;
        }

        Address addr1 = new Address();
        addr1.setName("Direccion Nombre");
        addr1.setAddress("50 y 120");
        addr1.setApartment(null);
        addr1.setCoords(new float[]{30, 55});
        addr1.setDescription("Primer direccion");
        addr1 = addressService.addNewAddress(addr1);

        List<Address> addressList = new ArrayList<Address>();
        addressList.add(addr1);

        Client cli1 = new Client();
        cli1.setActive(true);
        cli1.setAddresses(addressList);
        cli1.setDateOfBirth(LocalDate.of(1980, 1, 5));
        cli1.setDateOfRegister(LocalDate.now());
        cli1.setEmail("mail@email.com");
        cli1.setName("John Doe");
        cli1.setPassword("S3cret");
        cli1.setUsername("John");

        addr1.setClient(cli1);

        clientService.addNewClient(cli1);


        Address addr2 = new Address();
        addr2.setName("Direccion Nombre Cli 2");
        addr2.setAddress("1 y 115");
        addr2.setApartment(null);
        addr2.setCoords(new float[]{30, 55});
        addr2.setDescription("Segunda direccion");
        addr2 = addressService.addNewAddress(addr2);

        addressList = new ArrayList<Address>();
        addressList.add(addr2);

        Client cli2 = new Client();
        cli2.setActive(true);
        cli2.setAddresses(addressList);
        cli2.setDateOfBirth(LocalDate.of(1983, 5, 20));
        cli2.setDateOfRegister(LocalDate.now());
        cli2.setEmail("mail2@email.com");
        cli2.setName("John Doe 2");
        cli2.setPassword("S3cret");
        cli2.setUsername("John 2");

        addr2.setClient(cli2);
        clientService.addNewClient(cli2);


        DeliveryMan dm = new DeliveryMan();
        dm.setActive(true);
        dm.setDateOfBirth(LocalDate.of(1989, 12, 11));
        dm.setEmail("dm@email.com");
        dm.setName("Delivery Man Prueba 1");
        dm.setPassword("dmPass");
        dm.setUsername("dm1");

        dm = deliveryManService.addNewDeliveryMan(dm);

        // Creo los SupplierTypes
        SupplierType st1 = this.createSupplierType("SupplierType 1", "Primer SupplierType");
        SupplierType st2 = this.createSupplierType("SupplierType 2", "Segunda SupplierType");
        
        // Creo los Suppliers
        Supplier s1 = this.createSupplier("Supplier 1", "1111111", "Direccion 1", new float[]{31, 51}, st1);
        Supplier s2 = this.createSupplier("Supplier 2", "2222222", "Direccion 2", new float[]{44, 56}, st1);
        Supplier s3 = this.createSupplier("Supplier 3", "3333333", "Direccion 3", new float[]{22, 64}, st2);
        
        // Creo los ProductTypes
        ProductType pt1 = this.createProductType("ProductType 1", "Un ProductType");
        ProductType pt2 = this.createProductType("ProductType 2", "Otro ProductType");
        
        // Creo los Products
        Product p1 = this.createProduct("Producto1", "Producto 1", 22, s1, pt1);
        Product p2 = this.createProduct("Producto2", "Producto 2", 27, s2, pt1);
        Product p3 = this.createProduct("Producto3", "Producto 3", 36, s1, pt2);
        Product p4 = this.createProduct("Producto4", "Producto 4", 29, s1, pt1);
        Product p5 = this.createProduct("Producto5", "Producto 5", 28, s3, pt1);
        
        // Agrego los productos a los suppliers
        s1.addProduct(p1);
        s1.addProduct(p3);
        s1.addProduct(p4);
        supplierRepository.save(s1);
        
        s2.addProduct(p2);
        supplierRepository.save(s2);

        s3.addProduct(p5);
        supplierRepository.save(s3);
        
        // Creo la Orden
        Order order = orderService.createOrder(cli1.getId());
        
        // Assigno la direccion del cliente
        orderService.assignAddressToOrder(order.getNumber(), cli1.getAddresses().get(0).getId());
        
        // Agrego los productos a la orden
        orderService.addProductToOrder(order.getNumber(), p1.getId(), 2, "Compra un Producto");
        orderService.addProductToOrder(order.getNumber(), p3.getId(), 3, "Compra otro Producto");

        clientElasticRepository.save(clientToClientElastic(cli1));
        clientElasticRepository.save(clientToClientElastic(cli2));

    }
    
    private SupplierType createSupplierType(String name, String desc) throws Exception {
    	SupplierType st = new SupplierType();
        st.setName(name);
        st.setDescription(desc);
        st = supplierTypeService.createNewSupplierType(st);
        return st;
    }
    
    private Supplier createSupplier(String name, String cuil, String address, float[] coords, SupplierType st) throws Exception {
    	Supplier s = new Supplier();
        s.setName(name);
        s.setCuil(cuil);
        s.setAddress(address);
        s.setCoords(coords);
        s.setType(st);
        s = supplierService.createNewSupplier(s);
        return s;
    }
    
    private ProductType createProductType(String name, String desc) throws Exception {
    	ProductType pt = new ProductType();
        pt.setName(name);
        pt.setDescription(desc);
        pt = productTypeService.createNewProductType(pt);
        return pt;
    }
    
    private Product createProduct(String name, String desc, float price, Supplier supp, ProductType pt) throws Exception {
        Product prod = new Product();
        prod.setName(name);
        prod.setDescription(desc);
        prod.setPrice(22);
        prod.setSupplier(supp);
        prod.setType(pt);
        prod = productService.createNewProduct(prod);
        return prod;
    }
    
    private void query() {
    	QClient qClient = new QClient("Client");
    	Predicate predicate = qClient.id.eq("629e241a7d818e5132be0c90");
    	List<Client> clients = (List<Client>) clientRepository.findAll(predicate);
    	clients.forEach(System.out::println);
    	QOrder qOrder = new QOrder("Order");
    	List<Order> orders = (List<Order>) orderRepository.findAll(predicate);
    	orders.forEach(System.out::println);
    }

    private ClientElastic clientToClientElastic(final Client client){
        return ClientElastic
                .builder()
                .id(client.getId())
                .name(client.getName())
                .username(client.getUsername())
                .password(client.getPassword())
                .email(client.getEmail())
                .dateOfBirth(client.getDateOfBirth())
                .build();
    }
}
