package com.myretail.service;

import com.myretail.exception.Error;
import com.myretail.exception.Errors;
import com.myretail.model.Price;
import com.myretail.model.Product;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Service
public class ProductService {

    @Autowired
    RestTemplate restTemplate;

    @Value("${myretailPrice.URL}")
    String URL;

    @Value("${redskyService.URL}")
    String redSkyURL;

    @Value("${redskyService.params}")
    String params;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public Product getProductById(long productId) throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Errors errors = new Errors();

        Future<Product> productFuture = executorService.submit(() -> {
            logger.info("product price thread");

            Product product = new Product();
            product.setProductId(productId);

            try{
                String jsonResponse = restTemplate.getForObject(redSkyURL + productId + params, String.class);
                adoptProductDetails(jsonResponse, product);
            }
            catch (Exception e){
                logger.error("Not able to get the product details for the given product Id : " + productId);
                errors.getErrorList().add(new Error("404", "Product Details Not Found"));
            }

            return product;
        });

        Future<Price> priceFuture = executorService.submit(() -> {
            logger.info("product details thread");

            Price price = new Price();

            try{
                String jsonResponse = restTemplate.getForObject(URL + productId, String.class);
                adoptProductPrice(jsonResponse, price);
            }
            catch (Exception e){
                logger.error("Not able to get the price details for the given product Id : " + productId);
                errors.getErrorList().add(new Error("404", "Product Price Not Found"));
            }

            return price;
        });

        Product product = productFuture.get();
        Price price = priceFuture.get();
        if(price.getValue() != null){
            product.setCurrentprice(price);
        }
        if(errors.getErrorList().size() >= 1){
            product.setErrors(errors);
        }

        return product;
    }

    private void adoptProductDetails(String jsonResponse, Product product) {

        if(jsonResponse != null) {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            JSONObject productDescription = jsonObject.getJSONObject("product").getJSONObject("item").getJSONObject("product_description");
            if(productDescription != null) {
                product.setName(productDescription.getString("title"));
            }
        }

    }

    private void adoptProductPrice(String jsonResponse, Price price){

        if(jsonResponse != null) {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            price.setValue(jsonObject.getDouble("value"));
            price.setCurrencyCode(jsonObject.getString("currencyCode"));
        }

    }


}
