package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;

import java.util.Optional;

public interface OrderService {
    public Optional<Item> AgregarItemAOrdenCreada(Long order_id, Item item) throws Exception;
}
