package com.bd.tpfinal.services;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.*;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class OrderServiceImpl implements OrderService {
    private final AddressRepository addressRepository;
    private final ClientRepository clientRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusService orderStatusService;
    private final ProductRepository productRepository;
    private final MongoTemplate mongoTemplate;


    @Autowired
    public OrderServiceImpl(final AddressRepository addressRepository,
                            final ClientRepository clientRepository,
                            final DeliveryManRepository deliveryManRepository,
                            final ItemRepository itemRepository,
                            final OrderRepository orderRepository,
                            final OrderStatusService orderStatusService,
                            final ProductRepository productRepository,
                            final MongoTemplate mongoTemplate) {
        this.addressRepository = addressRepository;
        this.clientRepository = clientRepository;
        this.deliveryManRepository = deliveryManRepository;
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
        this.orderStatusService = orderStatusService;
        this.productRepository = productRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public Client saveClient(Client client) throws Exception {
        return clientRepository.save(client);
    }

    public Order getOrder(String orderNumber) throws Exception {
        // Obtengo la orden de la BD
        Order order = orderRepository.getOrderByNumber(orderNumber);
        // Si la orden no existe, retorno false
        if (order == null) {
            System.out.println("La orden no existe");
        }
        return order;
    }

    public Order createOrder(String idClient) throws Exception {
        // Obtengo el cliente de la BD
        Client client = clientRepository
                .findById(idClient)
                .orElse(null);

        // Si el cliente no existe, retorno null
        if (client == null) {
            System.out.println("El cliente no existe");
            return null;
        }

        // Verifico que el cliente este activo
        if (!client.isActive()) {
            System.out.println("El cliente no está activo");
            return null;
        }

        // Creo la nueva orden
        Order order = new Order(client);

        // Grabo la orden
        order = orderRepository.save(order);

        // Agrego la orden a la lista de ordenes del Cliente
        client.addOrder(order);

        // Guardo al cliente
        saveClient(client);

        return order;
    }

    public boolean assignAddressToOrder(String orderNumber, String idAddress) throws Exception {
        // Obtengo la orden de la BD
        Order order = orderRepository.getOrderByNumber(orderNumber);
        // Si la orden no existe, retorno false
        if (order == null) {
            System.out.println("La orden no existe");
            return false;
        }

        // Verifico que se pueda cambiar la direccion (debe estar en Pendiente o Asignada)
        if (!order.getStatus().canChangeAddress()) {
            System.out.println("No se puede cambiar la direccion, la orden debe estar en Pendiente o Asignada");
            return false;
        }

        // Obtengo la direccion de la BD
        Address address = addressRepository.getAddressById(idAddress);
        if (address == null) {
            System.out.println("La direccion no existe");
            return false;
        }

        if (!order.getClient().hasAddress(address)) {
            System.out.println("La direccion no le pertenece a usuario");
            return false;
        }

        // Actualizo la direccion en la orden
        order.setAddress(address);

        // Grabo la orden
        order = orderRepository.save(order);

        return true;
    }

    public boolean addProductToOrder(String orderNumber, String idProduct, int quantity, String description) throws Exception {
        // Obtengo la orden de la BD
        Order order = orderRepository.getOrderByNumber(orderNumber);
        // Si la orden no existe, retorno false
        if (order == null) {
            System.out.println("La orden no existe");
            return false;
        }

        if (!order.getStatus().canAddItem()) {
            System.out.println("No se puede agregar items a la orden");
            return false;
        }

        Product product = productRepository
                .findById(idProduct)
                .orElse(null);

        if (product == null) {
            System.out.println("El producto no existe");
            return false;
        }
        if (product.isProductDeleted()) {
            System.out.println("El producto ya no esta a la disponible para la venta");
            return false;
        }

        // Creo el nuevo item
        Item item = new Item();
        item.setProduct(product);
        item.setQuantity(quantity);
        item.setDescription(description);
        item.setOrder(order);

        // Sumo el precio de los productos
        float price = order.getTotalPrice();
        price += product.getPrice() * quantity;
        order.setTotalPrice(price);

        // Agrego el item a la orden
        order.addItem(item);

        // Grabo la orden
        orderRepository.save(order);

        return true;
    }

    public boolean cancel(String orderNumber) throws Exception {

        Order order = orderRepository.getOrderByNumber(orderNumber);
        if (order == null) {
            System.out.println("La orden no existe");
            return false;
        }
        if (!order.getStatus().canCancel()) {
            System.out.println("No se puede cancelar la orden");
            return false;
        }
        if (!order.getStatus().cancel()) {
            System.out.println("No se pudo cancelar la orden");
            return false;
        }
        orderRepository.save(order);
        return true;
    }


    private DeliveryMan chooseDeliveryMan(List<DeliveryMan> deliveryManList) {
        return deliveryManList.stream()
                .min(
                        (dm1, dm2) -> dm1.getOrdersPending().size() - dm2.getOrdersPending().size()
                )
                .get();
    }

    public boolean confirmOrder(String orderNumber) throws Exception {
        // Obtengo la orden de la BD
        Order order = orderRepository.getOrderByNumber(orderNumber);

        // Si la orden no existe, retorno false
        if (order == null) {
            System.out.println("La orden no existe");
            return false;
        }

        // Si la orden no se puede aignar, retorno en falso
        if (!order.getStatus().canAssign()) {
            System.out.println("La orden no se puede asignar");
            return false;
        }

        // Obtengo la lista de DeliveryMan libres de la BD
        List<DeliveryMan> dmList = deliveryManRepository.getFreeDeliveryManList();

        // Si no hay ningun DeliveryMan libre, obtengo todos los DeliveryMan
        if (dmList.isEmpty())
            dmList = deliveryManRepository.findAll();

        // Elijo uno de los DeliveryMan de la lista como el repartidor de la orden
        DeliveryMan deliveryMan = chooseDeliveryMan(dmList);

        // Asigna el deliveryMan a la orden
        if (!order.getStatus().assign(deliveryMan)) {
            System.out.println("La orden no se pudo asignar");
            return false;
        }

        // Grabo la ordn
        orderRepository.save(order);

        return true;
    }

    public boolean setQualification(String orderNumber, int score, String comment) throws Exception {
        // Obtengo la orden de la BD
        Order order = orderRepository.getOrderByNumber(orderNumber);

        // Si la orden no existe, retorno false
        if (order == null) {
            System.out.println("La orden no existe");
            return false;
        }

        // Si la orden no se puede calificar, retorno en falso
        if (!order.getStatus().canRate()) {
            System.out.println("La orden no se puede calificar");
            return false;
        }

        if (order.getQualification() != null) {
            System.out.println("La orden ya fue calificada");
            return false;
        }

        if ((score < 0) || (score > 5)) {
            System.out.println("La calificacion debe ser entre 0 y 5");
            return false;
        }

        Qualification qualification = new Qualification();
        qualification.setCommentary(comment);
        qualification.setOrder(order);
        qualification.setScore(score);

        order.setQualification(qualification);

        // Grabo la ordn
        orderRepository.save(order);

        return true;
    }

    public List<Order> getOrdersFromSupplier(String idSupplier) throws Exception {
        return null;
    }

    public Order getHighestPriceOrderOfDate(LocalDate date) throws Exception {
        return null;
    }

    @Override
    public List<Order> getOrdersFromSupplierWithMaxProducts(String idSupplier) throws Exception {
//        Predicate predicate = null;

        List<Order> orders = (List<Order>) orderRepository.findAll(build(idSupplier));

//        List<Order> orders = mongoTemplate.find(query(where("order.items.product.supplier.id").is(idSupplier)), Order.class);


        return null;
    }

    public Predicate build(String idSupplier) {
        BooleanExpression contentQuery = null;
        contentQuery = addPredicate(contentQuery, QOrder.order.items.any().product().supplier().id.eq(idSupplier));
        return contentQuery;
    }

    private BooleanExpression addPredicate(final BooleanExpression original, final BooleanExpression append) {
        return isNull(original) ? append : original.and(append);
    }
}
