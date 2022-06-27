package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.FinishOrderScore;
import com.bd.tpfinal.DTOs.ItemDTO;
import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.ItemRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private DeliveryManService deliveryManService;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public Optional<Order> findOrderById(long order_id) {
        return Optional.empty();
    }

    @Override
    public Optional<Item> agregarItemAOrdenCreada(String order_id, ItemDTO item) throws Exception {
        Optional<Order> order = orderRepository.findById(order_id);
        Optional<Product> product = productRepository.findById(item.getIdProduct());
        Product productActual = product.get();
        if (!productActual.isActive()) {
            throw new Exception("El producto seleccionado no está disponible");
        }
        Item it = new Item();
        if (order.isPresent()) {
            if (order.get().getStatus().getName().equals("Pending")) {
                Order ordenActual = order.get();
                it.setDescription(item.getDescription());
                it.setQuantity(item.getQuantity());
                it.setProduct(productActual);
                it.setOrder(ordenActual);
                itemRepository.save(it);
                ordenActual.getItems().add(it);
                float total = ((productActual.getPrice()) * it.getQuantity()) + ordenActual.getTotalPrice();
                ordenActual.setTotalPrice(total);
                orderRepository.save(ordenActual);
            } else {
                throw new Exception("No se puede agregar el item " + it.getDescription() + " a la orden " + order_id + " la misma ya fue asignada");
            }
        } else {
            throw new Exception("La orden no existe");
        }
        return Optional.ofNullable(it);
    }

    @Override
    public List<Order> getOrdersConMasProductosDeSupplier(long supplier_id) {
        return null;
    }

    @Override
    public Optional<Order> getOrderConMayorPrecioDelDia(LocalDate fecha) {
        return Optional.empty();
    }

    @Override
    public Optional<Order> confirmarPedido(String orderId) throws Exception {
        Optional<DeliveryMan> deliveryMan = deliveryManService.getFreeAndActiveDeliveryMan();
        if(deliveryMan.isPresent()) { // si no hay repartidor libre, no se puede confirmar pedido
            DeliveryMan currentDeliveryMan = deliveryMan.get();
            Optional<Order> order = orderRepository.findById(orderId);
            if(order.isPresent()) {
                Order orderActual = order.get();
                if (orderActual.getStatus().canAssign()){ // verifica si la orden puede ser confirmada
                    orderActual.getStatus().assign(currentDeliveryMan);
                    orderActual.setDeliveryMan(currentDeliveryMan);
                    orderRepository.save(orderActual);
                    deliveryManService.guardarDeliveryMan(currentDeliveryMan);
                    return order;
                }
            }
        }
        return null;
    }

    @Override
    public Optional<Order> cancelarPedido(String orderId) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Order> rechazarPedido(String orderId) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Order> entregarPedido(String orderId) throws Exception {
        return Optional.empty();
    }

    @Override
    public Optional<Order> finalizarPedido(FinishOrderScore score, String orderId) throws Exception {
        return Optional.empty();
    }

    @Override
    public void guardarOrder(Order order) {

    }

    @Override
    public List<Order> getOrdenesConMasProductosDelSupplier(long supplier_id) {
        return null;
    }

    @Override
    public Optional<Order> getOrderConMayorPrecioTotalDelDia(LocalDate fecha) {
        return Optional.empty();
    }

    @Override
    public List<Order> getOrderByOrderStatus(String status) {
        return null;
    }

    @Override
    public void updateOrdersTotalPrice(String status) {
   /*     List<Order> orderList = new ArrayList<Order>();
        Iterable<Order> orders = orderRepository.findAll();
        orders.forEach(orderList::add);
        int i = 0;
        while (!orderList.isEmpty()) {
            Order order = orderList.get(i);
            if (!order.getStatus().getName().equals("Pending")) {
                int j = 0;
                List <Item> items = itemRepository.findByOrderId(order.getId());
                float total =0;
                while (!items.isEmpty()) {
                    Item it = items.get(j);
                    if (it.getProduct().isActive())
                        total = total + (it.getProduct().getPrice() * it.getQuantity());
                    items.remove(j);
                }
                order.setTotalPrice(total);
                orderRepository.save(order);
                orderList.remove(i);
            }
        }*/
    }
}
