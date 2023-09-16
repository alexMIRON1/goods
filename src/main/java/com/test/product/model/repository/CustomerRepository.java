package com.test.product.model.repository;

import com.test.product.model.entity.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * interface describes working with {@link Customer} table
 *
 * @author Oleksandr Myronenko
 */
@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
}
