package com.test.product.service;

import com.test.product.model.entity.Customer;
import com.test.product.model.repository.CustomerRepository;
import com.test.product.service.exception.WrongInputException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * test for  {@link CustomerService} class
 *
 * @author Oleksandr Myronenko
 */
@SpringBootTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testGetCustomerById() {
        Long customerId = 1L;
        Customer expectedCustomer = new Customer();
        expectedCustomer.setId(customerId);

        expectedCustomer.setCustomerFullName("testuser");
        expectedCustomer.setCustomerLogin("user");
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(expectedCustomer));

        Customer result = customerService.getCustomerById(customerId);

        // Assert
        assertEquals(customerId, result.getId());
        assertEquals(expectedCustomer.getCustomerLogin(), result.getCustomerLogin());
        assertEquals(expectedCustomer.getCustomerFullName(), result.getCustomerFullName());
        verify(customerRepository, times(1)).findById(customerId);
    }

    @Test
    void testGetCustomerByIdNotFound() {
        Long customerId = 1L;

        when(customerRepository.findById(customerId)).thenReturn(Optional.empty());

        assertThrows(WrongInputException.class, () -> customerService.getCustomerById(customerId));
        verify(customerRepository, times(1)).findById(customerId);
    }
}