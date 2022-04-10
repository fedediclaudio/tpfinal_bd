package com.bd.tpfinal.config;

import com.bd.tpfinal.mappers.client.ClientMapper;
import com.bd.tpfinal.mappers.item.ItemMapper;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.proxy.repositories.ClientRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.OrderRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.impl.ClientRepositoryProxyImpl;
import com.bd.tpfinal.proxy.repositories.impl.OrderRepositoryProxyImpl;
import com.bd.tpfinal.repositories.ClientRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryProxyConfig {

    @Bean
    public OrderRepositoryProxy getOrderRepositoryProxy(OrderMapper orderMapper,
                                                        OrderRepository orderRepository,
                                                        ClientRepository clientRepository,
                                                        ProductRepository productRepository,
                                                        ClientMapper clientMapper,
                                                        ItemMapper itemMapper) {
        return new OrderRepositoryProxyImpl(orderMapper, orderRepository, clientRepository, productRepository, clientMapper, itemMapper);

    }

    @Bean
    public ClientRepositoryProxy getClientRepositoryProxy(ClientRepository clientRepository, ClientMapper clientMapper){
        return new ClientRepositoryProxyImpl(clientRepository, clientMapper);
    }
}
