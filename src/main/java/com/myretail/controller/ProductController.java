package com.myretail.controller;

import com.myretail.model.Product;
import com.myretail.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/products")
@RestController
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping(value = "/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    public Product getProductById(@PathVariable("id") long productId) throws Exception {

        Product product =  productService.getProductById(productId);

        return product;

    }

}
