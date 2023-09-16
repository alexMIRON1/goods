package com.test.product.web.controller;

import com.test.product.model.entity.Product;
import com.test.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * this class responsible for endpoints which allowed make some operation with product
 *
 * @author Oleksandr Myronenko
 */
@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    /**
     * allow user add product
     *
     * @param product product to add
     */
    @PostMapping("/add")
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    /**
     * allow user get list of products
     *
     * @return list of {@link Product}
     */
    @GetMapping
    public List<Product> getProducts() {
        return productService.getProducts();
    }
}
