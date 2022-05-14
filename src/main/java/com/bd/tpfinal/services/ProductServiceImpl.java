package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import com.bd.tpfinal.utils.NoExisteProductoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class ProductServiceImpl implements ProductService
{
    private final ProductRepository productRepository;
    private final HistoricalProductPriceRepository historicalProductPriceRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, HistoricalProductPriceRepository historicalProductPriceRepository)
    {
        this.productRepository = productRepository;
        this.historicalProductPriceRepository = historicalProductPriceRepository;
    }

    @Override
    @Transactional
    public void newProduct(Product newProduct)
    {
        this.productRepository.save(newProduct);
        Date starDate = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
        Date finishDate = null;
        HistoricalProductPrice hpp = new HistoricalProductPrice(newProduct.getPrice(), starDate, finishDate, newProduct);
        this.historicalProductPriceRepository.save(hpp);
    }

    @Override
    @Transactional
    public List<Product> getAll()
    {
        return this.productRepository.findAll();
    }

    @Override
    @Transactional
    public Product getProductById(Long id)
    {
        Optional<Product> opt_product_buscado = this.productRepository.findById(id);
        Product buscado = null;
        if(opt_product_buscado.isPresent())
        {
            buscado = opt_product_buscado.get();
        }
        return buscado;
    }

    @Override
    @Transactional
    public Product getProductByName(String name)
    {
        Product producto = null;
        List<Product> productos = this.productRepository.findByName(name);
        if (productos != null)
            producto = productos.get(0);
        return producto;
    }

    //TODO: revisar la lógica de los precios históricos, creo que falla
    @Override
    @Transactional
    //tener en cuenta las implicancias de un cambio de precio.
    //PRE: se considera que las actualizaciones son válidas. No se verifica acá.
    //cuando se crea un producto, el precio actual se agrega como histórico sin fecha de finalización
    //la fecha de finalización es la fecha de actualización del precio.
    public void updateData(Long id_product, Product updatedProduct) throws NoExisteProductoException
    {
        //Long id = updatedProduct.getId();
        Optional<Product> opt_product_buscado = this.productRepository.findById(id_product);
        Product producto_buscado = null;
        if(opt_product_buscado.isPresent())
        {
            producto_buscado = opt_product_buscado.get();
        }
        if (producto_buscado == null)
            throw new NoExisteProductoException("No existe producto");
        List<HistoricalProductPrice> prices = this.historicalProductPriceRepository.findByProductId(id_product);

        if (updatedProduct.getPrice() != producto_buscado.getPrice())
        {
            //List<HistoricalProductPrice> prices = this.historicalProductPriceRepository.findByProductId(id_product);
            int last_index = prices.size() - 1;
            HistoricalProductPrice hpp_ultimo = prices.get(last_index);
            System.out.println("ultimo indicoe  "+last_index);
            hpp_ultimo.setFinishDate(Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime());
            prices.set(last_index, hpp_ultimo);
            Date startDate = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
            HistoricalProductPrice hpp_nuevo = new HistoricalProductPrice(updatedProduct.getPrice(), startDate, null, producto_buscado);
            producto_buscado.setPrices(prices);
            producto_buscado.getPrices().add(hpp_nuevo);
            producto_buscado.setPrice(updatedProduct.getPrice());
        }
        producto_buscado.setPrices(prices);
        producto_buscado.setDescription(updatedProduct.getDescription());
        producto_buscado.setName(updatedProduct.getName());
        producto_buscado.setSupplier(updatedProduct.getSupplier());
        producto_buscado.setType(updatedProduct.getType());
        producto_buscado.setWeight(updatedProduct.getWeight());
        producto_buscado.setEliminado(updatedProduct.getEliminado());
        this.productRepository.save(producto_buscado);
    }

    @Override
    public void eliminarProductoById(Long id_product)  throws NoExisteProductoException
    {
        Optional<Product> opt_product_buscado = this.productRepository.findById(id_product);
        Product producto_buscado = null;
        if(opt_product_buscado.isPresent())
        {
            producto_buscado = opt_product_buscado.get();
        }
        if (producto_buscado == null)
            throw new NoExisteProductoException("No existe producto");
        producto_buscado.eliminar();
        this.productRepository.save(producto_buscado);
    }

    @Override
    public List<Product> getProductoByProductType(Long id_product_type)
    {
        List<Product> productos = this.productRepository.findByTypeId(id_product_type);
        return productos;
    }

}
