package com.myretail.service;

import com.myretail.exception.MyRetailException;
import com.myretail.model.Product;
import com.myretail.repository.CassandraRepository;
import com.myretail.repository.ProductDetailsRepository;
import com.myretail.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductDetailsRepository detailsRepository;

    @Autowired
    ProductPriceRepository priceRepository;

    public Product getProductById(String productId) throws MyRetailException {
        Product product = detailsRepository.getProductDetailsById("100");
        product.setProductId(priceRepository.getProductPrice());

        return product;
    }

}
