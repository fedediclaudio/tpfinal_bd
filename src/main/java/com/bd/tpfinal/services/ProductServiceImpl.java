package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.repositories.HistoricalProductPriceRepository;
import com.bd.tpfinal.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private HistoricalProductPriceRepository historicalProductPriceRepository;

    @Override
    public List<Product> getProductsAndTypeBySupplierId(long supplier_id) {
        return productRepository.findBySupplierId(supplier_id);
    }

    @Override
    public List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(Long product_id, Date start_date, Date finish_date) {
       return historicalProductPriceRepository.findByProductIdAndStartDateGreaterThanAndFinishDateLessThan(product_id, start_date, finish_date);
    }
}
