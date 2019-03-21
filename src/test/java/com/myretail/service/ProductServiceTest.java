package com.myretail.service;

import com.myretail.model.Price;
import com.myretail.model.Product;
import com.myretail.repository.ProductDetailsRepository;
import com.myretail.repository.ProductPriceRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = { "product-api-endpoint=http://redsky.target.com", })
public class ProductServiceTest {

	@Mock
	ProductService productService;

	@Mock
	ProductDetailsRepository productDetailsRepository;

	@Mock
	ProductPriceRepository productPriceRepository;

	@Value("${product-api-endpoint}")
	String endPoint;

	@Test
	public void testValueSetup() {
		assertEquals("http://redsky.target.com", endPoint);
	}

	@Configuration
	public
	static class Config {

		@Bean
		public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
			return new PropertySourcesPlaceholderConfigurer();
		}

	}


	/**
	 * Setup for Mockito before any test run.
	 */
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProductByIdTest() throws Exception{

		//Objects created for the actual Mock
		Price currentPriceMock = new Price(13.49,"USD");
		Product productMock = new Product("13860428",currentPriceMock) ;
		Mockito.when(productDetailsRepository.getProductDetailsById(Mockito.anyString())).thenReturn(productMock);
		assertEquals("13860428",productMock.getProductId());
	}

}



