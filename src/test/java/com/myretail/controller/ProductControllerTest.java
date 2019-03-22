package com.myretail.controller;

import com.myretail.exception.Error;
import com.myretail.exception.Errors;
import com.myretail.model.Price;
import com.myretail.model.Product;
import com.myretail.service.ProductService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.skyscreamer.jsonassert.JSONAssert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(value = ProductController.class, secure = false)
public class ProductControllerTest {

 Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	ProductService productService;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void getProductByIdTest() throws Exception{

		Product product = new Product();
		Price currentPrice = new Price();
		product.setProductId(13860428);
		product.setName("The Big Lebowski (Blu-ray)");
		currentPrice.setCurrencyCode("USD");
		currentPrice.setValue(13.49);
		product.setCurrentprice(currentPrice);

		Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(product);
		String prod_url = "/products/13860428";
		RequestBuilder builder = MockMvcRequestBuilders.get(prod_url).accept(MediaType.APPLICATION_JSON_VALUE);

		MvcResult actual = mockMvc.perform(builder).andReturn();

		String expected = "{\"id\":\"13860428\",\"name\":\"The Big Lebowski (Blu-ray)\",\"current_price\":{\"value\": 13.49,\"currency_code\":\"USD\"}}";

		JSONAssert.assertEquals(expected, actual.getResponse().getContentAsString(), false);
	}

    @Test
    public void getProductByWrongIdTest() throws Exception{

        Product product = new Product();
        product.setProductId(16483589);
        Errors errors = new Errors();
        errors.getErrorList().add(new Error("404", "Product Details Not found"));
        product.setErrors(errors);

        Mockito.when(productService.getProductById(Mockito.anyLong())).thenReturn(product);
        String prod_url = "/products/13860428";
        RequestBuilder builder = MockMvcRequestBuilders.get(prod_url).accept(MediaType.APPLICATION_JSON_VALUE);

        MvcResult actual = mockMvc.perform(builder).andReturn();

        String expected = "{productId: 16483589, errors: { errorList: [ { errorCode: \"404\", errorDescription: \"Product Details Not found\"}]}}";

        JSONAssert.assertEquals(expected, actual.getResponse().getContentAsString(), false);

    }
}
