package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductTypeAvgPrice_DTO;
import com.bd.tpfinal.utils.NoExisteProductoException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public interface ProductService
{
    public void newProduct(Product newProduct);
    public List<Product> getAll();
    public Product getProductById(Long id);
    public Product getProductByName(String name);
    public void updateData(Long id_product, Product updatedProduct) throws NoExisteProductoException;
    public void eliminarProductoById(Long id_product)  throws NoExisteProductoException;
    public List<Product> getProductoByProductType(Long id_product_type);
    public List<HistoricalProductPrice> getPrices(Long id_product, Date desde, Date hasta);
    public List<ProductTypeAvgPrice_DTO> getAvgPriceForProductType();
    public List<Product> getBySupplierId(Long id_supplier);

}
