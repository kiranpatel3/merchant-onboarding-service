package com.kp.merchantonboardingservice.utils;

import com.kp.merchantonboardingservice.models.Product;
import com.kp.merchantonboardingservice.models.ProductListResponse;
import org.springframework.data.domain.Page;

public class ProductsUtils {
    public static ProductListResponse getProductListResponse(Page<Product> productPage, int currentPage) {
        ProductListResponse response = new ProductListResponse();
        response.setProducts(productPage.getContent());
        response.setCurrentPage(currentPage);
        response.setTotalProductsCount(productPage.getTotalElements());
        response.setTotalPage(productPage.getTotalPages());
        return response;
    }
}
