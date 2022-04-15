package com.bd.tpfinal.config;

import com.bd.tpfinal.mappers.client.ClientMapper;
import com.bd.tpfinal.mappers.item.ItemMapper;
import com.bd.tpfinal.mappers.orders.OrderMapper;
import com.bd.tpfinal.mappers.product.ProductMapper;
import com.bd.tpfinal.mappers.suppplier.SupplierMapper;
import com.bd.tpfinal.proxy.repositories.ClientRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.OrderRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.ProductRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.SupplierRepositoryProxy;
import com.bd.tpfinal.proxy.repositories.impl.ClientRepositoryProxyImpl;
import com.bd.tpfinal.proxy.repositories.impl.OrderRepositoryProxyImpl;
import com.bd.tpfinal.proxy.repositories.impl.ProductRepositoryProxyImpl;
import com.bd.tpfinal.proxy.repositories.impl.SupplierRepositoryProxyImpl;
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
    public ClientRepositoryProxy getClientRepositoryProxy(ClientRepository clientRepository, ClientMapper clientMapper){
        return new ClientRepositoryProxyImpl(clientRepository, clientMapper);
    }

    @Bean
    public ProductRepositoryProxy getProductRespositoryProxy(ProductRepository productRepository,
                                                             HistoricalProductPriceRepository historicalProductPriceRepository,
                                                             ProductMapper productMapper){
        return new ProductRepositoryProxyImpl(productRepository, historicalProductPriceRepository, productMapper);
    }

    @Bean
    public SupplierRepositoryProxy getSupplierRepositoryProxy(SupplierRepository supplierRepository,
                                                              SupplierMapper supplierMapper,
                                                              ProductRepository productRepository,
                                                              ProductTypeRepository productTypeRepository,
                                                              ProductMapper productMapper,
                                                              SupplierWithOrdersCountRepository supplierWithOrdersCountRepository){
        return new SupplierRepositoryProxyImpl(supplierRepository, productTypeRepository, productRepository, supplierMapper,
                productMapper, supplierWithOrdersCountRepository);
    }
}
