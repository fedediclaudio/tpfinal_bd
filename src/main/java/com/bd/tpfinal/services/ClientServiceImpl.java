package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Address;
import com.bd.tpfinal.model.Client;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.ClientRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.util.Objects.isNull;

@Service
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final OrderRepository orderRepository;

    @Autowired
    public ClientServiceImpl(final ClientRepository clientRepository,
                             final OrderRepository orderRepository) {
        this.clientRepository = clientRepository;
        this.orderRepository = orderRepository;
    }

    public Client addNewClient(Client client) throws Exception {
        client.setDateOfRegister(LocalDate.now());
        return clientRepository.save(client);
    }

    public Address addNewAddress(Address address) throws Exception {

        Client client = clientRepository
                .findById(address.getClient().getId())
                .orElse(null);

        if (isNull(client)) {
            System.out.println("El cliente no existe");
            return null;
        }

        address.setClient(client);
        client.addAddress(address);
        client = clientRepository.save(client);

        return client.getAddresses().get(client.getAddresses().size() - 1);
    }

    public long clientCount() throws Exception {
        return clientRepository.count();
    }

    public List<Client> getAllClients() throws Exception {
        return clientRepository.findAll();
    }

    public List<Address> getAddresses(String idClient) throws Exception {
        Client client = clientRepository
                .findById(idClient)
                .orElse(null);

        if (isNull(client)) {
            System.out.println("El cliente no existe");
            return null;
        }

        return client.getAddresses();
    }

    public List<Order> getAllOrders(String idClient) throws Exception {
        return orderRepository.getAllOrdersForClient(idClient, null);
    }

    public List<Order> getAllPendingOrders(String idClient) throws Exception {
        return orderRepository.getAllOrdersForClient(idClient, "Pending");
    }

    public Order getNextPendingOrder(String idClient) throws Exception {
        return orderRepository.getNextOrderForClient(idClient, "Pending");
    }

    public boolean cancelPendingOrder(String idClient, String orderNumber) throws Exception {
        Client client = clientRepository
                .findById(idClient)
                .orElse(null);

        if (isNull(client)) {
            System.out.println("El cliente no existe");
            return false;
        }

        if (!client.isActive()) {
            System.out.println("El cliente no esta activo");
            return false;
        }

        Order order = orderRepository.getOrderByNumber(orderNumber);

        if (isNull(order)) {
            System.out.println("La orden no existe");
            return false;
        }

        if (!order.getStatus().canCancel()) {
            System.out.println("La orden no se puede cancelar");
            return false;
        }

        order.getStatus().cancel();

        orderRepository.save(order);

        client.removeOrder(order);

        clientRepository.save(client);

        return true;
    }

}
