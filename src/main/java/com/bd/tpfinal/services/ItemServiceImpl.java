package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Item;
import java.util.List;
import java.util.Optional;

public class ItemServiceImpl  implements ItemService {
    @Override
    public void eliminar(List<Item> itemsToRemove) {
    }

    @Override
    public Optional<Item> itemWithProductId(long product_id) {
        return Optional.empty();
    }
}
