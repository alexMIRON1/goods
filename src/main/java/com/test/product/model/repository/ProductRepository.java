package com.test.product.model.repository;

import com.test.product.model.entity.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * this interface describes working with database and executing simple queries for entity {@link Product}
 *
 * @author Oleksandr Myronenko
 */
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
