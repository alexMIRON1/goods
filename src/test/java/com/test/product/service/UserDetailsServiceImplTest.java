package com.test.product.service;

import com.test.product.model.entity.Customer;
import com.test.product.model.entity.Role;
import com.test.product.model.repository.CustomerRepository;
import com.test.product.service.exception.WrongInputException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * test for {@link UserDetailsServiceImpl} class
 *
 * @author Oleksandr Myronenko
 */
@SpringBootTest
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @MockBean
    private CustomerRepository customerRepository;

    @Test
    void testLoadUserByUsername() {
        String username = "testuser";
        Customer customer = new Customer();
        customer.setCustomerLogin(username);
        customer.setCustomerPassword("password");
        customer.setCustomerRole(Role.CLIENT);

        when(customerRepository.findCustomerByCustomerLogin(username)).thenReturn(Optional.of(customer));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals("ROLE_CLIENT")));
        verify(customerRepository, times(1)).findCustomerByCustomerLogin(username);
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        String username = "testuser";

        when(customerRepository.findCustomerByCustomerLogin(username)).thenReturn(Optional.empty());

        assertThrows(WrongInputException.class, () -> userDetailsService.loadUserByUsername(username));
        verify(customerRepository, times(1)).findCustomerByCustomerLogin(username);
    }
}
