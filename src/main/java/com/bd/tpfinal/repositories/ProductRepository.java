package com.bd.tpfinal.repositories;

import com.bd.tpfinal.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {


}
