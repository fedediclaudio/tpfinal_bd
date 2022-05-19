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
/*
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
    private AddressRepository addressRepository;

    private Logger logger = Logger.getLogger(String.valueOf(DatabaseSeeder.class));

    @EventListener
    public void seedTables(ContextRefreshedEvent event){
        // seedProductTypeTable();
        //seedItemTable();
       // seedSupplierTypeTable();
        //seedSupplierTable();
        // seedHistoricalProductPriceTable();
        //seedClientTable();
        // seedAddressTable();
       // seedOrderTable();
    }

    private void seedProductTypeTable() {
        String sql = "SELECT name, description FROM product_type";
        List<ProductType> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {
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

            productTypeRepository.saveAll(Arrays.asList(productType_1,productType_2, productType_3, productType_4, productType_5, productType_6));
            logger.info("ProductType table Cargada");
        } else {
            logger.info("ProductType Seeding no requerido, tabla con datos");
        }
    }

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
    }

    private void seedItemTable() {
        String sql = "SELECT quantity, description FROM item";
        List<Item> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {
            Item item = new Item();
            item.setQuantity(10);
            item.setDescription("Yerba Mate");
            Iterable<Product> productos = productRepository.findAll();
            item.setProduct(productos.iterator().next());
            itemRepository.save(item);
            logger.info("Item table Cargada");
        } else {
            logger.info("Item Seeding no requerido, tabla con datos");
        }
    }

    private void seedSupplierTypeTable(){
        String sql = "SELECT * FROM supplier_type";
        List<SupplierType> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {
            SupplierType supplierType_1 = new SupplierType();
            supplierType_1.setDescription("Gran variedad de productos");
            supplierType_1.setName("Almacen grande");
            supplierTypeRepository.saveAll(Arrays.asList(supplierType_1));
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
            supplierRepository.save(supplier_3);

            Supplier supplier_4 = new Supplier();
            supplier_4.setAddress("Calle 2");
            supplier_4.setName("Vea");
            supplier_4.setCoords(new float[]{-546.44f,10.22f});
            supplier_4.setCuil("32131321231");
            supplier_4.setQualificationOfUsers(4.1f);
            generalProducts = createAndReturnGeneralProducts(supplier_4, 4);
            supplier_4.setProducts(generalProducts);
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
        prod_1.setId(5L + i);
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
        prod_2.setId(6L + i);
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
        prod_3.setId(7L + i);
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
        prod_4.setId(8L + i);
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
        prod_1.setId(0L + i);
        prod_1.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_1.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_1);

        Product prod_2 = new Product();
        prod_2.setPrice(random_2);
        prod_2.setDescription("Café instantaneo");
        prod_2.setWeight(35.0f);
        prod_2.setName("Nescafe");
        prod_2.setId(1L + i);
        prod_2.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_2.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_2);

        Product prod_3 = new Product();
        prod_3.setPrice(random_3);
        prod_3.setDescription("Cafe molido");
        prod_3.setWeight(30.0f);
        prod_3.setName("Starbucks Pike Place");
        prod_3.setId(2L + i);
        prod_3.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_3.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_3);

        Product prod_4 = new Product();
        prod_4.setPrice(random_4);
        prod_4.setDescription("Cafe en cápsulas");
        prod_4.setWeight(21.0f);
        prod_4.setName("Nespresso");
        prod_4.setId(3L + i);
        prod_4.setSupplier(aSupplier);
        if(type_1.isPresent() && type_2.isPresent() && type_3.isPresent())
            prod_4.setType(Arrays.asList(type_1.get(), type_2.get(), type_3.get()));
        products.add(prod_4);

        Product prod_5 = new Product();
        prod_5.setPrice(random_5);
        prod_5.setDescription("Cafe en saquitos");
        prod_5.setWeight(15.0f);
        prod_5.setName("Bonafide");
        prod_5.setId(4L + i);
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
            Client client = new Client();
            client.setName("John Doe");
            client.setDateOfRegister(new GregorianCalendar(2019, 2, 11).getTime());
            client.setEmail("john@doe.com");
            client.setScore(10);
            client.setDateOfBirth(new GregorianCalendar(1989, 1, 8).getTime());
            client.setUsername("johnnydoe");
            client.setPassword("johndoe01");
            clientRepository.save(client);
            logger.info("Client table Cargada");
        } else {
            logger.info("Client Seeding no requerido, tabla con datos");
        }
    }

    public void seedAddressTable() {
        String sql = "SELECT * FROM address";
        List<Address> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {
            Address address = new Address();
            address.setDescription("Domicilio particular");
            address.setName("Calle Muzio");
            address.setCoords(new float[]{-103.44f,102.22f});
            address.setApartment("Ninguno");
            address.setAddress("Muzio 26");
            List<Client> clients = clientRepository.findByUsername("johnnydoe");
            if(!clients.isEmpty()) {
                Client client = clients.get(0);
                address.setClient(client);
            }
            addressRepository.save(address);
            logger.info("Address table Cargada");
        } else {
            logger.info("Address Seeding no requerido, tabla con datos");
        }
    }

    private void seedOrderTable() {
        String sql = "SELECT * FROM orders";
        List<Order> u = jdbcTemplate.query(sql, (resultSet, rowNum) -> null);
        if(u == null || u.size() <= 0) {
            Order order = new Order();
            order.setComments("Casa de rejas negras");
            order.setNumber(10);
            order.setStatus(new Pending());
            order.setDateOfOrder(new Date());
            List<Item> listaItems = new ArrayList<>();
            Item item_1 = new Item();
            item_1.setId(1L);
            item_1.setDescription("Yerba");
            item_1.setQuantity(20);
            item_1.setOrder(order);
            Iterable<Product> products = productRepository.findByNameIgnoreCaseContaining("Yerba Playadito");
            item_1.setProduct(products.iterator().next());
            order.setItems(Arrays.asList(item_1));

            Iterable<Item> items = itemRepository.findAll();
            items.iterator().forEachRemaining(listaItems::add);
            order.setItems(listaItems);

            Iterable<Address> address = addressRepository.findAll();
            if(address.iterator().next() != null) {
                order.setAddress(address.iterator().next());
            }
            Qualification qualification = new Qualification();
            qualification.setOrder(order);
            qualification.setScore(8);
            qualification.setCommentary("Llegó en tiempo y forma");
            order.setQualification(qualification);
            List<Client> clients = clientRepository.findByUsername("johnnydoe");
            if(!clients.isEmpty()) {
                Client client = clients.get(0);
                order.setClient(client);
            }
            orderRepository.save(order);
            logger.info("Order table Cargada");
        } else {
            logger.info("Order Seeding no requerido, tabla con datos");
        }
    }
}/*/
