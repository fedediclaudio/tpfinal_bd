package com.bd.tpfinal.repositories.implementations;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.repositories.interfaces.IProductTypeRepository;

public interface ProductTypeRepository extends MongoRepository<ProductType, String>, IProductTypeRepository {

    // Aca se definen los metodos que van a ser utilizados pero que son implementados por Spring Boot

    ProductType getProductTypeById(String id);

    ProductType getProductTypeByName(String name);


}
