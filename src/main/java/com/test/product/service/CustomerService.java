package com.test.product.service;

import com.test.product.model.entity.Customer;
import com.test.product.model.repository.CustomerRepository;
import com.test.product.service.exception.WrongInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * this class responsible for the main business logic with {}
 *
 * @author Oleksandr Myronenko
 */
@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    /**
     * using for get customer by id
     * @param customerId customer id
     * @return {@link Customer}
     */
    public Customer getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .orElseThrow(() -> new WrongInputException("customer was not found by this id " + customerId));
    }
}
