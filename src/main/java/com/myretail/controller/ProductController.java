package com.myretail.controller;

import com.myretail.exception.MyRetailException;
import com.myretail.model.Product;
import com.myretail.repository.CassandraRepository;
import com.myretail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

@RequestMapping("/products")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProductById(@PathVariable("id") String productId) throws MyRetailException {

        //logger.info("Inside getProductById :  " + productId);

        Product product =  productService.getProductById(productId);
        return new ResponseEntity<Product>(product, HttpStatus.OK);

        //logger.debug(" product Response " + productResponse);

    }

}
