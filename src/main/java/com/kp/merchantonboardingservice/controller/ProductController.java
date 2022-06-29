package com.kp.merchantonboardingservice.controller;

import com.kp.merchantonboardingservice.models.Product;
import com.kp.merchantonboardingservice.models.ProductListResponse;
import com.kp.merchantonboardingservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(product));
    }

    @GetMapping("/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") int productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity deleteProduct(@PathVariable("productId") int productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/page/{pageNumber}/size/{pageSize}")
    public ResponseEntity<ProductListResponse> getProducts(@PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
        return ResponseEntity.ok(productService.getAllProducts(pageNumber, pageSize));
    }

    @GetMapping("/merchant/{merchantId}/page/{pageNumber}/size/{pageSize}")
    public ResponseEntity<ProductListResponse> getProductsForMerchant(@PathVariable("merchantId") int merchantId, @PathVariable("pageNumber") int pageNumber, @PathVariable("pageSize") int pageSize) {
        return ResponseEntity.ok(productService.getAllProductsForMerchant(merchantId, pageNumber, pageSize));
    }
}
