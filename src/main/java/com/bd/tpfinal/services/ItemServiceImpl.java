package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.model.Order;
import com.bd.tpfinal.repositories.ItemRepository;
import com.bd.tpfinal.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService
{
    private final ItemRepository itemRepository;
    private final OrderRepository orderRepository;

@Autowired
    public ItemServiceImpl(ItemRepository itemRepository, OrderRepository orderRepository)
    {
        this.itemRepository = itemRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    //agrega un item a la orden y actualiza el precio total de la orden
    public void newItem(Item newItem)
    {
        Order orden = newItem.getOrder();
        this.itemRepository.save(newItem);
        orden = this.orderRepository.findByNumber(orden.getNumber());
        List<Item> items = this.itemRepository.findByOrderId(orden.getNumber());
        orden.setItems(items);
        orden.setTotalPrice();
        this.orderRepository.save(orden);
    }

    @Override
    @Transactional
    public List<Item> getAll()
    {
        return this.itemRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Item> getItemById(Long id)
    {
        return this.itemRepository.findById(id);
    }

    @Override
    public List<Item> getAllBySupplier(Long id)
    {
        return this.itemRepository.findBySupplier(id);
    }

    @Override
    public List<Item> getItemByOrderId(Long id_orden)
    {
        return this.itemRepository.findByOrderId(id_orden);
    }


}
