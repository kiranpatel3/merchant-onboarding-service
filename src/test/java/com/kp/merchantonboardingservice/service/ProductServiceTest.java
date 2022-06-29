package com.kp.merchantonboardingservice.service;

import com.kp.merchantonboardingservice.models.Merchant;
import com.kp.merchantonboardingservice.models.Product;
import com.kp.merchantonboardingservice.models.ProductListResponse;
import com.kp.merchantonboardingservice.respository.ProductRepository;
import com.kp.merchantonboardingservice.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @InjectMocks
    ProductServiceImpl productService;

    @Mock
    ProductRepository productRepository;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }
    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }
    @Test
    void createProduct() {
        Product product = createTestProduct();
        Product expectedProduct = createTestProduct();
        expectedProduct.setId(1);
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        product = productService.createProduct(product);
        assertNotNull(product);
        assertEquals(expectedProduct.getId(), product.getId());
    }

    @Test
    void createProductValidationError() {
        Product product = createTestProduct();
        product.setMerchant(null);
        assertThrows(IllegalArgumentException.class , () -> {
            productService.createProduct(product);
        });
    }

    private Product createTestProduct() {
        Merchant merchant = new Merchant();
        merchant.setId(1);
        merchant.setName("test-merchant");
        merchant.setStatus("ACTIVE");
        Product product = new Product();
        product.setName("test-product");
        product.setPrice(10.0);
        product.setMerchant(merchant);
        return product;
    }

    @Test
    void updateProduct() {
        Product product = createTestProduct();
        product.setName("updated-name");
        Product expectedProduct = createTestProduct();
        expectedProduct.setId(1);
        expectedProduct.setName(product.getName());
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(createTestProduct()));
        Mockito.when(productRepository.save(any(Product.class))).thenReturn(expectedProduct);
        product = productService.updateProduct(product);
        assertNotNull(product);
        assertEquals(expectedProduct.getName(), product.getName());
    }

    @Test
    void deleteProduct() {
        Mockito.doNothing().when(productRepository).deleteById(anyLong());
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(createTestProduct()));
        productService.deleteProduct(1);
    }

    @Test
    void getProduct() {
        Product expectedProduct = createTestProduct();
        expectedProduct.setId(1);
        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.of(expectedProduct));
        Product product = productService.getProduct(1);
        assertNotNull(product);
        assertEquals(1, product.getId());
    }
    @Test
    void getProductNotFound() {

        Mockito.when(productRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> {
            productService.getProduct(1);
        });
    }

    @Test
    void getAllProducts() {
        List<Product> productList = Arrays.asList(
                createTestProduct(),
                createTestProduct()
        );
        Pageable pageable = Pageable.ofSize(20).withPage(0);
        Page<Product> productPage = new PageImpl<>(productList, pageable, 2);
        Mockito.when(productRepository.findAll(any(Pageable.class))).thenReturn(productPage);
        ProductListResponse productListResponse = productService.getAllProducts(0, 20);
        assertNotNull(productListResponse);
        assertNotNull(productListResponse.getProducts());
        assertEquals(2, productListResponse.getProducts().size());
        assertEquals(2, productListResponse.getTotalProductsCount());
    }

    @Test
    void getAllProductsForMerchant() {
        List<Product> productList = Arrays.asList(
                createTestProduct(),
                createTestProduct()
        );
        Pageable pageable = Pageable.ofSize(20).withPage(0);
        Page<Product> productPage = new PageImpl<>(productList, pageable, 2);
        Mockito.when(productRepository.findAllByMerchant(anyLong(),any(Pageable.class))).thenReturn(productPage);
        ProductListResponse productListResponse = productService.getAllProductsForMerchant(1,0, 20);
        assertNotNull(productListResponse);
        assertNotNull(productListResponse.getProducts());
        assertEquals(2, productListResponse.getProducts().size());
        assertEquals(2, productListResponse.getTotalProductsCount());
    }
}