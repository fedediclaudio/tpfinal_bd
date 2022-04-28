package com.bd.tpfinal.services;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import com.bd.tpfinal.utils.DeliveryException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DeliveryServiceImpl implements DeliveryService {

    @Autowired
    private DeliveryManRepository deliveryManRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private SupplierTypeRepository supplierTypeRepository;

    @Autowired
    private ProductTypeRepository productTypeRepository;


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
        }
        this.clientRepository.save(realClient);
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
    public Product editProduct(Long number, Product product) throws DeliveryException {
        Product actual = this.productRepository.findById(number).orElse(null);
        if (actual != null) {
            if (actual.getPrice() != product.getPrice()) {
                this.addHistoricalProductPrice(actual, product.getPrice());
            }
            actual.update(product);
            throw new DeliveryException("El producto con ese número no existe");
        } else {
        }
        return actual;
    }

    @Override
    @Transactional
    public Product createProduct(Map<String, Object> data) {
        ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        List<Long> productTypes = Stream.of(data.get("productTypes").toString().replaceAll( "(\\[|\\]|\\s)", "").split(",")).map(c->Long.parseLong(c)).collect(Collectors.toList());
        Optional<Supplier> supplier = this.supplierRepository.findById(Long.parseLong(data.get("supplier_id").toString()));
        Product product = mapper.convertValue(data, Product.class);
        product.setSupplier(supplier.get());
        return this.productRepository.save(product);
    }

    @Override
    @Transactional
    public ProductType createProductType(ProductType newProductType) {
        return this.productTypeRepository.save(newProductType);
    }

    @Override
    @Transactional
    public void desactiveDeliveryMan(String username) {
        DeliveryMan deliveryMan = this.getDeliveryManInfo(username);
        if (deliveryMan != null && deliveryMan.isActive()) {
            deliveryMan.setActive(false);
        }
        this.deliveryManRepository.save(deliveryMan);
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryMan getDeliveryManInfo(String username) {
        return this.deliveryManRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional
    public Order newOrderPending(Order order) {
        this.orderRepository.save(order);
        return order;
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderInfo(Long number) {
        Order order = this.orderRepository.findOrderByNumber(number).orElse(null);
        return order;
    }

    @Override
    @Transactional
    public DeliveryMan confirmOrder(Long number) throws DeliveryException {
        DeliveryMan deliveryMan = this.deliveryManRepository.findByFreeTrueAndActiveTrue().stream().findAny().orElse(null);
        if (deliveryMan != null) {
            try {
                Order order = this.getOrderInfo(number);
                order.getOrderStatus().assign(deliveryMan);
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

/*    @Override
    @Transactional
    public List<Order> getAssignedOrders(String username) {
        DeliveryMan dm = this.getDeliveryManInfo(username);
        dm.getActualOrders().size(); // Inicializar lista LAZY
        return dm.getActualOrders();
    }
*/
    @Override
    @Transactional
    public void deliverOrder(Long number) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.getOrderStatus().deliver();
        this.orderRepository.save(order); // Tambien guardamos el DeliveryMan y el Client, debido a las oper en cadena
    }

    @Override
    @Transactional
    public void refuseOrder(Long number) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.getOrderStatus().refuse();
        this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(Long number) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.getOrderStatus().cancel();
        this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void finishOrder(Long number) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.getOrderStatus().finish();
        this.orderRepository.save(order);
    }

    @Override
    @Transactional
    public void qualifyOrder(Long number, Qualification qualification) throws DeliveryException {
        Order order = this.getOrderInfo(number);
        order.setQualification(qualification);
        Supplier supplier = order.getItemProductSupplier();
        Long count = this.orderRepository.countBySupplierId(supplier.getId());
        supplier.updateScore(qualification, count);
        this.orderRepository.save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public Address getAddressWithID(Long id) {
        Optional<Address> address = this.addressRepository.findAddressById(id);
        return address.orElse(null);
    }

    @Override
    @Transactional
    public Address createAddress(Address newAddress) {
        return this.addressRepository.save(newAddress);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductBySupplierId(Long id) {
        List<Product> products = this.productRepository.findProductBySupplierId(id);
        ListIterator<Product> iterator = products.listIterator();
        while (iterator.hasNext()) {
            Product product = iterator.next();
            product.getProductType().size();
        }
        return products;
    }
    
    @Override
    @Transactional(readOnly = true)
    public SupplierType getSupplierTypeById(Long id) {
        return this.supplierTypeRepository.findSupplierTypeById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductType getProductTypeById(Long id) {
        return this.productTypeRepository.findProductTypeById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProductById(Long id) {
        return this.productRepository.findProductById(id).orElse(null);
    }

    @Override
    @Transactional
    public SupplierType createSupplierType(SupplierType newSupplierType) {
        return this.supplierTypeRepository.save(newSupplierType);
    }

    @Override
    @Transactional
    public ProductType newProductType(ProductType aProductType) {
        return this.productTypeRepository.save(aProductType);
    }

    @Override
    @Transactional(readOnly = true)
    public Supplier getSupplier(Long id) {
        return this.supplierRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Supplier createSupplier(Map<String, Object> data) {
        ObjectMapper mapper = new ObjectMapper().disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        Optional<SupplierType> type = this.supplierTypeRepository.findById(Long.parseLong(data.get("type").toString()));
        Supplier newSupplier = mapper.convertValue(data, Supplier.class);
        newSupplier.setSupplierType(type.get());
        return this.supplierRepository.save(newSupplier);
    }

    @Override
    @Transactional
    public Item createItem(Item newItem) {
        return this.itemRepository.save(newItem);
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
    public void deleteProduct(Long id) throws DeliveryException {
        try {
            this.productRepository.deleteById(id);
            this.historicalProductPriceRepository.deleteByProductId(id);
        } catch (Exception e) {
            throw new DeliveryException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public List<Item> getItemsByOrderNumber(Long number) {
        Optional<Order> order = this.orderRepository.findOrderByNumber(number);
        List<Item> items = order.isPresent() ? order.get().getItems() : null;
        return items;
    }

    @Override
    @Transactional(readOnly = true)
    public Item getItemWithID(Long id) {
        Optional<Item> item = this.itemRepository.findById(id);
        return item.orElse(null);
    }
}
