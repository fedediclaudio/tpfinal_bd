package com.bd.tpfinal.services;

import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.utils.NoExisteProductoException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService
{
    public void newProduct(Product newProduct);
    public List<Product> getAll();
    public Optional<Product> getProductById(Long id);
    public Product getProductByName(String name);
    public void updateData(Long id_product, Product updatedProduct) throws NoExisteProductoException;
    public List<Product> getProductoByProductType(Long id_product_type);

}
