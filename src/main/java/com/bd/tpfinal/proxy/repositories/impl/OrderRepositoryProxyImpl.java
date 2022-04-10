package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.ItemDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.mappers.client.ClientMapper;
import com.bd.tpfinal.mappers.item.ItemMapper;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.proxy.repositories.OrderRepositoryProxy;
import com.bd.tpfinal.repositories.ClientRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.ProductRepository;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepositoryProxyImpl implements OrderRepositoryProxy {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    private final ClientMapper clientMapper;
    private final ItemMapper itemMapper;

    public OrderRepositoryProxyImpl(OrderMapper orderMapper,
                                    OrderRepository orderRepository,
                                    ClientRepository clientRepository,
                                    ProductRepository productRepository, ClientMapper clientMapper,
                                    ItemMapper itemMapper) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.clientMapper = clientMapper;
        this.itemMapper = itemMapper;
    }

    @Override
    public OrderDto findById(String id) throws PersistenceEntityException {
        Optional<Order> optionalOrder = orderRepository.findById(Long.parseLong(id));
        if (!optionalOrder.isPresent())
            throw new PersistenceEntityException("Order with id "+ id + " not found");
        return createOrderDto(optionalOrder.get(), optionalOrder.get().getClient());
    }

    @Override
    public OrderDto findByNumber(int number) throws PersistenceEntityException {
        Optional<Order> optionalOrder = orderRepository.findOneByNumber(number);
        if (!optionalOrder.isPresent())
            throw new PersistenceEntityException("Order with number " + number + " not found");
        return createOrderDto(optionalOrder.get(), optionalOrder.get().getClient());
    }

    @Override
    public List<OrderDto> findByStatusName(String status) {
        List<Order> orders = orderRepository.findByStatus_Name(status);
        return orders.parallelStream().map(order -> {
            return createOrderDto(order, order.getClient());
        }).collect(Collectors.toList());
    }

    @Override
    public OrderDto save(OrderDto orderDto) throws PersistenceEntityException {
        Optional<Order> orderOp = orderRepository.findById(Long.parseLong(orderDto.getId()));
        Order order = orderOp.orElseThrow(() -> new PersistenceEntityException("Order with id " + orderDto.getId() + " not found."));
        order.setComments(orderDto.getComments());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setNumber(orderDto.getNumber());
        order.getQualification().setScore(orderDto.getQualificationScore());
        order.getQualification().setCommentary(orderDto.getQualificationComments());
        return createOrderDto(order, order.getClient());
    }

    @Override
    public OrderDto create(String clientId, OrderDto orderDto) throws PersistenceEntityException {
        Optional<Client> optionalClient = clientRepository.findById(Long.parseLong(clientId));
        if (!optionalClient.isPresent())
            throw new PersistenceEntityException("Client with id " + clientId + " not found");

        Order order = new Order();
        order.setDateOfOrder(new Date());
        order.setStatus(new Pending());
        Client client = optionalClient.get();
        order.setClient(client);

        Address address = client.getAddresses().stream()
                .filter(ad -> ad.getId().compareTo(Long.parseLong(orderDto.getAddress().getId())) == 0)
                .findFirst().orElseThrow(() -> new PersistenceEntityException("Address with id " + orderDto.getAddress().getId() + " not found."));

        order.setAddress(address);
        order = orderRepository.save(order);
        client.getOrders().add(order);
        client = clientRepository.save(client);

        return createOrderDto(order, client);
    }

    @Override
    public List<OrderDto> findAll() {
        List<Order> orders = orderRepository.findAll();
        return orders.parallelStream().map(order -> {
            return createOrderDto(order, order.getClient());
        }).collect(Collectors.toList());
    }

    @Override
    public OrderDto findByIdWithItems(String orderId) throws PersistenceEntityException {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new PersistenceEntityException("Order with id "+ orderId + " not found"));
        return createOrderDto(order, order.getClient(), order.getItems());
    }

    @Override
    public boolean exists(String orderId) {
        if (!orderRepository.existsById(Long.parseLong(orderId)))
            new PersistenceEntityException("Order with id " + orderId + " not found.");
        return true;
    }

    @Override
    public OrderDto addItem(ItemDto itemDto) throws PersistenceEntityException {
        long orderId = Long.parseLong(itemDto.getOrderId());
        long productId = Long.parseLong(itemDto.getProductId());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new PersistenceEntityException("Order with id "+ orderId + " not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new PersistenceEntityException("Product with id "+ productId + " not found"));

        Item item = new Item();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(item.getQuantity());
        item.setDescription(product.getName());
        order.getItems().add(item);
        order = orderRepository.save(order);

        return createOrderDto(order, order.getClient(), order.getItems());
    }

    @Override
    public OrderDto findMaxTotalPriceBetweenDates(Date from, Date to) {
        String strFrom = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(from);
        String strTo = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(to);
        Order order = orderRepository.findMaxTotalPriceBetweenDates(strFrom, strTo);
        if (order == null)
            return new OrderDto();
        return orderMapper.toOrderDto(order);
    }

    @Override
    public OrderDto qualifyOrder(String orderId, Float qualification, String qualificationMessage) throws PersistenceEntityException {
        Order order = orderRepository.findById(Long.parseLong(orderId))
                .orElseThrow(() -> new PersistenceEntityException("Order with id " + orderId + " not found."));
        Qualification qualif = new Qualification();
        qualif.setScore(qualification);
        qualif.setCommentary(qualificationMessage);
        order.setQualification(qualif);
        qualif.setOrder(order);
        orderRepository.save(order);
        return orderMapper.toOrderDto(order);
    }

    @Override
    public OrderDto getOrdersWithMaximumProductsBySupplier(String supplierId) throws PersistenceEntityException {
        Order order = orderRepository.findOrderWithMaxProductsBySupplier(Long.parseLong(supplierId));
        return createOrderDto(order, order.getClient(), order.getItems());
    }

    private OrderDto createOrderDto(Order order, Client client){
        OrderDto dto = orderMapper.toOrderDto(order);
        if (client != null)
            dto.setClient(clientMapper.toClientDto(client));
        return dto;
    }

    private OrderDto createOrderDto(Order order, Client client, List<Item> items){
        OrderDto dto = this.createOrderDto(order, client);
        dto.setItems(order.getItems().stream().map(itemMapper::toItemDto).collect(Collectors.toList()));
        return dto;
    }
}
