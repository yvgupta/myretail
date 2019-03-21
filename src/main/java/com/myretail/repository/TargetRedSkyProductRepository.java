package com.myretail.repository;

import com.myretail.exception.MyRetailException;
import com.myretail.model.Product;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

@Repository
public class TargetRedSkyProductRepository implements ProductDetailsRepository {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RestTemplate restTemplate;

    @Value("${operations.serviceURL}")
    String serviceURL;

    public Product getProductDetailsById(String productId) throws Exception {
        logger.info("Inside getProductById :  " + productId);

        String jsonResponse = restTemplate.getForObject(serviceURL, String.class);
        String productName = null;

        if(jsonResponse != null) {
            JSONObject jsonObject=new JSONObject(jsonResponse);
            logger.debug("JSON Response from Remote Client  :" + jsonResponse.toString());

            if(jsonObject.getJSONObject("product").getJSONObject("item").getJSONObject("product_description") != null) {
                JSONObject productDescription = jsonObject.getJSONObject("product").getJSONObject("item").getJSONObject("product_description");
                productName = productDescription.getString("title");
            }
            else {
                logger.debug("Product title JSON value Unavailable in Product API");
                throw new MyRetailException(HttpStatus.NO_CONTENT.value() ,"The title does not exists for the product" );
            }
        }

        Product product = new Product();
        product.setName(productName );

        //logger.debug(" product Response " + productResponse);
        return product;
    }
}
