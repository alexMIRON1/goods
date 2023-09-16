package com.test.product.model.repository;

import com.test.product.model.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * interface describes working with {@link Customer} table
 *
 * @author Oleksandr Myronenko
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    /**
     * using for getting customer by login
     *
     * @param customerLogin login
     * @return {@link Customer}
     */
    Optional<Customer> findCustomerByCustomerLogin(String customerLogin);
}
