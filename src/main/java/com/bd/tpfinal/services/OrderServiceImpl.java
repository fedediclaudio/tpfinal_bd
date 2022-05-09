package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.DeliveryManRepository;
import com.bd.tpfinal.repositories.ItemRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService
{
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final DeliveryManRepository deliveryManRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ItemRepository itemRepository, DeliveryManRepository deliveryManRepository)
    {
        this.orderRepository = orderRepository;
        this.itemRepository = itemRepository;
        this.deliveryManRepository = deliveryManRepository;
    }

    @Override
    @Transactional
    public Order newOrder(Order newOrder)
    {
        //newOrder.getClient().addOrder(newOrder);
        this.orderRepository.save(newOrder);
        return newOrder;
    }

    @Override
    @Transactional
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
    @Transactional
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
    @Transactional
    public Order getByNumber(Long number)
    {
        Order order_aux = this.orderRepository.findByNumber(number);
        order_aux.setStatusByName();
        return order_aux;
    }

    @Override
    @Transactional
    public boolean assignOrderToDeliveryMan(Long orden_id, Long dm_id)
    {
        Optional<Order> orden_buscada = this.orderRepository.findById(orden_id);
        boolean rta = true;
        Optional<DeliveryMan> dm = this.deliveryManRepository.findById(dm_id);
        orden_buscada.get().assignDeliveryMan(dm.get());
        this.orderRepository.save(orden_buscada.get());
        return rta;

    }

    @Override
    @Transactional
    public Order actualizarOrder(Order orden_actualizada)
    {
        this.orderRepository.save(orden_actualizada);
        return orden_actualizada;
    }

    @Override
    @Transactional
    public void cancelarOrder(Long number) throws Exception
    {
        Order orden_buscada = this.orderRepository.findByNumber(number);
        orden_buscada.setStatusByName();
        orden_buscada.getOrderStatus().cancel();
        this.orderRepository.save(orden_buscada);
    }

    @Override
    public List<Order> getOrderByIdSupplier(Long id)
    {
        return this.orderRepository.findBySupplier(id);
    }


}
