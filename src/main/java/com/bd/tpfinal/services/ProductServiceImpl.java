package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductTypeAvgPrice_DTO;
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
        this.historicalProductPriceRepository.save(newProduct.getPrices().get(0));
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
        List<HistoricalProductPrice> hpp_list = this.historicalProductPriceRepository.findByProductId(id_product);

        if (updatedProduct.getPrice() != producto_buscado.getPrice())
        {
            int last_index = hpp_list.size()-1;
            HistoricalProductPrice hpp_ultimo = hpp_list.get(last_index);
            System.out.println("ultimo indice  "+last_index);
            hpp_ultimo.setFinishDate(Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime());
            //hpp_list.set(last_index, hpp_ultimo);
            System.out.println("startDate: "+hpp_ultimo.getStartDate() + " finishDate:" +hpp_ultimo.getFinishDate());
            Date startDate = Calendar.getInstance(TimeZone.getTimeZone("es-AR")).getTime();
            HistoricalProductPrice hpp_nuevo = new HistoricalProductPrice(updatedProduct.getPrice(), startDate, null, producto_buscado);
            //producto_buscado.setPrices(hpp_list);
            //producto_buscado.getPrices().add(hpp_nuevo);
            //producto_buscado.setPrice(updatedProduct.getPrice());
            hpp_list.add(hpp_nuevo);
            Iterator<HistoricalProductPrice> hppIterator = producto_buscado.getPrices().iterator();//hpp actualizado con la fecha de finalización del ultimo y el agregado del nuevo
            while(hppIterator.hasNext()) //save de todos, ya que el viejo último cambió su fecha de finalización.
            {
                HistoricalProductPrice historicalProductPrice = (HistoricalProductPrice) hppIterator.next();
                this.historicalProductPriceRepository.save(historicalProductPrice);
            }
        }
        producto_buscado.setPrices(hpp_list);
        producto_buscado.setDescription(updatedProduct.getDescription());
        producto_buscado.setName(updatedProduct.getName());
        producto_buscado.setSupplier(updatedProduct.getSupplier());
        producto_buscado.setType(updatedProduct.getType());
        producto_buscado.setWeight(updatedProduct.getWeight());
        producto_buscado.setEliminado(updatedProduct.getEliminado());
        this.productRepository.save(producto_buscado);
    }

    @Override
    @Transactional
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
    @Transactional
    public List<Product> getProductoByProductType(Long id_product_type)
    {
        List<Product> productos = this.productRepository.findByTypeId(id_product_type);
        return productos;
    }

    @Override
    @Transactional
    public List<HistoricalProductPrice> getPrices(Long id_product, Date desde, Date hasta)
    {
        return this.historicalProductPriceRepository.findAllBetweenDates(id_product, desde, hasta);
    }
    @Override
    @Transactional
    public List<ProductTypeAvgPrice_DTO> getAvgPriceForProductType()
    {
        return this.productRepository.findAllAvgPriceForProductType();
    }

    @Override
    @Transactional
    //7) Obtener todos los productos y su tipo, de un proveedor específico.
    // tal vez haya que meter un DTO
    public List<Product> getBySupplierId(Long id_supplier)
    {
        return this.productRepository.findBySupplierId(id_supplier);
    }


}
