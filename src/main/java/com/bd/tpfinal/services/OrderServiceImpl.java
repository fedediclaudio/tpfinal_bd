package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;
    private final DeliveryManRepository deliveryManRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, DeliveryManRepository deliveryManRepository)
    {
        this.orderRepository = orderRepository;
        this.deliveryManRepository = deliveryManRepository;
    }

    @Override
    public Order newOrder(Order newOrder)
    {
        //newOrder.getClient().addOrder(newOrder);
        this.orderRepository.save(newOrder);
        return newOrder;
    }

    @Override
    public List<Order> getAll()
    {
        List<Order> orders =  this.orderRepository.findAll();
        return orders;
    }

    /**
     * retorna la orden con el atributo status reconstruido.
     *
     * @param id
     * @return
     */
    @Override
    public Optional<Order> getById(Long id)
    {
        Order order_aux;
        Optional<Order> order_aux_optional = this.orderRepository.findById(id);
        order_aux = order_aux_optional.get();
        order_aux.setStatusByName();
        //OrderStatus orderStatus_aux = this.orderStatusRepository.findByOrder(id);
        //order_aux.setOrderStatus(orderStatus_aux);
        return Optional.of(order_aux);
    }

    /**
     * retorna un objeto Order de acuerdo al valor de su atributo "number", que es el id.
     * @param number
     * @return
     */
    @Override
    public Order getByNumber(Long number)
    {
        Order order_aux = this.orderRepository.findByNumber(number);
        order_aux.setStatusByName();
        return order_aux;
    }

    @Override
    public boolean assignOrderToDeliveryMan(Order orden, DeliveryMan dm)
    {
        boolean rta = false;
        orden.assignDeliveryMan(dm);
        this.orderRepository.save(orden);

        return rta;

    }

    @Override
    public Order actualizarOrder(Order orden)
    {
        return newOrder(orden);
    }
}
