package com.bd.tpfinal.repositories;

import com.bd.tpfinal.DTOs.ProductoPrecioPromedioDTO;
import com.bd.tpfinal.model.Product;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query(value="{name:{$eq:?0}},{_id:1,name:1,description:1}")//retorna el producto acotado por nombre y descripcion
    List<Product> findByNameIgnoreCaseContaining(String name);

    @Query(value="{'supplier': DBRef('supplier', ObjectId('62b723d1084d3b4a236bc056'))}")
    List<Product> findProductsBySupplierId(String supplier_cuil);

    @Query(value="{ $and:[ {'supplier': DBRef('supplier', ObjectId('62b723d1084d3b4a236bc056'))}, {'name':'Lechuga'}]}")
    List<Product> findBySupplierIdAndNameIgnoreCaseContaining(long supplier_id, String name);

    @Aggregation(pipeline = {"{ $group : { '_id': '$name', promedioPrecio : {  $avg : '$price' } } }"})
    List<ProductoPrecioPromedioDTO> getProductosPrecioPromedioDTO();

}
