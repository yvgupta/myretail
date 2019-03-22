package com.myretail.service;

import com.myretail.model.Price;
import com.myretail.model.Product;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = { "redskyService.URL=https://redsky.target.com/v2/pdp/tcin/", "redskyService.params=?excludes=taxonomy,price,promotion,bulk_ship,rating_and_review_reviews,rating_and_review_statistics,question_answer_statistics", "myretailPrice.URL=http://localhost:8080/productPrice/", })
public class ProductServiceTest {

	@Value("${myretailPrice.URL}")
	String URL;

	@Value("${redskyService.URL}")
	String redSkyURL;

	@Value("${redskyService.params}")
	String params;

	@Spy
	@InjectMocks
	RestTemplate restTemplate = new RestTemplate();

	@Test
	public void testValueSetup() {
		assertEquals("http://redsky.target.com", redSkyURL);
	}

	@Configuration
	public
	static class Config {
		@Bean
		public static PropertySourcesPlaceholderConfigurer propertiesResolver() {
			return new PropertySourcesPlaceholderConfigurer();
		}
	}

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProductByIdTest() throws Exception{

		Price currentPriceMock = new Price(13.49,"USD");
		Product productMock = new Product(13860428,currentPriceMock) ;
		String jsonResponse = "{\"product\":{\"item\":{\"product_description\":{\"title\":\"The Big Lebowski (Blu-ray)\"}}}}";
		Mockito.when(restTemplate.getForObject(redSkyURL + "13860428" + params, String.class)).thenReturn(jsonResponse);
		//String jsonPriceResponse = "{\"price\":{\"value\",\"35\",\"currencyCode\":\"USD\"}}";
		//Mockito.when(restTemplate.getForObject(URL, String.class)).thenReturn(jsonResponse);
		assertEquals(13860428,productMock.getProductId());
		//assertEquals("The Big Lebowski (Blu-ray)",productMock.getName());
	}

}



