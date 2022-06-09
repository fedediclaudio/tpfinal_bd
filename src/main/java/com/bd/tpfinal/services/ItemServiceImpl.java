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
    public void eliminar(List<Item> itemsToRemove) {

        itemsToRemove.forEach(item -> itemRepository.deleteById(item.getId()));
    }

    @Override
    public Optional<Item> itemWithProductId(long product_id) {
        return itemRepository.findByProductId(product_id);
    }
}
