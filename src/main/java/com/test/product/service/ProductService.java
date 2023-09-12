package com.test.product.service;

import com.test.product.model.entity.Product;
import com.test.product.model.repository.ProductRepository;
import com.test.product.service.exception.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    public void addProduct(Product product) {
        if (product == null) {
            throw new WrongInputException("inputted product is null ");
        }
        productRepository.save(product);
    }
}
