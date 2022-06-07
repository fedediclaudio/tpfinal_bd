package com.bd.tpfinal;

import com.bd.tpfinal.controllers.ProductController;
import com.bd.tpfinal.model.HistoricalProductPrice;
import com.bd.tpfinal.model.Product;
import com.bd.tpfinal.model.ProductType;
import com.bd.tpfinal.repositories.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class TpfinalApplicationTests {

	@Autowired
	private ProductController productController;
	@Autowired
	MockMvc mockMvc;
	@Autowired
	ObjectMapper mapper;
	@MockBean
	ProductRepository productMockRepository;

	ProductType productType = new ProductType();
	HistoricalProductPrice historicalProductPrice = new HistoricalProductPrice();
	List<ProductType> productTypes = new ArrayList<>();
	List<HistoricalProductPrice> historicalProductPrices = new ArrayList<>();


	@Test
	void contextLoads() {
		assertThat(productController).isNotNull();
	}

	@Test
	public void createProductsTest() throws Exception {
		productTypes.add(productType);
		historicalProductPrices.add(historicalProductPrice);
		Product product = new Product(
				"Café",
				14.0f,
				20.0f,
				"Cafe instantaneo",
				productTypes,
				historicalProductPrices
		);

		List<Product> products = new ArrayList<>();
		products.add(product);
		Mockito.when(productMockRepository.findAll()).thenReturn(products);
	}
}
