package com.myretail.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.myretail.exception.Errors;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Product {

    private long productId;

    private String name;

    private Price currentprice;

    private Errors errors;

    public Product() {
    }

    public Product(long productId, Price currentprice) {
        this.productId = productId;
        this.currentprice = currentprice;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
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

    public Errors getErrors() {
        return errors;
    }

    public void setErrors(Errors errors) {
        this.errors = errors;
    }

    @Override
    public String toString() {
        return "ProductResponse [productId=" + productId + ", name=" + name + ", currentprice=" + currentprice.toString() + "]";
    }

}
