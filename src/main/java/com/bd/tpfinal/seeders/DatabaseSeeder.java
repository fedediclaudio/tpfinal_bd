package com.bd.tpfinal.seeders;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
import java.util.logging.Logger;

@Component
public class DatabaseSeeder {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductTypeRepository productTypeRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ItemRepository itemRepository;
    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;
    @Autowired
    private SupplierTypeRepository supplierTypeRepository;
    @Autowired
    private SupplierRepository supplierRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DeliveryManRepository deliveryManRepository;
    @Autowired
    private AddressRepository addressRepository;

    private Logger logger = Logger.getLogger(String.valueOf(DatabaseSeeder.class));

    @EventListener
    public void seedTables(ContextRefreshedEvent event) throws Exception {
        seedProductTypeTable();
        seedSupplierTypeTable();
        seedSupplierTable();
        seedClientTable();
        seedOrderTable();
        // seedHistoricalProductPriceTable();
    }

    private void seedProductTypeTable() {
        String sql = "SELECT name, description FROM product_type";
        List<ProductType> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if (u == null || u.size() <= 0) {
            ProductType productType_1 = new ProductType();
            productType_1.setName("Almacen");
            productType_1.setDescription("Productos almacen");

            ProductType productType_2 = new ProductType();
            productType_2.setName("Limpieza");
            productType_2.setDescription("Productos limpieza");

            ProductType productType_3 = new ProductType();
            productType_3.setName("Verduleria");
            productType_3.setDescription("Productos verduleria");

            ProductType productType_4 = new ProductType();
            productType_4.setName("Autopartes");
            productType_4.setDescription("Productos autopartes");

            ProductType productType_5 = new ProductType();
            productType_5.setName("Comestibles");
            productType_5.setDescription("Productos para consumo humano");

            ProductType productType_6 = new ProductType();
            productType_6.setName("Café");
            productType_6.setDescription("Productos de café");

            productTypeRepository.saveAll(Arrays.asList(productType_1, productType_2, productType_3, productType_4, productType_5, productType_6));
            logger.info("ProductType table Cargada");
        } else {
            logger.info("ProductType Seeding no requerido, tabla con datos");
        }
    }

    private void seedSupplierTypeTable(){
        String sql = "SELECT * FROM supplier_type";
        List<SupplierType> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {
            SupplierType supplierType_1 = new SupplierType();
            supplierType_1.setDescription("Productos variados");
            supplierType_1.setName("Supermercado");

            SupplierType supplierType_2 = new SupplierType();
            supplierType_2.setDescription("Productos Limpieza");
            supplierType_2.setName("Artículos de hogar");

            SupplierType supplierType_3 = new SupplierType();
            supplierType_3.setDescription("Productos de jardineria");
            supplierType_3.setName("Artículos de exterior");

            SupplierType supplierType_4 = new SupplierType();
            supplierType_4.setDescription("Productos de mascotas");
            supplierType_4.setName("Artículos de animales");

            SupplierType supplierType_5 = new SupplierType();
            supplierType_5.setDescription("Productos variados mayorista");
            supplierType_5.setName("Artículos mayoristas");


            supplierTypeRepository.saveAll(Arrays.asList(supplierType_1, supplierType_2, supplierType_3, supplierType_4, supplierType_5));

            logger.info("SupplierType table Cargada");
        } else {
            logger.info("SupplierType Seeding no requerido, tabla con datos");
        }
    }

    private void seedSupplierTable(){
        String sql = "SELECT * FROM supplier";
        List<Supplier> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);

        if(u == null || u.size() <= 0) {
            List<Product> coffeeProducts;
            List<Product> generalProducts;

            Supplier supplier_1 = new Supplier();
            supplier_1.setAddress("Soberania Nacional 200");
            supplier_1.setName("Bonafide");
            supplier_1.setCoords(new float[]{-43.44f,10.22f});
            supplier_1.setCuil("7022221224");
            supplier_1.setQualificationOfUsers(4.3f);
            coffeeProducts = createAndReturnCoffeeProducts(supplier_1, 32);
            supplier_1.setProducts(coffeeProducts);
            supplier_1.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("supermercado").get(0));
            supplierRepository.save(supplier_1);
            coffeeProducts.clear();

            Supplier supplier_2 = new Supplier();
            supplier_2.setAddress("Ruta 3");
            supplier_2.setName("Diarco");
            supplier_2.setCoords(new float[]{-143.44f,310.22f});
            supplier_2.setCuil("4343432322");
            supplier_2.setQualificationOfUsers(2.3f);
            generalProducts = createAndReturnGeneralProducts(supplier_2,231);
            supplier_2.setProducts(generalProducts);
            supplier_2.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("mayorista").get(0));
            supplierRepository.save(supplier_2);
            generalProducts.clear();

            Supplier supplier_3 = new Supplier();
            supplier_3.setAddress("Av Irigoyen");
            supplier_3.setName("La Anónima");
            supplier_3.setCoords(new float[]{-543.44f,610.22f});
            supplier_3.setCuil("321213321");
            supplier_3.setQualificationOfUsers(4.6f);
            coffeeProducts = createAndReturnCoffeeProducts(supplier_3, 80);
            supplier_3.setProducts(coffeeProducts);
            supplier_3.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("supermercado").get(0));
            supplierRepository.save(supplier_3);

            Supplier supplier_4 = new Supplier();
            supplier_4.setAddress("Calle 22");
            supplier_4.setName("La Casita");
            supplier_4.setCoords(new float[]{-546.44f,10.22f});
            supplier_4.setCuil("21211121");
            supplier_4.setQualificationOfUsers(4.5f);
            generalProducts = createAndReturnGeneralProducts(supplier_4, 4);
            supplier_4.setProducts(generalProducts);
            supplier_4.setType(supplierTypeRepository.findByNameIgnoreCaseContaining("hogar").get(0));
            supplierRepository.save(supplier_4);
            logger.info("Supplier table Cargada");
        } else {
            logger.info("Supplier Seeding no requerido, tabla con datos");
        }
    }

    private List<Product> createAndReturnGeneralProducts(Supplier aSupplier, int i) {
        List<Product> products = new ArrayList<>();
        Random r = new Random();
        float random_1 = i + r.nextFloat() * (20 - 10);
        float random_2 = i + r.nextFloat() * (20 - 10);
        float random_3 = i + r.nextFloat() * (20 - 10);
        float random_4 = i + r.nextFloat() * (20 - 10);

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Arroz en caja");
        prod_1.setWeight(5.0f);
        prod_1.setName("Arroz Luchetti");
        prod_1.setSupplier(aSupplier);
        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Almacen");
        if(type_1.isPresent())
            prod_1.setType(Arrays.asList(type_1.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("Desinfectante de cocina");
        prod_2.setWeight(3.0f);
        prod_2.setName("Odex");
        prod_2.setSupplier(aSupplier);
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Limpieza");
        if(type_2.isPresent())
            prod_2.setType(Arrays.asList(type_2.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("Cerveza artesanal");
        prod_3.setWeight(70.0f);
        prod_3.setName("Cerveza Patagonia");
        prod_3.setSupplier(aSupplier);
        Optional<ProductType> type_3 = productTypeRepository.findProductTypesByNameIgnoreCase("Comestibles");
        if(type_3.isPresent())
            prod_3.setType(Arrays.asList(type_3.get(), type_1.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("Jabon liquido para lavarropas");
        prod_4.setWeight(3.0f);
        prod_4.setName("Skip");
        prod_4.setSupplier(aSupplier);
        prod_4.setType(Arrays.asList(type_2.get()));
        products.add(prod_4);
        return products;
    }
    private List<Product> createAndReturnCoffeeProducts(Supplier aSupplier, int i) {
        Random r = new Random();
        float random_1 = i + r.nextFloat() * (100 - 10);
        float random_2 = i + r.nextFloat() * (100 - 10);
        float random_3 = i + r.nextFloat() * (100 - 10);
        float random_4 = i + r.nextFloat() * (100 - 10);
        float random_5 = i + r.nextFloat() * (100 - 10);
        List<Product> products = new ArrayList<>();

        Optional<ProductType> type_1 = productTypeRepository.findProductTypesByNameIgnoreCase("Almacen");
        Optional<ProductType> type_2 = productTypeRepository.findProductTypesByNameIgnoreCase("Comestibles");
        Optional<ProductType> type_3 = productTypeRepository.findProductTypesByNameIgnoreCase("Café");

        Product prod_1 = new Product();
        prod_1.setPrice(random_1);
        prod_1.setDescription("Café en grano");
        prod_1.setWeight(30.0f);
        prod_1.setName("Juan Valdez");
        prod_1.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_1.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("Café instantaneo");
        prod_2.setWeight(35.0f);
        prod_2.setName("Nescafe");
        prod_2.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_2.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("Cafe molido");
        prod_3.setWeight(30.0f);
        prod_3.setName("Starbucks Pike Place");
        prod_3.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_3.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("Cafe en cápsulas");
        prod_4.setWeight(21.0f);
        prod_4.setName("Nespresso");
        prod_4.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_4.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_4);

        Product prod_5 = new Product();
        prod_5.setPrice(random_5);
        prod_5.setDescription("Cafe en saquitos");
        prod_5.setWeight(15.0f);
        prod_5.setName("Bonafide");
        prod_5.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_5.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_5);
        return products;
    }

    public void seedClientTable() {
        String sql = "SELECT * FROM user";
        List<Client> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {

            Client client_1 = new Client();
            client_1.setName("John Doe");
            client_1.setDateOfRegister(new GregorianCalendar(2019, 2, 11).getTime());
            client_1.setEmail("john@doe.com");
            client_1.setScore(10);
            client_1.setDateOfBirth(new GregorianCalendar(1900, 1, 8).getTime());
            client_1.setUsername("johnnydoe");
            client_1.setPassword("johndoe01");

            Address address_1 = new Address();
            address_1.setDescription("Domicilio particular");
            address_1.setName("Calle Muzio");
            address_1.setCoords(new float[]{-103.44f,102.22f});
            address_1.setApartment("C");
            address_1.setAddress("Muzio 26");
            address_1.setClient(client_1);

            Address address_2 = new Address();
            address_2.setDescription("Oficina");
            address_2.setName("Empresa");
            address_2.setCoords(new float[]{-122.44f,12.22f});
            address_2.setApartment("Ninguno");
            address_2.setAddress("Belgrano 521");
            address_2.setClient(client_1);

            client_1.setAddresses(Arrays.asList(address_1,address_2));
            clientRepository.save(client_1);

            Client client_2 = new Client();
            client_2.setName("Mauro");
            client_2.setDateOfRegister(new GregorianCalendar(2011, 4, 21).getTime());
            client_2.setEmail("mauro@pedidosya.com");
            client_2.setScore(8);
            client_2.setDateOfBirth(new GregorianCalendar(1989, 1, 8).getTime());
            client_2.setUsername("mauropedidosya");
            client_2.setPassword("mauropedidosyapass");

            Address address_3 = new Address();
            address_3.setDescription("Empresa");
            address_3.setName("Edificio");
            address_3.setCoords(new float[]{-123.44f,112.22f});
            address_3.setApartment("D");
            address_3.setAddress("Av. Fontana 231");
            address_3.setClient(client_2);
            client_2.setAddresses(Arrays.asList(address_3));
            clientRepository.save(client_2);

            Client client_3 = new Client();
            client_3.setName("Marty");
            client_3.setDateOfRegister(new GregorianCalendar(1985, 4, 21).getTime());
            client_3.setEmail("marty@bttf.com");
            client_3.setScore(5);
            client_3.setDateOfBirth(new GregorianCalendar(2014, 3, 5).getTime());
            client_3.setUsername("martybttf");
            client_3.setPassword("martybttf");

            Address address_4 = new Address();
            address_4.setDescription("Departamento a la calle");
            address_4.setName("Depto");
            address_4.setCoords(new float[]{-132.54f,142.22f});
            address_4.setApartment("A");
            address_4.setAddress("Marconi 531");
            address_4.setClient(client_3);
            client_3.setAddresses(Arrays.asList(address_4));

            clientRepository.save(client_3);

            DeliveryMan deliveryMan_1 = new DeliveryMan();
            deliveryMan_1.setFree(true);
            deliveryMan_1.setName("pizza guy 1");
            deliveryMan_1.setNumberOfSuccessOrders(2);
            deliveryMan_1.setEmail("pizza@guy1.com");
            deliveryMan_1.setUsername("pizzadude1");
            deliveryMan_1.setPassword("pizzapassword");
            deliveryMan_1.setActive(true);
            deliveryMan_1.setScore(0);
            deliveryMan_1.setDateOfBirth(new GregorianCalendar(2014, 3, 5).getTime());
            deliveryMan_1.setOrdersPending(null);
            deliveryManRepository.save(deliveryMan_1);

            DeliveryMan deliveryMan_2 = new DeliveryMan();
            deliveryMan_2.setFree(true);
            deliveryMan_2.setName("pizza guy 2");
            deliveryMan_2.setNumberOfSuccessOrders(2);
            deliveryMan_2.setEmail("pizza@guy2.com");
            deliveryMan_2.setUsername("pizzadude2");
            deliveryMan_2.setPassword("pizza2password");
            deliveryMan_2.setActive(true);
            deliveryMan_2.setScore(0);
            deliveryMan_2.setDateOfBirth(new GregorianCalendar(2018, 3, 5).getTime());
            deliveryMan_2.setOrdersPending(null);
            deliveryManRepository.save(deliveryMan_2);

            logger.info("Client table Cargada");
        } else {
            logger.info("Client Seeding no requerido, tabla con datos");
        }
    }

    private void seedOrderTable() throws Exception {
        String sql = "SELECT * FROM orders";
        List<Order> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {




            Order order_1 = new Order();
            order_1.setComments("Casa de rejas negras");
            order_1.setNumber(1);
            order_1.setStatus(new Pending(order_1,new Date() ) );

          /*  order_1.setStatusByName();*/
            order_1.setDateOfOrder(new Date());

            Iterable<Product> products_cafe = productRepository.findByNameIgnoreCaseContaining("Cafe");
            Iterable<Product> products_almacen = productRepository.findByNameIgnoreCaseContaining("Arroz");

            Item item_1 = new Item();
            item_1.setDescription("Cafe");
            item_1.setQuantity(2);
            item_1.setOrder(order_1);
            item_1.setProduct(products_cafe.iterator().next());

            Item item_2 = new Item();
            item_2.setDescription("Cafe");
            item_2.setQuantity(2);
            item_2.setOrder(order_1);
            item_2.setProduct(products_almacen.iterator().next());

            order_1.setItems(Arrays.asList(item_1, item_2));
            List<Client> clients = clientRepository.findByUsername("johnnydoe");
            if(!clients.isEmpty()) {
                Client client = clients.get(0);
                order_1.setClient(client);
               // order_1.setAddress(client.getAddresses().get(0));
            }
            orderRepository.save(order_1);
            // para probar asignar
            Optional <Order> order = orderRepository.findById(1L);
            Order order_3 = order.get();
            Optional <DeliveryMan> deliverys = deliveryManRepository.findById(4L);
            DeliveryMan deliv = deliverys.get();
            order_3.getStatus().assign(deliv);
            orderRepository.save(order_3);

            // para probar cancel
/*            Optional <Order> order4 = orderRepository.findById(1L);
            Order order_4 = order4.get();
            order_4.getStatus().cancel();
            orderRepository.save(order_4);

            // para probar  refuse
            Optional <Order> order5 = orderRepository.findById(1L);
            Order order_5 = order5.get();
            order_5.getStatus().refuse();
            orderRepository.save(order_5);*/
/*
            // para probar entrega
            Optional <Order> order5 = orderRepository.findById(1L);
            Order order_5 = order5.get();
            order_5.getStatus().deliver();
            orderRepository.save(order_5);

            //una vez entregado se finaliza y califica
            Optional <Order> order6 = orderRepository.findById(1L);
            Order order_6 = order6.get();
            order_6.getStatus().finish(2, "muy bien");
            orderRepository.save(order_6);

*/
            logger.info("Order table Cargada");
        } else {
            logger.info("Order Seeding no requerido, tabla con datos");
        }
    }
}
/*
    private void seedHistoricalProductPriceTable() {
        String sql = "SELECT * FROM historical_product_price";
        List<HistoricalProductPrice> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {
            Date startDate = new GregorianCalendar(2014, 2, 11).getTime();
            Date endDate = new GregorianCalendar(2014, 3, 11).getTime();
            HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice();
            historicalProductPrice.setPrice(10f);
            historicalProductPrice.setStartDate(startDate);
            historicalProductPrice.setFinishDate(endDate);
            List<Product> products = productRepository.findByNameIgnoreCaseContaining("Café");
            historicalProductPrice.setProduct(products.get(0));
            historicalProductPriceRepository.save(historicalProductPrice);
            logger.info("HistoricalProductPrice table Cargada");
        } else {
            logger.info("HistoricalProductPrice Seeding no requerido, tabla con datos");
        }
*/