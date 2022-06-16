package com.bd.tpfinal.services;


import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.OrderStatus;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.Qualification;
import com.bd.tpfinal.model.Supplier;
import com.bd.tpfinal.model.orderStatusTypes.Pending;
import com.bd.tpfinal.model.projections.OrderMaxPrice;
import com.bd.tpfinal.repositories.implementations.AddressRepository;
import com.bd.tpfinal.repositories.implementations.ClientRepository;
import com.bd.tpfinal.repositories.implementations.DeliveryManRepository;
import com.bd.tpfinal.repositories.implementations.ItemRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;
import com.bd.tpfinal.repositories.implementations.ProductRepository;
import com.bd.tpfinal.repositories.implementations.SupplierRepository;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired AddressRepository addressRepository;
    @Autowired ClientRepository clientRepository;
    @Autowired DeliveryManRepository deliveryManRepository;
    @Autowired ItemRepository itemRepository;
    @Autowired OrderRepository orderRepository;
    @Autowired OrderStatusService orderStatusService;
    @Autowired ProductRepository productRepository;
    @Autowired SupplierRepository supplierRepository;
    @Autowired MongoTemplate mongoTemplate;

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
        Client client = clientRepository.getClientById(idClient);

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

        // Por defecto la orden esta en Pendiente
        OrderStatus status = new Pending(order);
        order.setStatus(status);
        // Actualizo la orden
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
        Address address = addressRepository.findById(idAddress).orElse(null);
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

        // Verifico si el estado actual de la orden permite agregar items
        if (!order.getStatus().canAddItem()) {
            System.out.println("No se puede agregar items a la orden");
            return false;
        }

        // Obtengo el producto de la BD
        Product product = productRepository.getProductById(idProduct);
        // Si el producto no existe, retorno false
        if (product == null) {
            System.out.println("El producto no existe");
            return false;
        }
        if (product.isProductDeleted()) {
            System.out.println("El producto ya no esta a la disponible para la venta");
            return false;
        }

        if (!order.getItems().isEmpty()) {
        	Supplier cargado = order.getItems().get(0).getProduct().getSupplier();
        	// Si el Supplier del producto cargado no es el mismo que el nuevo producto
        	if (!product.getSupplier().getId().equalsIgnoreCase(cargado.getId())) {
        		System.out.println("Solo se admite un Supplier por orden");
                return false;
        	}
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
        // Obtengo la orden de la BD
        Order order = orderRepository.getOrderByNumber(orderNumber);

        // Si la orden no existe, retorno false
        if (order == null) {
            System.out.println("La orden no existe");
            return false;
        }

        // Si no se puede cancelar, retorno falso
        if (!order.getStatus().canCancel()) {
            System.out.println("No se puede cancelar la orden");
            return false;
        }

        // Intento cancelar la orden
        if (!order.getStatus().cancel()) {
            System.out.println("No se pudo cancelar la orden");
            return false;
        }

        orderRepository.save(order);

        return true;
    }

    /**
     * Elije un DeliveryMan, buscando el que tenga menos pedidos en su lista
     *
     * @param deliveryManList
     * @return un DeliveryMan con la menor cantidad de pedidos en espera
     */
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
        
        // Grabo el DeliveryMan
        deliveryMan.addPendingOrder(order);
        deliveryManRepository.save(deliveryMan);

        return true;
    }

    private float getQualificationSumForSupplier(String idSupplier) {
    	List<Order> orders = orderRepository.findByItems_Product_Supplier_Id(idSupplier);
    	float sum = 0;
    	return (orders.stream().reduce(sum, (partialQualification, order) -> partialQualification + order.getQualification().getScore(), Float::sum)) / orders.size();
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

        // Actualizo el Supplier
        Supplier supplier = order.getItems().get(0).getProduct().getSupplier();
        supplier.setQualificationOfUsers( this.getQualificationSumForSupplier( supplier.getId() ) );
        supplierRepository.save(supplier);
        
        return true;
    }

    public List<Order> getOrdersFromSupplier(String idSupplier) throws Exception {
        return orderRepository.findByItems_Product_Supplier_Id(idSupplier);
    }

    public OrderMaxPrice getHighestPriceOrderOfDate(LocalDate date) throws Exception {
        return orderRepository.findTopByDateOfOrderOrderByTotalPriceDesc(date);
    }

    @Override
    public Map<Order, Integer> getOrdersFromSupplierWithMaxProducts(String idSupplier) throws Exception {
    	List<Order> orders = orderRepository.findByItems_Product_Supplier_Id(idSupplier);
    	
    	Map<Order, Integer> map = new HashMap<Order, Integer>();
        orders.forEach( order -> {
            map.put(order, order.getItems().size());
        });
        
        return sortByValue(map);
    }
    
    
    private Map<Order, Integer> sortByValue(Map<Order, Integer> hm) {
        // Create a list from elements of HashMap
        List<Map.Entry<Order, Integer>> list = new LinkedList<Map.Entry<Order, Integer>>(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<Order, Integer>>() {
            public int compare(Map.Entry<Order, Integer> o1,
                               Map.Entry<Order, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        
        // put data from sorted list to hashmap
        Map<Order, Integer> temp = new LinkedHashMap<Order, Integer>();
        for (Map.Entry<Order, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        
        // Return the top 10
        return temp;
    }
}
