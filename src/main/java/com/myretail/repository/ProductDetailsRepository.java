package com.myretail.repository;

import com.myretail.exception.MyRetailException;
import com.myretail.model.Product;

public interface ProductDetailsRepository {

    public Product getProductDetailsById(String productId) throws MyRetailException;

}
