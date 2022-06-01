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
import java.util.Date;
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
    //TODO:sacarle el retorno, creo que no incluye al id
    public void newOrder(Order newOrder)
    {
        //newOrder.getClient().addOrder(newOrder);
        this.orderRepository.save(newOrder);
    }

    @Override
    @Transactional
    public Order newOrder_seteado_state(Order newOrden)
    {
        this.orderRepository.save(newOrden);
        newOrden.setStatusByName();
        return newOrden;
    }

    @Override
    @Transactional
    public List<Order> getAll()
    {
        List<Order> orders = this.orderRepository.findAll();
        return orders;
    }

    @Override
    @Transactional
    public List<Order> getAllWithStatus()
    {
        List<Order> orders = this.orderRepository.findAll();
        int cant = orders.size();
        for(int i=0; i<cant; i++)
        {
            orders.get(i).setStatusByName();
        }
        return orders;
    }

    @Override
    @Transactional
    public List<Order> getAllWithoutQual()
    {
        List<Order> orders = this.orderRepository.findAllWithoutQual();
        int cant = orders.size();
        System.out.println("getAllWithoutQual() tamano lista de ordenes: "+cant);
        int i = 0;
        for(i=0; i<cant; i++)
        {
            orders.get(i).setStatusByName();
            System.out.println("orden number: "+orders.get(i).getNumber()+" status: "+orders.get(i).getOrderStatus().getName());
        }
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
     *
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
        orden_actualizada.setStatusByName();
        return orden_actualizada;
    }



    @Override
    @Transactional
    //todas las Order de un Supplier
    public List<Order> getOrderByIdSupplier(Long id)
    {
        return this.orderRepository.findBySupplier(id);
    }

    @Override
    @Transactional
    // suma de calificaciones de todas las Order que contienen a un Supplier
    // cuenta las calificaciones de todas las Order que contienen a un Supplier
    // calcula el promedio
    public double getQualificationAverage(Long id_Supplier)
    {
        return this.orderRepository.findQualificationSupplier(id_Supplier);
    }

    @Override
    @Transactional
    public List<Order> getByClientId(Long id)
    {
        return this.orderRepository.findByClient(id);
    }

    @Override
    @Transactional
    public List<Order> getBySupplierMaxCantItems(Long id_supplier)
    {
        return this.orderRepository.findOrderBySupplierItems(id_supplier);
    }

    @Override
    @Transactional
    public List<Order> getOrderMaxPricePorFecha(Date fecha)
    {
        return this.orderRepository.findMaxOrderByDate(fecha);

    }

    @Override
    @Transactional
    public Order getOrderByDateOfOrder(Date date)
    {
        Order orden = this.orderRepository.findByDateOfOrder(date);
        orden.setStatusByName();
        return orden;
    }

    @Override
    @Transactional
    //retorna la orden con el estado actualizado.
    public Order aceptacionDeOrden(Long number) throws Exception
    {
        Order orden = this.orderRepository.findByNumber(number);
        orden.deliver();
        actualizarOrder(orden);
        orden.setStatusByName();//no es necesario, rever. Lo hace actualizarOrder()
        return orden;
    }

    @Override
    @Transactional
    //retorna la orden con el estado actualizado.
    public Order finalizacionDeOrden(Long number) throws Exception
    {
        Order orden = this.orderRepository.findByNumber(number);
        orden.finish();
        actualizarOrder(orden);
        orden.setStatusByName();//no es necesario, rever. Lo hace actualizarOrder()
        return orden;
    }

    @Override
    @Transactional
    public Order rechazoDeOrden(Long number) throws Exception
    {
        Order orden = this.orderRepository.findByNumber(number);
        orden.refuse();
        actualizarOrder(orden);
        orden.setStatusByName();//no es necesario, rever. Lo hace actualizarOrder()
        return orden;
    }

    @Override
    @Transactional
    public Order cancelacionDeOrden(Long number) throws Exception
    {
        Order orden = this.orderRepository.findByNumber(number);
        orden.cancel();
        actualizarOrder(orden);
        orden.setStatusByName();//no es necesario, rever. Lo hace actualizarOrder()
        return orden;
    }


}
