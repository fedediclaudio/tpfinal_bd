package com.bd.tpfinal.services;

import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;

import java.util.Date;
import java.util.List;

public interface ProductService {
    List<Product> getProductsAndTypeBySupplierId(long supplier_id);
    List<HistoricalProductPrice> getPreciosProductoBetweenToFechas(Long product_id, Date from_date, Date to_date);
}
