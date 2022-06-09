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
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository  productRepository;

    @Autowired

    private DeliveryManService deliveryManService;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Optional<Order> findOrderById(long order_id) {
        return orderRepository.findById(order_id);
    }

    @Override
    public Optional<Order> confirmarPedido(long order_id) throws Exception {
        Optional<DeliveryMan> deliveryMan = deliveryManService.getFreeAndActiveDeliveryMan();
        if(deliveryMan.isPresent()) {
            DeliveryMan currentDeliveryMan = deliveryMan.get();
            Optional<Order> order = orderRepository.findById(order_id);
            if(order.isPresent()) {
                Order orderActual = order.get();
                if (orderActual.getStatus().canAssign()){
                    orderActual.getStatus().assign(currentDeliveryMan);
                    orderActual.setDeliveryMan(currentDeliveryMan);
                    //currentDeliveryMan.setFree(false);
                    orderRepository.save(order.get());
                    deliveryManService.guardarDeliveryMan(currentDeliveryMan);
                    return order;
                }
            }
        }
        return null;
    }

    @Override
    public Optional<Order> cancelarPedido(long order_id) throws Exception {
            Optional<Order> order = orderRepository.findById(order_id);
            if(order.isPresent()) {
                Order orderActual = order.get();
                if (orderActual.getStatus().canCancel()) {
                    orderActual.getStatus().cancel();
                }
                orderRepository.save(order.get());
                return order;
            }
        return null;
    }


    @Override
    public Optional<Order> rechazarPedido(long order_id) throws Exception {
        Optional<Order> order = orderRepository.findById(order_id);
        if(order.isPresent()) {
            Order orderActual = order.get();
            if (orderActual.getStatus().canRefuse()) {
                orderActual.getStatus().refuse();
            }
            orderRepository.save(order.get());
            return order;
        }
        return null;
    }
    @Override
    public Optional<Order> entregarPedido(long order_id) throws Exception {
        Optional<Order> order = orderRepository.findById(order_id);
        if(order.isPresent()) {
            Order orderActual = order.get();
            if (orderActual.getStatus().canDeliver()) {
                orderActual.getStatus().deliver();
            }
            orderRepository.save(order.get());
            return order;
        }
        return null;
    }


    public Optional<Order> finalizarPedido(FinishOrderScore score, long order_id) throws Exception {
        Optional<Order> order = orderRepository.findById(order_id);
        if(order.isPresent()) {
            Order orderActual = order.get();
            if (orderActual.getStatus().canFinish()) {
                orderActual.getStatus().finish(score.getScore(), score.getCommentary());
            }
            orderRepository.save(order.get());
            return order;
        }
        return null;
    }

    @Override
    public Optional<Item> agregarItemAOrdenCreada(Long order_id, ItemDTO item) throws Exception{
        Optional<Order> order = orderRepository.findById(order_id);
        Optional<Product> product = productRepository.findById(item.getIdProduct());
        Item it = new Item();

        if(order.isPresent()) {
            Order ordenActual = order.get();
            Product productActual = product.get();

            it.setDescription(item.getDescription());
            it.setQuantity(item.getQuantity());
            it.setProduct(productActual);
            it.setOrder(ordenActual);
            ordenActual.getItems().add(it);
            float total = ((productActual.getPrice()) * it.getQuantity() ) + ordenActual.getTotalPrice();
            ordenActual.setTotalPrice(total);
            orderRepository.save(ordenActual);
        } else {
            throw new Exception("La orden con el id: " + order_id + " no existe");
        }
            return Optional.ofNullable(it);
    }

    @Override
    public List<Order> getOrdersConMasProductosDeSupplier(long supplier_id) {
        return orderRepository.getOrdersConMasProductosDeSupplier(supplier_id);
    }

    @Override
    public Optional<Order> getOrderConMayorPrecioDelDia(LocalDate fecha) {
        return orderRepository.getOrdenConMayorPrecioDelDia(fecha);
    }

    @Override
    public void guardarOrder(Order order) {
        orderRepository.save(order);
    }

    @Override
    public List<Order> getOrdenesConMasProductosDelSupplier(long supplier_id) {
        return orderRepository.findOrdersConMasProductosDeSupplier(supplier_id);
    }

    @Override
    public Optional<Order> getOrderConMayorPrecioTotalDelDia(LocalDate fecha) {
        return orderRepository.getOrdenConMayorPrecioTotalDelDia(fecha);
    }

    @Override
    public void removeItemFromOrderAndUpdatePrice(Item item) {
        Optional<Order> orderToUpdate = orderRepository.findOrderWithItemId(item.getId());

        if(orderToUpdate.isPresent()) {
            float total = orderToUpdate.get().getTotalPrice() - item.getProduct().getPrice();
            orderToUpdate.get().setTotalPrice(total);
            List<Item> updatedItems = orderToUpdate.get().getItems()
                    .stream()
                    .filter(oldItem -> oldItem.getId() == item.getId())
                    .collect(Collectors.toList());
            itemRepository.deleteById(updatedItems.get(0).getId());

            orderToUpdate.get().getItems().clear();
            orderToUpdate.get().setItems(updatedItems);
            orderRepository.save(orderToUpdate.get());
        }
    }

   /* @Override
    public List<Order> findOrderThatIncludesItems(List<Item> itemsToRemove) {
        List<Order> orders = orderRepository.findOrdersThatIncludesItems(itemsToRemove);
        orders.forEach(order -> {
            order.getItems().forEach(item -> {
                order.setTotalPrice(order.getTotalPrice() - item.getProduct().getPrice());
            });
            order.getItems().removeAll(itemsToRemove);
        });
        return orders;
    }*/
}
