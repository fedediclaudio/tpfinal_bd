package com.bd.tpfinal.proxy.repositories.impl;

import com.bd.tpfinal.dtos.common.ChangeOrderStatusDto;
import com.bd.tpfinal.dtos.common.ItemDto;
import com.bd.tpfinal.dtos.common.OrderDto;
import com.bd.tpfinal.enums.OrderStatusAction;
import com.bd.tpfinal.exceptions.general.ActionNotAllowedException;
import com.bd.tpfinal.exceptions.persistence.EntityNotFoundException;
import com.bd.tpfinal.exceptions.persistence.PersistenceEntityException;
import com.bd.tpfinal.helpers.IdConvertionHelper;
import com.bd.tpfinal.mappers.client.ClientMapper;
import com.bd.tpfinal.mappers.item.ItemMapper;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.model.*;
import com.bd.tpfinal.proxy.repositories.OrderRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.command.*;
import com.bd.tpfinal.repositories.*;


import java.util.*;
import java.util.stream.Collectors;


public class OrderRepositoryProxyImpl implements OrderRepositoryProxy {

    private final OrderRepository orderRepository;

    private final ClientRepository clientRepository;

    private final ProductRepository productRepository;
    private final DeliveryManRepository deliveryManRepository;
    private final SupplierRepository supplierRepository;

    private final OrderMapper orderMapper;

    private final ClientMapper clientMapper;
    private final ItemMapper itemMapper;

    private final Map<OrderStatusAction, ChangeStatusCommand> changeStatusMap = new HashMap<>();

    public OrderRepositoryProxyImpl(OrderMapper orderMapper,
                                    OrderRepository orderRepository,
                                    ClientRepository clientRepository,
                                    ProductRepository productRepository,
                                    DeliveryManRepository deliveryManRepository,
                                    SupplierRepository supplierRepository, ClientMapper clientMapper,
                                    ItemMapper itemMapper) {
        this.orderMapper = orderMapper;
        this.orderRepository = orderRepository;
        this.clientRepository = clientRepository;
        this.productRepository = productRepository;
        this.deliveryManRepository = deliveryManRepository;
        this.supplierRepository = supplierRepository;
        this.clientMapper = clientMapper;
        this.itemMapper = itemMapper;


        changeStatusMap.put(OrderStatusAction.FINISH, new FinishCommand(orderRepository, deliveryManRepository, clientRepository, orderMapper, supplierRepository));
        changeStatusMap.put(OrderStatusAction.CANCEL, new CancelCommand(orderRepository, deliveryManRepository, clientRepository, orderMapper));
        changeStatusMap.put(OrderStatusAction.DELIVER, new DeliverCommand(orderRepository, deliveryManRepository, orderMapper));
        changeStatusMap.put(OrderStatusAction.ASSIGN, new AssignCommand(orderRepository, deliveryManRepository, orderMapper));
        changeStatusMap.put(OrderStatusAction.REFUSE, new RefuseCommand(orderRepository, deliveryManRepository, orderMapper));

    }

    @Override
    public OrderDto findById(String id) {
        Optional<Order> optionalOrder = orderRepository.findById(IdConvertionHelper.convert(id));
        if (!optionalOrder.isPresent())
            throw new EntityNotFoundException("Order with id "+ id + " not found");
        return createOrderDto(optionalOrder.get(), optionalOrder.get().getClient());
    }

    @Override
    public OrderDto findByNumber(int number) {
        Optional<Order> optionalOrder = orderRepository.findOneByNumber(number);
        if (!optionalOrder.isPresent())
            throw new EntityNotFoundException("Order with number " + number + " not found");
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

    public OrderDto save(OrderDto orderDto) {
        Optional<Order> orderOp = orderRepository.findById(IdConvertionHelper.convert(orderDto.getId()));
        Order order = orderOp.orElseThrow(() -> new EntityNotFoundException("Order with id " + orderDto.getId() + " not found."));
        order.setComments(orderDto.getComments());
        order.setTotalPrice(orderDto.getTotalPrice());
        order.setNumber(orderDto.getNumber());
        order.getQualification().setScore(orderDto.getQualificationScore());
        order.getQualification().setCommentary(orderDto.getQualificationComments());
        return createOrderDto(order, order.getClient());
    }

    @Override

    public OrderDto create(String clientId, OrderDto orderDto) {
        Optional<Client> optionalClient = clientRepository.findById(IdConvertionHelper.convert(clientId));
        if (!optionalClient.isPresent())
            throw new EntityNotFoundException("Client with id " + clientId + " not found");

        Order order = new Order();
        order.setDateOfOrder(new Date());
        order.setStatus(new Pending());
        Random random = new Random();
        order.setNumber(random.nextInt(1000000)+9999999);
        Client client = optionalClient.get();
        order.setClient(client);

        Address address = client.getAddresses().stream()
                .filter(ad -> ad.getId().compareTo(IdConvertionHelper.convert(orderDto.getAddress().getId())) == 0)
                .findFirst().orElseThrow(() -> new EntityNotFoundException("Address with id " + orderDto.getAddress().getId() + " not found."));

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

    public OrderDto addItem(ItemDto itemDto) {
        String orderId = IdConvertionHelper.convert(itemDto.getOrderId());
        String productId = IdConvertionHelper.convert(itemDto.getProductId());

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id "+ orderId + " not found"));

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product with id "+ productId + " not found"));

        Item item = new Item();
        item.setOrder(order);
        item.setProduct(product);
        item.setQuantity(itemDto.getQuantity());
        item.setDescription(product.getName());
        order.getItems().add(item);
        order.setTotalPrice(order.getTotalPrice() + (item.getQuantity() * item.getProduct().getPrice()));
        order = orderRepository.save(order);

        return createOrderDto(order, order.getClient(), order.getItems());
    }

    @Override
    public OrderDto findMaxTotalPriceBetweenDates(Date from, Date to) {
        Order order = orderRepository.findTopByDateOfOrderBetweenOrderByTotalPriceDesc(from, to);
        if (order == null)
            return OrderDto.builder().build();
        return createOrderDto(order, order.getClient(), order.getItems());
    }

    @Override

    public OrderDto qualifyOrder(String orderId, Float qualification, String qualificationMessage) throws ActionNotAllowedException {
        Order order = orderRepository.findById(IdConvertionHelper.convert(orderId))
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderId + " not found."));
        if (!"DELIVERED".equals(order.getStatus().getName()))
            throw new ActionNotAllowedException("Can't qualify order. The order status isn't DELIVERED.");
        Qualification qualif = new Qualification();
        qualif.setScore(qualification);
        qualif.setCommentary(qualificationMessage);
        order.setQualification(qualif);
        qualif.setOrder(order);
        orderRepository.save(order);

        Set<Supplier> suppliers = order.getItems().stream().map(i -> i.getProduct().getSupplier()).collect(Collectors.toSet());

        suppliers.forEach(supplier -> {
            Supplier saveSupplier = supplierRepository.findById(supplier.getId()).get();
            if (supplier != null) {
                Set<Order> ordersForSupplier = orderRepository.findByStatus_nameAndQualificationIsNotNullAndItems_product_supplier("DELIVERED", supplier);
                OptionalDouble average = ordersForSupplier.stream().mapToDouble(o -> o.getQualification().getScore()).average();
                if (average.isPresent()) {
                    saveSupplier.setQualificationOfUsers(((Double)average.getAsDouble()).floatValue());
                    supplierRepository.save(saveSupplier);
                }
            }
        });

        return orderMapper.toOrderDto(order);
    }

    @Override
    public OrderDto getOrdersWithMaximumProductsBySupplier(String supplierId) {
        List<Order> orders = orderRepository.findByItems_Product_Supplier_id(IdConvertionHelper.convert(supplierId));
        Map<Long, Order> map = new HashMap<>();
        orders.forEach( order -> {
            long count =  order.getItems().stream().map(i -> i.getProduct().getSupplier().getId()).filter(f -> f.equals(supplierId)).count();
            map.put(count, order);
        });
        Order order = map.get(map.keySet().stream().mapToLong(v -> v).max().getAsLong());
        return createOrderDto(order, order.getClient(), order.getItems());
    }

    @Override
    public OrderDto changeOrderStatus(ChangeOrderStatusDto request) throws PersistenceEntityException {
        Order order = orderRepository.findById(IdConvertionHelper.convert(request.getOrderId()))
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + request.getOrderId() + " not found."));

        ChangeStatusCommand changeStatusCommand = changeStatusMap.get(request.getStatus());
        OrderDto orderDto = changeStatusCommand.execute(request);

        return orderDto;
    }

    @Override
    public OrderDto update(String orderId, String addressId) {
        Order order = orderRepository.findById(IdConvertionHelper.convert(orderId))
                .orElseThrow(() -> new EntityNotFoundException("Can't find order with id: " + orderId));
        Address address = order.getClient()
                .getAddresses()
                .stream()
                .filter(ad -> ad.getId().compareTo(IdConvertionHelper.convert(addressId)) == 0)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Address with id " + addressId + " does not exists"));
        order.setAddress(address);
        order = orderRepository.save(order);
        return orderMapper.toOrderDto(order);
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
