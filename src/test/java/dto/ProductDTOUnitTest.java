package dto;

import com.bd.tpfinal.DTOs.ProductDTO;
import com.bd.tpfinal.DTOs.ProductTypeDTO;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class ProductDTOUnitTest {
   private ModelMapper modelMapper = new ModelMapper();

   @Test
    public void convertProductEntityToProductDTO() {
        Product product = new Product();
        product.setName("Cafe");
        product.setDescription("A coffee description");
        product.setWeight(32.0f);
        product.setPrice(20.0f);
        ProductType prodType = new ProductType();
        prodType.setName("Bebidas");
        prodType.setDescription("Bebidas calientes");
        product.setType(Arrays.asList(prodType));

        ProductDTO productDTO = modelMapper.map(product, ProductDTO.class);

        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getDescription(), productDTO.getDescription());
        assertEquals(product.getWeight(), productDTO.getWeight(), 0);
        assertEquals(product.getPrice(), productDTO.getPrice(), 0);
        assertEquals(product.getType().size(), productDTO.getType().size());
        assertEquals(product.getType().get(0).getName(), productDTO.getType().get(0).getName());
    }

    @Test
    public void convertProductDTOToProductEntity() {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("Cafe");
        productDTO.setDescription("A coffee description");
        productDTO.setWeight(32.0f);
        productDTO.setPrice(20.0f);
        ProductTypeDTO prodTypeDTO = new ProductTypeDTO();
        prodTypeDTO.setName("Bebidas");
        prodTypeDTO.setDescription("Bebidas calientes");
        productDTO.setType(Arrays.asList(prodTypeDTO));

        Product product = modelMapper.map(productDTO, Product.class);

        assertEquals(productDTO.getName(),product.getName());
        assertEquals(productDTO.getDescription(),product.getDescription());
        assertEquals(productDTO.getWeight(),product.getWeight(), 0);
        assertEquals(productDTO.getPrice(),product.getPrice(), 0);
        assertEquals(productDTO.getType().size(), product.getType().size());
        assertEquals( productDTO.getType().get(0).getName(), product.getType().get(0).getName());
    }
}
