package com.test.product.service;

import com.test.product.model.entity.Customer;
import com.test.product.model.entity.Role;
import com.test.product.model.repository.CustomerRepository;
import com.test.product.web.dto.CustomerResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * testing class for {@link AuthenticationService} class
 *
 * @author Oleksandr Myronenko
 */
@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;


    @Test
    void testCreateCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerLogin("testuser");
        customer.setCustomerPassword("password");
        customer.setId(1L);
        List<Customer> customers = new ArrayList<>();
        customers.add(customer);

        when(customerRepository.findAll()).thenReturn(customers);
        when(customerRepository.save(any(Customer.class))).thenReturn(customer);

        authenticationService.createCustomer(customer);

        assertEquals(Role.CLIENT, customer.getCustomerRole());
        verify(passwordEncoder, times(1)).encode("password");
        verify(customerRepository, times(1)).findAll();
        verify(customerRepository, times(1)).save(any(Customer.class));
    }

    @Test
    void testSignInCustomer() {
        // Arrange
        Customer customer = new Customer();
        customer.setCustomerLogin("testuser");
        customer.setCustomerPassword("password");

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "testuser", "encodedPassword", new ArrayList<>()
        );

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);
        when(jwtService.generateToken(userDetails)).thenReturn("jwtToken");

        // Act
        CustomerResponse response = authenticationService.signInCustomer(customer);

        // Assert
        assertEquals("testuser", response.getCustomerLogin());
        assertEquals("jwtToken", response.getJwtToken());
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
        verify(jwtService, times(1)).generateToken(userDetails);
    }
}