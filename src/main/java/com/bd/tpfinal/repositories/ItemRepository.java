package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Item;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {
    List<Item> getByProductId(long productId);

    @Query(value="DELETE FROM item where id = :id", nativeQuery = true)
    void deleteById(long id);

    @Query(value="SELECT i.* from item i where id_product = :product_id", nativeQuery = true)
    Optional<Item> findByProductId(long product_id);
}
