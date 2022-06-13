package com.bd.tpfinal.services;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.repositories.ProductTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired
    ProductTypeRepository productTypeRepository;


    public ProductType createNewProductType(ProductType productType) throws Exception {
        // Verifico que tanto el nombre como la descripcion no esten vacíos
        if (productType.getName().isBlank() ||
                productType.getDescription().isBlank())
            return null;

        // Busco el ProductType en la BD
        ProductType productTypeDB = productTypeRepository.getProductTypeByName(productType.getName());

        // Si el ProductoType existe, retorno null, ya que estaría como duplicado
        if (productTypeDB != null) {
            System.out.println("El ProductType no existe");
            return null;
        }

        // Inicializo los atributos
        productType.setId(null);
        productType.setProducts(null);

        // Grabo el ProductType
        return productTypeRepository.save(productType);
    }

    public List<ProductType> getProductTypeList() throws Exception {
        return productTypeRepository.findAll();
    }

    public List<ProductType> getAveragePriceOfProductsByType() throws Exception {
        List<ProductType> pt = new ArrayList<ProductType>();

        return pt;
    }
}
