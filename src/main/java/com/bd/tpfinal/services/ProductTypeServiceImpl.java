package com.bd.tpfinal.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.model.dto.ProductAvgDTO;
import com.bd.tpfinal.model.projections.ProductAndType;
import com.bd.tpfinal.repositories.implementations.ProductAVGRepository;
import com.bd.tpfinal.repositories.implementations.ProductRepository;
import com.bd.tpfinal.repositories.implementations.ProductTypeRepository;

@Service
public class ProductTypeServiceImpl implements ProductTypeService {
    @Autowired ProductTypeRepository productTypeRepository;
    @Autowired ProductRepository productRepository;
    @Autowired ProductAVGRepository productAVGRepository;

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

    public List<ProductAvgDTO> getAveragePriceOfProducts() throws Exception {
    	List<ProductType> allTypes = productTypeRepository.findAll();
    	
        return allTypes.stream().map(type -> {
        	List<ProductAndType> products = productRepository.findByTypeId(type.getId());
            float sum = products.stream().map(prod -> prod.getPrice()).reduce(Float::sum).orElse(0f);
            ProductAvgDTO dto = new ProductAvgDTO(type.getId(), type.getName(), (sum / products.size()));
            return dto;
        }).collect(Collectors.toList());
    	
    }
}
