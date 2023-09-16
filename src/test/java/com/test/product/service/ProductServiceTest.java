package com.test.product.service;

import com.test.product.model.entity.Product;
import com.test.product.model.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * tests for {@link ProductService}
 *
 * @author Oleksandr Myronenko
 */
@SpringBootTest
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @Test
    void testGetProducts() {
        Product product1 = new Product();
        Product product2 = new Product();
        when(productRepository.findAll()).thenReturn(Stream.of(product1, product2).collect(Collectors.toList()));
        var products = productService.getProducts();

        assertEquals(2, products.size());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testGetProductById() {
        Long productId = 1L;
        Product expectedProduct = new Product();
        expectedProduct.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(expectedProduct));
        Product product = productService.getProductById(productId);

        assertEquals(productId, product.getId());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testGetProductByIdNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void testAddProduct() {
        Product product = new Product();
        productService.addProduct(product);

        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testAddProductNullInput() {
        Product product = null;

        assertThrows(IllegalArgumentException.class, () -> productService.addProduct(product));
        verify(productRepository, times(0)).save(any());
    }

    @Test
    void testDecreaseQuantityProduct() {
        Long productId = 1L;
        Product product = new Product();
        Integer productQuantity = 10;
        product.setId(productId);
        product.setProductQuantity(productQuantity);
        Integer wantedProductQuantity = 5;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.decreaseQuantityProduct(productId, wantedProductQuantity);

        assertEquals(productQuantity - wantedProductQuantity, product.getProductQuantity());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void testDecreaseQuantityProductNotEnoughQuantity() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);
        product.setProductQuantity(5);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        assertThrows(IllegalArgumentException.class, () -> productService.decreaseQuantityProduct(productId, 10));
        verify(productRepository, times(0)).save(product);
    }

    @Test
    void testIncreaseQuantityProduct() {
        Long productId = 1L;
        Product product = new Product();
        Integer productQuantity = 5;
        product.setId(productId);
        product.setProductQuantity(productQuantity);
        Integer wantedProductQuantity = 3;
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.increaseQuantityProduct(productId, wantedProductQuantity);

        assertEquals(productQuantity + wantedProductQuantity, product.getProductQuantity());
        verify(productRepository, times(1)).save(product);
    }
}
