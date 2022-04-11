package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    @Override
    public List<Item> getAll()
    {
        return this.itemRepository.findAll();
    }

    @Override
    public Optional<Item> getItemById(Long id)
    {
        return this.itemRepository.findById(id);
    }


}
