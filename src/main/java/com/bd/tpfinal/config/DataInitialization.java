package com.bd.tpfinal.config;


import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class DataInitialization implements ApplicationRunner {
    private final OrderRepository orderRepository;
    private final SupplierRepository supplierRepository;
    private final ClientRepository clientRepository;
    private final SupplierTypeRepository supplierTypeRepository;
    private final ProductTypeRepository productTypeRepository;
    private final ProductRepository productRepository;
    private final HistoricalProductPriceRepository historicalProductPriceRepository;
    private final ItemRepository itemRepository;

    private List<Client> clients = new ArrayList<>();
    private List<Order> orders = new ArrayList<>();
    private List<Supplier> suppliers = new ArrayList<>();
    private List<ProductType> productTypes = new ArrayList<>();
    private List<Product> products = new ArrayList<>();

    private Random random = new Random();
    private final DeliveryManRepository deliveryManRepository;

    public DataInitialization(OrderRepository orderRepository,
                              SupplierRepository supplierRepository,
                              ClientRepository clientRepository,
                              SupplierTypeRepository supplierTypeRepository,
                              ProductTypeRepository productTypeRepository,
                              ProductRepository productRepository,
                              HistoricalProductPriceRepository historicalProductPriceRepository,
                              ItemRepository itemRepository, DeliveryManRepository deliveryManRepository) {
        this.orderRepository = orderRepository;
        this.supplierRepository = supplierRepository;
        this.clientRepository = clientRepository;
        this.supplierTypeRepository = supplierTypeRepository;
        this.productTypeRepository = productTypeRepository;
        this.productRepository = productRepository;
        this.historicalProductPriceRepository = historicalProductPriceRepository;
        this.itemRepository = itemRepository;
        this.deliveryManRepository = deliveryManRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (orderRepository.count() == 0) {
            generateClients();
            generateSuppliers();
            generateProducts();
            generateOrders();
            addItemsToOrders();
            generateSupplierWithAllProductTypes();
            generateDeliveryMen();
        }
    }

    private void generateOrders(){
        if (clients.size() == 0)
            clients = clientRepository.findAll();
        for (int i = 0; i < 100; i++) {
            Order order = new Order();
            order.setNumber(random.nextInt(100000));
            order.setDateOfOrder(new Date());
//            float price = (float) Math.random() * 10000;
            order.setTotalPrice(0f);
            Pending status = new Pending();
            status.setName();
            status.setOrder(order);
            order.setStatus(status);

            Client client = clients.get(random.nextInt(clients.size()));
            client.getOrders().add(order);
            order.setClient(client);

            orders.add(orderRepository.save(order));
        }
    }

    private void generateClients(){
        for (int i = 0; i < 100; i++) {
            Client client = new Client();
            client.setName(Datasets.LASTNAMES[random.nextInt(100)] + " " + Datasets.Names[random.nextInt(100)]);
            client.setDateOfRegister(new Date());
            client.setDateOfBirth(new Date());
            client.setUsername(client.getName().replace(" ", "").toLowerCase(Locale.ROOT));
            client.setPassword("");
            clients.add(clientRepository.save(client));
        }
    }


    private void generateSuppliers(){
        List<SupplierType> supplierTypes = new ArrayList<>();
        for(String type : Datasets.SUPPLIER_TYPES){
            SupplierType supplierType = new SupplierType();
            supplierType.setName(type);
            supplierType = supplierTypeRepository.save(supplierType);
            supplierTypes.add(supplierType);
        }



        for (String company : Datasets.COMPANY_NAMES) {
            Supplier supplier = new Supplier();
            supplier.setName(company);
            supplier.setCuil(30 + "-" + (20000000 + random.nextInt(20000000)) + "-" + random.nextInt(9));

            SupplierType supplierType = supplierTypes.get(random.nextInt(supplierTypes.size()));

            supplier.setType(supplierType);
            supplierType.getSuppliers().add(supplier);
            supplier = supplierRepository.save(supplier);

            suppliers.add(supplier);
        }
    }

    public void generateProducts(){
        for (String type: Datasets.PRODUCT_TYPES){
            ProductType productType = new ProductType();
            productType.setName(type);
            productType = productTypeRepository.save(productType);
            productTypes.add(productType);
        }

        int supLength = suppliers.size();
        int npLength = Datasets.PRODUCT_NAME_PARTS.length;

        for (int i = 0; i< 200; i++){
            float price = (float)Math.random() * random.nextInt(1000);
            String name = Datasets.PRODUCT_NAME_PARTS[random.nextInt(npLength)] + Datasets.PRODUCT_NAME_PARTS[random.nextInt(npLength)] +
                    Datasets.PRODUCT_NAME_PARTS[random.nextInt(npLength)];
            int pti = random.nextInt(productTypes.size());
            ProductType type = productTypes.get(pti);

            Product product = new Product();
            product.setName(name);
            product.setType(type);
            product.setPrice(price);

            HistoricalProductPrice historicalPrice = new HistoricalProductPrice();
            historicalPrice.setPrice(price);
            historicalPrice.setStartDate(new Date());
            historicalPrice.setProduct(product);

            product.getPrices().add(historicalPrice);

            Supplier supplier = suppliers.get(random.nextInt(supLength));
            product.setSupplier(supplier);
            supplier.getProducts().add(product);

            type.getProducts().add(product);

            product = productRepository.save(product);
            supplierRepository.save(supplier);
            products.add(product);
        }

        suppliers.stream().map(s -> supplierRepository.save(s));
    }

    public void addItemsToOrders(){
        for (Order order: orders) {
            int j = random.nextInt(15) + 1;

            for(int i = 0; i < j; i++){
                Item item = new Item();
                item.setQuantity(random.nextInt(10)+1);
                item.setOrder(order);
                item.setProduct(products.get(random.nextInt(products.size())));
                order.setTotalPrice(order.getTotalPrice() + (item.getQuantity() * item.getProduct().getPrice()));
                order.getItems().add(item);
            }
            orderRepository.save(order);

        }
    }

    public void generateSupplierWithAllProductTypes(){

        Supplier supplier = new Supplier();
        supplier.setName("Company with all product types");
        supplier.setCuil(30 + "-" + (20000000 + random.nextInt(20000000)) + "-" + random.nextInt(9));

        SupplierType supplierType = supplierTypeRepository.findById(1l).get();
        supplier.setType(supplierType);
        supplier = supplierRepository.save(supplier);
        suppliers.add(supplier);

        int supLength = suppliers.size();
        int npLength = Datasets.PRODUCT_NAME_PARTS.length;

        productTypes = productTypeRepository.findAll();

        for (ProductType type: productTypes){

            float price = (float)Math.random() * random.nextInt(1000);
            String name = Datasets.PRODUCT_NAME_PARTS[random.nextInt(npLength)] + Datasets.PRODUCT_NAME_PARTS[random.nextInt(npLength)] +
                    Datasets.PRODUCT_NAME_PARTS[random.nextInt(npLength)];
            int pti = random.nextInt(productTypes.size());

            Product product = new Product();
            product.setName(name);
            product.setType(type);
            product.setPrice(price);

            HistoricalProductPrice historicalPrice = new HistoricalProductPrice();
            historicalPrice.setPrice(price);
            historicalPrice.setStartDate(new Date());
            historicalPrice.setProduct(product);

            product.getPrices().add(historicalPrice);


            product.setSupplier(supplier);
            supplier.getProducts().add(product);

            product = productRepository.save(product);
            supplierRepository.save(supplier);
            products.add(product);
        }

        supplierRepository.save(supplier);
    }

    public void generateDeliveryMen(){
        for (int i = 0; i < 10; i++){
            DeliveryMan deliveryMan = new DeliveryMan();
            deliveryMan.setName(Datasets.LASTNAMES[random.nextInt(Datasets.LASTNAMES.length)] + " " + Datasets.Names[random.nextInt(Datasets.Names.length)]);
            deliveryMan.setDateOfAdmission(new Date());
            deliveryMan.setEmail(deliveryMan.getName().toLowerCase(Locale.ROOT).replace(" ","")+"@gmail.com");
            deliveryMan.setPassword(deliveryMan.getName().toLowerCase(Locale.ROOT).replace("a","4"));
            deliveryMan.setUsername(deliveryMan.getName().toLowerCase(Locale.ROOT).replace(" ","_"));
            deliveryMan.setNumberOfSuccessOrders(0);
            deliveryMan.setDateOfBirth(new Date());
            deliveryManRepository.save(deliveryMan);
        }
    }
}
