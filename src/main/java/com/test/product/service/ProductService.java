package com.test.product.service;

import com.test.product.model.entity.Product;
import com.test.product.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * using for getting list of products
     *
     * @return list of {@link Product}
     */
    public List<Product> getProducts() {
        return StreamSupport.stream(productRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    /**
     * using for getting product by id
     *
     * @param productId product id
     * @return {@link Product}
     */
    public Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(() -> new NoSuchElementException("Such product does not exist"));
    }

    /**
     * using for add new product
     *
     * @param product product to add
     */
    public void addProduct(Product product) {
        if (product == null) {
            log.debug("Wrong input exception -> {}", product);
            throw new IllegalArgumentException("inputted product is null ");
        }
        productRepository.save(product);
        log.info("product was saved -> {}", product.getProductName());
    }

    /**
     * using for decreasing quantity of product by wanted product quantity for some product
     *
     * @param productId             product id for decrease
     * @param wantedProductQuantity wanted product quantity
     */
    public void decreaseQuantityProduct(Long productId, Integer wantedProductQuantity) {
        Product product = getProductById(productId);
        log.info("getting product by id -> {}", product);
        Integer productQuantity = product.getProductQuantity();
        if (productQuantity < wantedProductQuantity) {
            log.debug("product entity -> {}, less than wanted product quantity -> {}", productQuantity,
                    wantedProductQuantity);
            throw new IllegalArgumentException("We have not so many quantity as you want, our quantity -> " + productQuantity);
        }
        log.info("current product quantity -> {}", productQuantity);
        product.setProductQuantity(productQuantity - wantedProductQuantity);
        productRepository.save(product);
        log.info("product entity after decreasing -> {}", product.getProductQuantity());
    }

    public void increaseQuantityProduct(Long productId, Integer productQuantity) {
        Product product = getProductById(productId);
        Integer currentProductQuantity = product.getProductQuantity();
        log.info("current product quantity -> {}", currentProductQuantity);
        product.setProductQuantity(productQuantity + currentProductQuantity);
        productRepository.save(product);
        log.info("product entity after decreasing -> {}", product.getProductQuantity());
    }
}
