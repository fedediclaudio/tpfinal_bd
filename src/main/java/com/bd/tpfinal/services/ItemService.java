package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService
{
    public void newItem(Item newItem);
    public List<Item> getAll();
    public Optional<Item> getItemById(Long id);
    public List<Item> getAllBySupplier(Long id);
    public List<Item> getItemByOrderId(Long id_orden);

    //TODO: completar este ItemService con más servicios

}
