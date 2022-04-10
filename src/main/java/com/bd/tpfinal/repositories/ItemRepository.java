package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
