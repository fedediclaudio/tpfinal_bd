package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    /*List<Item> getByProductId(long productId);
    List<Item> getByOrderId(long orderId);
    void deleteById(long id);*/
    List<Item> findByOrderId(String orderId);
}
