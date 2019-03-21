package com.myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Product {

    String productId;

    String name;

    Price currentprice;

    public Product() {
    }

    public Product(String productId, Price currentprice) {
        this.productId = productId;
        this.currentprice = currentprice;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Price getCurrentprice() {
        return currentprice;
    }

    public void setCurrentprice(Price currentprice) {
        this.currentprice = currentprice;
    }

    @Override
    public String toString() {
        return "ProductResponse [productId=" + productId + ", name=" + name + ", currentprice=" + currentprice.toString() + "]";
    }

}
