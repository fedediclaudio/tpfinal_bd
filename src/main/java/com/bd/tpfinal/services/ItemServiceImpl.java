package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService{
    @Autowired
    private ItemRepository itemRepository;

    @Override
    public List<Item> itemsWithProductId(long product_id) {
        return itemRepository.getByProductId(product_id);
    }

    @Override
    public void eliminar(List<Item> itemsToRemove) {
        itemRepository.deleteAll(itemsToRemove);
    }
}
