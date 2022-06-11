package com.bd.tpfinal.services;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import com.bd.tpfinal.utils.DeliveryException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.text.ParseException;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * Esta clase contiene la implementación de los servicios relacionados con el sistema.
 *
 * @author Grupo 8
 *
 */

@Service
@Transactional
public class DeliveryServiceImpl implements DeliveryService {

    /**
     * Es el repositorio ligado a los repartidores.
     */
    @Inject
    private DeliveryManRepository deliveryManRepository;

    /**
     * Es el repositorio ligado a los clientes.
     */
    @Inject
    private ClientRepository clientRepository;

    /**
     * Es el repositorio ligado a los domicilios.
     */
    @Inject
    private AddressRepository addressRepository;

    /**
     * Es el repositorio ligado a los items.
     */
    @Inject
    private ItemRepository itemRepository;

    /**
     * Es el repositorio ligado a las ordenes.
     */
    @Inject
    private OrderRepository orderRepository;

    /**
     * Es el repositorio ligado a los precios históricos.
     */
    @Inject
    private HistoricalProductPriceRepository historicalProductPriceRepository;

    /**
     * Es el repositorio ligado a los productos.
     */
    @Inject
    private ProductRepository productRepository;

    /**
     * Es el repositorio ligado a los proveedores.
     */
    @Inject
    private SupplierRepository supplierRepository;

    /**
     * Es el repositorio ligado a los tipos de proveedores.
     */
    @Inject
    private SupplierTypeRepository supplierTypeRepository;

    /**
     * Es el repositorio ligado a los tipos de productos.
     */
    @Inject
    private ProductTypeRepository productTypeRepository;


    @Transactional
    public ProductTypeRepository getProductTypeRepository() {
        return this.productTypeRepository;
    }

    @Transactional
    public ProductRepository getProductRepository() {
        return this.productRepository;
    }

    @Override
    @Transactional
    public Client newClient(Client client) {
        return this.clientRepository.save(client);
    }

    @Override
    @Transactional
    public Client editClient(String username, Client client) throws DeliveryException {
        Client realClient = this.getClientInfo(username);
        realClient = (Client) this.editUser(realClient, client);
        return this.clientRepository.save(realClient);
    }

    @Override
    @Transactional
    public void desactiveClient(String username) {
        Client realClient = this.getClientInfo(username);
        if (realClient != null && realClient.isActive()) {
            realClient.setActive(false);
            this.clientRepository.save(realClient);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Client getClientInfo(String username) {
        return this.clientRepository.findByUsername(username).orElse(null);
    }

    /*    @Override
        @Transactional(readOnly = true)
        public List<Order> getClientOrders(String username) {
            Client client = this.getClientInfo(username);
            return (client != null) ? client.getOrders() : null;
        }
    */
    @Override
    @Transactional
    public DeliveryMan newDeliveryMan(DeliveryMan deliveryMan) {
        return this.deliveryManRepository.save(deliveryMan);
    }

    @Override
    @Transactional
    public DeliveryMan editDeliveryMan(String username, DeliveryMan deliveryMan) throws DeliveryException {
        DeliveryMan realDeliveryMan = this.getDeliveryManInfo(username);
        realDeliveryMan = (DeliveryMan) this.editUser(realDeliveryMan, deliveryMan);
        return this.deliveryManRepository.save(realDeliveryMan);
    }

    private User editUser(User realUser, User user) throws DeliveryException {
        if (user != null) {
            realUser.setName(user.getName());
            realUser.setEmail(user.getEmail());
            realUser.setDateOfBirth(user.getDateOfBirth());
            realUser.setPassword(user.getPassword());
        } else {
            throw new DeliveryException("The client with username does not exist");
        }
        return realUser;
    }
    public void addHistoricalProductPrice(Product product, float price) throws DeliveryException {
        Date now = new Date();
        HistoricalProductPrice actualPrice = this.historicalProductPriceRepository.findFirstByProductIdOrderByStartDateDesc(product.getId()).orElse(null);
        if (actualPrice != null) {
            HistoricalProductPrice newPrice = new HistoricalProductPrice(product, price, now);
            actualPrice.setFinishDate(now);
            this.historicalProductPriceRepository.save(actualPrice);
            this.historicalProductPriceRepository.save(newPrice);
        } else {
            throw new DeliveryException("No se encontró el precio histórico");
        }
    }

    @Override
    @Transactional
    public Product editProduct(ObjectId number, Product product) throws DeliveryException {
        Product actual = this.productRepository.findById(number).orElse(null);
        if (actual != null) {
            if (actual.getPrice() != product.getPrice()) {
                this.addHistoricalProductPrice(actual, product.getPrice());
            }
            actual.update(product);
        } else {
            throw new DeliveryException("El producto con ese número no existe");
        }
        return actual;
    }

    @Override
    @Transactional
    public void desactiveDeliveryMan(String username) {
        DeliveryMan deliveryMan = this.getDeliveryManInfo(username);
        if (deliveryMan != null && deliveryMan.isActive()) {
            deliveryMan.setActive(false);
            this.deliveryManRepository.save(deliveryMan);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryMan getDeliveryManInfo(String username) {
        return this.deliveryManRepository.findByUsername(username).orElse(null);
    }
    @Override
    @Transactional(readOnly = true)
    public List<DeliveryMan> getBestDeliveryMan(Integer size) {
        Pageable paging = PageRequest.of(0, size);
        return this.deliveryManRepository.findByOrderByScoreDesc(paging).getContent();
    }
    @Override
    @Transactional
    public Order newOrderPending(Map<String, Object> data) throws DeliveryException {
        ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Optional<Address> address = this.addressRepository.findById(new ObjectId(data.get("client_address").toString()));
        if (address.isPresent()) {
            Order newOrder = mapper.convertValue(data, Order.class);
            newOrder.setAddress(address.get());
            newOrder.setClient(address.get().getClient());
            Optional<Supplier> supplier = this.supplierRepository.findById(new ObjectId (data.get("id_supplier").toString()));
            supplier.ifPresent(newOrder::setSupplier);
            newOrder.setOrderStatus(new Pending());
            newOrder.setDateOfOrder(new Date());
/*          SE PONE EN NULL MANUALMENTE PARA EVITAR UNA INJECCION */
            newOrder.setDeliveryMan(null);
/* ------ */
            List<Item> items = newOrder.getItems();
            newOrder.setItems(null);
            this.orderRepository.save(newOrder);
            for (Item item : items) {
                this.productRepository.findById(item.getProduct().getId()).ifPresent(item::setProduct);
                item.setOrder(newOrder);
                this.itemRepository.save(item);
            }
            newOrder.setItems(items);
            return this.orderRepository.save(newOrder);
        } else
            throw new DeliveryException("No se definió una dirección de entrega.");
    }
    @Override
    @Transactional(readOnly = true)
    public Order getOrderInfo(ObjectId number) {
        return this.orderRepository.findByNumber(number).orElse(null);
    }

    @Override
    @Transactional
    public DeliveryMan confirmOrder(ObjectId number) throws DeliveryException {
        Integer i = (new Random()).nextInt(this.deliveryManRepository.countByFreeTrueAndActiveTrue());
        Pageable paging = PageRequest.of(i, 1);
        List<DeliveryMan> deliveryManList = this.deliveryManRepository.findByFreeTrueAndActiveTrue(paging).getContent();
        if (deliveryManList.size() > 0) {
            try {
                DeliveryMan deliveryMan = deliveryManList.get(0);
                Order order = this.getOrderInfo(number);
                order.getOrderStatus().assign(deliveryMan, order);
                this.deliveryManRepository.save(deliveryMan);
                this.orderRepository.save(order);
                return deliveryMan;
            } catch (Exception e) {
                throw new DeliveryException(e.getMessage());
            }
        } else {
            throw new DeliveryException("No hay repartidores disponibles");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersWithMaxItems(ObjectId supplier, int size) {
        Pageable paging = PageRequest.of(0, size, Sort.by("count(o)").descending());
        List<ObjectId> ids = this.orderRepository.findFirstsWithMaxItemsBySupplier(supplier, paging).getContent();
        List<Order> orders = (List<Order>) this.orderRepository.findAllById(ids);
        Collections.sort(orders, Comparator.comparing(item -> ids.indexOf(item.getNumber())));
        return orders;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getBestFirstsDispatchersSupplier(Integer quantity) {
        Pageable paging = PageRequest.of(0, quantity);
        Page<ObjectId> page = this.supplierRepository.findBestDispatchersSupplierIds(paging);
        return (List<Supplier>) this.supplierRepository.findAllById(page.getContent());
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderMaxPriceInDate(String stringDate) throws DeliveryException{
        try {
            Date start = new SimpleDateFormat("yyyy-MM-dd").parse(stringDate);
            Calendar c = Calendar.getInstance();
            c.setTime(start);
            c.add(Calendar.DATE, 1);
            c.add(Calendar.SECOND, -1);
            return this.orderRepository.findTopByDateOfOrderBetweenOrderByTotalPriceDesc(start, c.getTime()).orElse(null);
        } catch (ParseException exception) {
            throw new DeliveryException("Error en la fecha: " + exception.getMessage());
        }
    }
    @Override
    @Transactional
    public void deliverOrder(ObjectId number) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.getOrderStatus().deliver(order);
        this.orderRepository.save(order); // Tambien guardamos el DeliveryMan y el Client, debido a las oper en cadena
    }

    @Override
    @Transactional
    public void refuseOrder(ObjectId number) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.getOrderStatus().refuse(order);
        this.orderRepository.save(order);
        this.confirmOrder(number);
    }

    @Override
    @Transactional
    public void cancelOrder(ObjectId number) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.getOrderStatus().cancel(order);
        this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void finishOrder(ObjectId number) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.getOrderStatus().finish(order);
        this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void qualifyOrder(ObjectId number, Qualification qualification) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.setQualification(qualification);
        Supplier supplier = order.getSupplier();
        Long count = this.orderRepository.countBySupplierId(supplier.getId());
        supplier.updateScore(qualification, count);
        this.orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Address getAddress(ObjectId id) {
        return this.addressRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Address createAddress(Address newAddress) {
        return this.addressRepository.save(newAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductBySupplier(ObjectId id) {
        List<Product> products = this.productRepository.findBySupplierId(id);
        for (Product product : products) {
            product.getProductType().size();
        }
        return products;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        List<Product> products = this.productRepository.findAll();
        for (Product product : products) {
            product.getProductType().size();
        }
        return products;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        List<Order> orders  = this.orderRepository.findAll();
        for (Order order : orders) {
            order.getItems().size();
        }
        return orders;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getSupplierByType(ObjectId id) {
        List<Supplier> suppliers = this.supplierRepository.findBySupplierTypeId(id);
        return suppliers;
    }
    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getAllSuppliers() {
        List<Supplier> suppliers = this.supplierRepository.findAll();
        for (Supplier supplier : suppliers) {
            supplier.getProducts().size();
        }
        return suppliers;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductByProductTypeId(ObjectId id) {
        List<Product> products = this.productRepository.findByProductTypeId(id);
        for (Product product : products) {
            product.getProductType().size();
        }
        return products;
    }
    @Override
    @Transactional(readOnly = true)
    public List<ProductType> getAllProductTypes() {
        List<ProductType> productTypes = this.productTypeRepository.findAll();
        for (ProductType productType : productTypes) {
            productType.getName();
        }
        return productTypes;
    }
    @Override
    @Transactional(readOnly = true)
    public Supplier getSupplierById(ObjectId id) {
        return this.supplierRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierType getSupplierTypeById(ObjectId id) {
        return this.supplierTypeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductType getProductTypeById(ObjectId id) {
        return this.productTypeRepository.findProductTypeById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductType> getProductTypeFindAll() {
        return this.productTypeRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(ObjectId id) {
        return this.productRepository.findProductById(id).orElse(null);
    }

    @Override
    @Transactional
    public SupplierType createSupplierType(SupplierType newSupplierType) {
        return this.supplierTypeRepository.save(newSupplierType);
    }
    @Override
    @Transactional
    public ProductType createProductType(ProductType newProductType) {
        return this.productTypeRepository.save(newProductType);
    }

    @Override
    @Transactional
    public Supplier createSupplier(Supplier newSupplier) {
        return this.supplierRepository.save(newSupplier);
    }

    @Override
    @Transactional
    public Product createProduct(Product newProduct) {
        this.productRepository.save(newProduct);
        HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice(newProduct, newProduct.getPrice(), new Date());
        this.historicalProductPriceRepository.save(historicalProductPrice);
        return newProduct;
    }

    @Override
    @Transactional
    public Item createItem(Item newItem) throws DeliveryException {
        Order order = this.orderRepository.findByNumber(newItem.getOrder().getNumber()).orElse(null);
        if (order == null) {
            throw new DeliveryException("No existe la orden donde agregar este item");
        } else {
            OrderStatus status = order.getOrderStatus();
            if (status.canAddItem()) {
                return this.itemRepository.save(newItem);
            } else {
                throw new DeliveryException("No se puede agregar items en el estado "+status.getName());
            }
        }
    }


    @Override
    @Transactional
    public Object deleteItem(Item item) {
        try {
            this.itemRepository.delete(item);
        } catch (Exception e) {
            return e;
        }
        return true;
    }

    @Override
    @Transactional
    public void deleteProduct(ObjectId id) throws DeliveryException {
        try {
            this.productRepository.deleteById(id);
            this.historicalProductPriceRepository.deleteByProductId(id);
        } catch (Exception e) {
            throw new DeliveryException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Item> getItemsByOrderNumber(ObjectId number) {
        Optional<Order> order = this.orderRepository.findByNumber(number);
        return order.map(Order::getItems).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItemWithID(ObjectId id) {
        Optional<Item> item = this.itemRepository.findById(id);
        return item.orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<HistoricalProductPrice> getHistoricalProductPriceByProductId(ObjectId id) {
        List<HistoricalProductPrice> prices = this.historicalProductPriceRepository.findByProductId(id);
        return prices;

    }

    @Override
    @Transactional
    public List<HistoricalProductPrice> getHistoricalProductPriceBetweenDates(ObjectId id, String startDateStr, String finishDateStr) throws DeliveryException {
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            Date startDate = df.parse(startDateStr);
            Date finishDate = df.parse(finishDateStr);
            if ( startDate.after(finishDate)) {
                throw new DeliveryException("Error en las fechas enviadas: la fecha de finalización debe ser mayor que la de inicio");
            }
            c.setTime(finishDate);
            c.add(Calendar.DATE, 1);
            c.add(Calendar.SECOND, -1);
            return this.historicalProductPriceRepository.findAllByStartDateGreaterThanEqualAndFinishDateLessThanEqualAndProductId(startDate, finishDate, id);
        } catch (ParseException exception) {
            throw new DeliveryException("Error en las fechas enviadas: " + exception.getMessage());
        }
    }
    @Override
    @Transactional
    public List<ArrayList> getAverageProductTypePrices() throws DeliveryException {
//        List<ArrayList> tiposConPromedio = this.productRepository.findAllAveragePriceGroupByProductType();
//        System.out.println("tiposConPromedio:" + tiposConPromedio);
//        ArrayList<ArrayList> resultado = new ArrayList<ArrayList>();
//        for (ArrayList tipoConPromedio : tiposConPromedio) {
//            ArrayList<Object> elemento = new ArrayList<>();
//            elemento.add(this.productTypeRepository.findById((ObjectId) tipoConPromedio.get(0)).orElse(null));
//            elemento.add(new DecimalFormat("#.##").format(tipoConPromedio.get(1)));
//            resultado.add(elemento);
//        }
//        return resultado;

        return this.productRepository.findAllAveragePriceGroupByProductType();
    }

    @Override
    @Transactional
    public float getAverageProductTypePrice(ObjectId id) throws DeliveryException {
        List<Product> products = this.productRepository.findByProductTypeId(id);
        float productCount = (float) products.size();
        float sum = 0;
        for (Product product : products) {
            product.getProductType().size();
            float price = product.getPrice();
            sum = sum+price;
        }
        return sum / productCount;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getSupplierProvidingAllProductType() {
        List<Supplier> suppliersProvidingAllProductTypes = new ArrayList<>();
        List<ProductType> allProductTypes = this.getAllProductTypes();
        List<Supplier> suppliers = this.getAllSuppliers();
        Long numberOfProductTypes = allProductTypes.stream().count();
        for (Supplier supplier : suppliers) {
            List<Product> products = this.getProductBySupplier(supplier.getId());
            Set<List<ProductType>> productTypesInThisSupplier = products.
                    stream()
                    .map(product -> product.getProductType())
                    .collect(Collectors.toSet());
            if (allProductTypes.size()==productTypesInThisSupplier.size()){
                suppliersProvidingAllProductTypes.add(supplier);}
              }
        return suppliersProvidingAllProductTypes;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Supplier> getSupplierByQualificationValue(Float stars) {
        List<ObjectId> ids = this.supplierRepository.findByScoreLessThanEqual(stars);
        return (List<Supplier>) this.supplierRepository.findAllById(ids);
    }
}
