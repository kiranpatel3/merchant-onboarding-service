package com.kp.merchantonboardingservice.service;

import com.kp.merchantonboardingservice.models.Product;
import com.kp.merchantonboardingservice.models.ProductListResponse;


public interface ProductService {

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(long productId);

    Product getProduct(long productId);

    ProductListResponse getAllProducts(int pageNumber, int size);

    ProductListResponse getAllProductsForMerchant(int merchantId, int pageNumber, int size);

}
