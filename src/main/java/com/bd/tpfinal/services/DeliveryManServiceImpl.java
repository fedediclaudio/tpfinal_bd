package com.bd.tpfinal.services;

import com.bd.tpfinal.model.DeliveryMan;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.implementations.ClientRepository;
import com.bd.tpfinal.repositories.implementations.DeliveryManRepository;
import com.bd.tpfinal.repositories.implementations.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class DeliveryManServiceImpl implements DeliveryManService {
    @Autowired
    ClientRepository clientRepository;
    @Autowired
    DeliveryManRepository deliveryManRepository;
    @Autowired
    OrderRepository orderRepository;

    public DeliveryMan addNewDeliveryMan(DeliveryMan deliveryMan) throws Exception {
        deliveryMan.setDateOfAdmission(LocalDate.now());
        deliveryMan.setActive(true);
        if (!deliveryMan.isValid()) {
            System.out.println("El DeliveryMan no es valido, corrobore los datos enviados");
            return null;
        }
        deliveryMan = deliveryManRepository.save(deliveryMan);

        return deliveryMan;
    }

    public long deliveryManCount() throws Exception {
        return deliveryManRepository.count();
    }

    public List<DeliveryMan> getAllDeliveryMan() throws Exception {
        return deliveryManRepository.findAll();
    }

    public List<Order> getAllPendingOrders(String idDeliveryMan) throws Exception {
        return orderRepository.getAllOrdersForDeliveryMan(idDeliveryMan, "Assigned");
    }

    public Order getNextPendingOrder(String idDeliveryMan) throws Exception {
        return orderRepository.getNextOrderDeliveryMan(idDeliveryMan, "Assigned");
    }

    public List<DeliveryMan> getTopTen() throws Exception {
        return deliveryManRepository.findTop10ByOrderByScoreDesc();
    }

    public boolean deliverNextPendingOrder(String idDeliveryMan) throws Exception {
        DeliveryMan dm = deliveryManRepository
                .findById(idDeliveryMan)
                .orElse(null);

        if (isNull(dm)) {
            System.out.println("La DeliveryMan no existe");
            return false;
        }

        if (!dm.isActive()) {
            System.out.println("El DeliveryMan no esta activo");
            return false;
        }
        if (!dm.isFree()) {
            System.out.println("El DeliveryMan no esta libre");
            return false;
        }
        Order order = orderRepository.getNextOrderDeliveryMan(idDeliveryMan, "Assigned");

        if (isNull(order)) {
            System.out.println("La orden no existe");
            return false;
        }

        if (!order.getStatus().canDeliver()) {
            System.out.println("La orden no se puede despachar");
            return false;
        }
        order.getStatus().deliver();
        orderRepository.save(order);
        dm.setFree(false);
        deliveryManRepository.save(dm);

        return true;
    }

    public boolean refuseNextPendingOrder(String idDeliveryMan) throws Exception {
        DeliveryMan dm = deliveryManRepository
                .findById(idDeliveryMan)
                .orElse(null);

        if (dm == null) {
            System.out.println("La DeliveryMan no existe");
            return false;
        }
        if ((!dm.isActive()) || (!dm.isFree())) {
            System.out.println("El DeliveryMan no esta activo o libre");
            return false;
        }
        Order order = orderRepository.getNextOrderDeliveryMan(idDeliveryMan, "Assigned");

        if (order == null) {
            System.out.println("La orden no existe");
            return false;
        }
        if (!order.getStatus().canRefuse()) {
            System.out.println("La orden no se puede cancelar");
            return false;
        }
        order.getStatus().refuse();
        orderRepository.save(order);
        return true;
    }

    @Transactional
    public boolean finishActualOrder(String idDeliveryMan) throws Exception {
        DeliveryMan dm = deliveryManRepository
                .findById(idDeliveryMan)
                .orElse(null);

        if (isNull(dm)) {
            System.out.println("La DeliveryMan no existe");
            return false;
        }

        if (!dm.isActive()) {
            System.out.println("El DeliveryMan no esta activo");
            return false;
        }

        Order order = orderRepository.getNextOrderDeliveryMan(idDeliveryMan, "Sent");

        if (isNull(order)) {
            System.out.println("La orden no existe");
            return false;
        }

        if (!order.getStatus().canFinish()) {
            System.out.println("La orden no se puede terminar");
            return false;
        }
        order.getStatus().finish();
        orderRepository.save(order);
        clientRepository.save(order.getClient());
        dm = order.getDeliveryMan();
        dm.removePendingOrder(order);
        deliveryManRepository.save(dm);

        return true;
    }
}
