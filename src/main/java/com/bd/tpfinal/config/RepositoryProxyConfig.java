package com.bd.tpfinal.config;

import com.bd.tpfinal.mappers.client.ClientMapper;
import com.bd.tpfinal.mappers.delivery.DeliveryManMapper;
import com.bd.tpfinal.mappers.item.ItemMapper;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.mappers.product.ProductMapper;
import com.bd.tpfinal.mappers.suppplier.SupplierMapper;
import com.bd.tpfinal.proxy.repositories.*;
import com.bd.tpfinal.proxy.repositories.impl.*;
import com.bd.tpfinal.repositories.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RepositoryProxyConfig {

    @Bean
    public OrderRepositoryProxy getOrderRepositoryProxy(OrderMapper orderMapper,
                                                        OrderRepository orderRepository,
                                                        ClientRepository clientRepository,
                                                        ProductRepository productRepository,
                                                        DeliveryManRepository deliveryManRepository,
                                                        SupplierRepository supplierRepository,
                                                        ClientMapper clientMapper,
                                                        ItemMapper itemMapper) {
        return new OrderRepositoryProxyImpl(orderMapper, orderRepository, clientRepository, productRepository, deliveryManRepository, supplierRepository, clientMapper, itemMapper);

    }

    @Bean
    public ClientRepositoryProxy getClientRepositoryProxy(ClientRepository clientRepository, ClientMapper clientMapper, OrderMapper orderMapper){
        return new ClientRepositoryProxyImpl(clientRepository, clientMapper, orderMapper);
    }

    @Bean
    public ProductRepositoryProxy getProductRespositoryProxy(ProductRepository productRepository,
                                                             ProductMapper productMapper, SupplierRepository supplierRepository,
                                                             ProductTypeRepository productTypeRepository,
                                                             HistoricalProductPriceRepository historicalProductPriceRepository){
        return new ProductRepositoryProxyImpl(productRepository, productMapper, supplierRepository, productTypeRepository, historicalProductPriceRepository);
    }

    @Bean
    public SupplierRepositoryProxy getSupplierRepositoryProxy(SupplierRepository supplierRepository,
                                                              SupplierMapper supplierMapper,
                                                              ProductRepository productRepository,
                                                              ProductTypeRepository productTypeRepository,
                                                              ProductMapper productMapper,
                                                              SupplierWithOrdersCountRepository supplierWithOrdersCountRepository,
                                                              SupplierTypeRepository supplierTypeRepository){
        return new SupplierRepositoryProxyImpl(supplierRepository, productTypeRepository, productRepository, supplierMapper,
                productMapper, supplierWithOrdersCountRepository, supplierTypeRepository);
    }

    @Bean
    public DeliveryMenRepositoryProxy getDeliveryMenRepositoryProxy(DeliveryManRepository deliveryManRepository, DeliveryManMapper deliveryManMapper){
        return new DeliveryMenRepositoryProxyImpl(deliveryManRepository, deliveryManMapper);
    }
}
