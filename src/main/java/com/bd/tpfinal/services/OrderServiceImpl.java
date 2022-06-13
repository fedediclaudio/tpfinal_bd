package com.bd.tpfinal.services;

import com.bd.tpfinal.DTOs.FinishOrderScore;
import com.bd.tpfinal.DTOs.ItemDTO;

import com.bd.tpfinal.model.*;
import com.bd.tpfinal.repositories.ItemRepository;

import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.repositories.SupplierRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private ProductRepository  productRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private DeliveryManService deliveryManService;

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public Optional<Order> findOrderById(long order_id) {
        return orderRepository.findById(order_id);
    }

    @Override
    @Transactional
    public Optional<Order> confirmarPedido(long order_id) throws Exception {
        Optional<DeliveryMan> deliveryMan = deliveryManService.getFreeAndActiveDeliveryMan();
        if(deliveryMan.isPresent()) { // si no hay repartidor libre, no se puede confirmar pedido
            DeliveryMan currentDeliveryMan = deliveryMan.get();
            Optional<Order> order = orderRepository.findById(order_id);
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
    @Transactional
    public Optional<Order> cancelarPedido(long order_id) throws Exception {
        Optional<Order> order = orderRepository.findById(order_id);
        if(order.isPresent()) {
            Order orderActual = order.get();
                if (orderActual.getStatus().canCancel()) {
                    orderActual.getStatus().cancel();
                    orderRepository.save(orderActual);;
                    return order;
                }
            }
        return null;

    }


    @Override
    @Transactional
    public Optional<Order> rechazarPedido(long order_id) throws Exception {
        Optional<Order> order = orderRepository.findById(order_id);
        if(order.isPresent()) {
            Order orderActual = order.get();
            if (orderActual.getStatus().canRefuse()) {
                orderActual.getStatus().refuse();

            orderRepository.save(order.get());
            return order;
            }
        }
        return null;
    }
    @Override
    @Transactional
    public Optional<Order> entregarPedido(long order_id) throws Exception {
        Optional<Order> order = orderRepository.findById(order_id);
        if(order.isPresent()) {
            Order orderActual = order.get();
            if (orderActual.getStatus().canDeliver()) {
                orderActual.getStatus().deliver();
            orderRepository.save(order.get());
            return order;
            }
        }
        return null;
    }

    @Override
    @Transactional
    public Optional<Order> finalizarPedido(FinishOrderScore score, long order_id) throws Exception {
        Optional<Order> order = orderRepository.findById(order_id);
        if(order.isPresent()) {
            Order orderActual = order.get();
            if (orderActual.getStatus().canFinish()) {
                orderActual.getStatus().finish(score.getScore(), score.getCommentary());
                orderRepository.save(order.get());

                Supplier orderSupplier = orderActual.getSupplier();
                List<Order> ordersSupplier = orderRepository.findOrdersDeSupplier(orderSupplier.getId() );
                float totalScore = 0;
                float totalQualification = 0;
                for (int i=0;i<ordersSupplier.size();i++)
                {
                    totalScore = totalScore +  ordersSupplier.get(i).getQualification().getScore();
                }
                totalQualification =  totalScore / ordersSupplier.size();
                orderSupplier.setQualificationOfUsers(totalQualification);
                supplierRepository.save(orderSupplier);

            }

            return order;
        }
        return null;
    }

    @Override
    @Transactional
    public Optional<Item> agregarItemAOrdenCreada(Long order_id, ItemDTO item) throws Exception{
        Optional<Order> order = orderRepository.findById(order_id);
        Optional<Product> product = productRepository.findById(item.getIdProduct());
        Product productActual = product.get();
        if (!productActual.isActive()){
            throw new Exception("La producto seleccionado no está disponible");
        }
        Item it = new Item();
        if(order.isPresent()) {
            if(order.get().getStatus().getName().equals("Pending")) {
                Order ordenActual = order.get();

                it.setDescription(item.getDescription());
                it.setQuantity(item.getQuantity());
                it.setProduct(productActual);
                it.setOrder(ordenActual);
                ordenActual.getItems().add(it);
                float total = ((productActual.getPrice()) * it.getQuantity() ) + ordenActual.getTotalPrice();
                ordenActual.setTotalPrice(total);
                orderRepository.save(ordenActual);
            } else {
                throw new Exception("No se puede agregar el item " + it.getDescription() + " a la orden " + order_id + " la misma ya fue asignada");
            }
        }
        else {
            throw new Exception("La orden no existe");
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
    @Transactional
    public void updateOrdersTotalPrice(String status) {
        List<Order> orderList = new ArrayList<Order>();
        Iterable<Order> orders = orderRepository.findAll();
        orders.forEach(orderList::add);
        int i = 0;
        while (!orderList.isEmpty()) {
            Order order = orderList.get(i);
            if (!order.getStatus().getName().equals("Pending")) {
                int j = 0;
                List <Item> items = itemRepository.getByOrderId(order.getNumber());
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
        }
    }



    @Override
    public List <Order> getOrderByOrderStatus(String status) {
        List<Order> orderList = new ArrayList<Order>();
        Iterable<Order> orders = orderRepository.findAll();
        orders.forEach(orderList::add);
        int i = 0;
        while (!orderList.isEmpty()) {
            Order order = orderList.get(i);
            if (!order.getStatus().getName().equals(status)) {
                orderList.remove(i);
            }
        }
        return orderList;
    }




}
