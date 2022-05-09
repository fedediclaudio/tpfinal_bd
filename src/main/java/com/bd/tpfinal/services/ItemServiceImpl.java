package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import com.bd.tpfinal.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    @Transactional
    public void newItem(Item newItem)
    {
        this.itemRepository.save(newItem);
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


}
