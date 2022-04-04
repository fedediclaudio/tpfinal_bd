package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemService
{
    private final ItemRepository itemRepository;

@Autowired
    public ItemServiceImpl(ItemRepository itemRepository)
    {
        this.itemRepository = itemRepository;
    }



    @Override
    public void addItem(Item newItem)
    {
        this.itemRepository.save(newItem);
    }


}
