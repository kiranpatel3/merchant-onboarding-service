package com.kp.merchantonboardingservice.service.impl;

import com.kp.merchantonboardingservice.models.Product;
import com.kp.merchantonboardingservice.models.ProductListResponse;
import com.kp.merchantonboardingservice.respository.ProductRepository;
import com.kp.merchantonboardingservice.service.ProductService;
import com.kp.merchantonboardingservice.utils.ProductsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public Product createProduct(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    private void validateProduct(Product product) {
        if (product == null
                || StringUtils.isEmpty(product.getName())
                || product.getPrice() == null
                || Double.isNaN(product.getPrice())
                || product.getMerchant() == null) {
            throw new IllegalArgumentException("Product is invalid");
        }
    }

    @Override
    public Product updateProduct(Product product) {
        validateProduct(product);
        validateProductAvailability(product.getId());
        return productRepository.save(product);
    }

    private void validateProductAvailability(long productId) {
        boolean isProductAvailable = productRepository.findById(productId).isPresent();
        if (!isProductAvailable) {
            throw new IllegalArgumentException("Product with " + productId + " not available");
        }
    }

    @Override
    public void deleteProduct(long productId) {
        validateProductAvailability(productId);
        productRepository.deleteById(productId);
    }

    @Override
    public Product getProduct(long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("Product with " + productId + " not available")
        );
    }

    @Override
    public ProductListResponse getAllProducts(int pageNumber, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(pageNumber);
        Page<Product> productPage = productRepository.findAll(pageable);
        return ProductsUtils.getProductListResponse(productPage, pageNumber);
    }

    @Override
    public ProductListResponse getAllProductsForMerchant(int merchantId, int pageNumber, int size) {
        Pageable pageable = Pageable.ofSize(size).withPage(pageNumber);
        Page<Product> productPage = productRepository.findAllByMerchant(merchantId, pageable);
        return ProductsUtils.getProductListResponse(productPage, pageNumber);
    }
}
