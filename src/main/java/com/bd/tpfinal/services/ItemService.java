package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> itemsWithProductId(long itemId);
    void eliminar(List<Item> itemsToRemove);
}