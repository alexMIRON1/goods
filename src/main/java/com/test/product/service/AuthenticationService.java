package com.test.product.service;

import com.test.product.model.entity.Customer;
import com.test.product.model.entity.Role;
import com.test.product.model.repository.CustomerRepository;
import com.test.product.web.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * main business logic that responsible for authentication customers
 *
 * @author Oleksandr Myronenko
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    /**
     * using for creation customer
     *
     * @param customer customer for creation
     */
    public void createCustomer(Customer customer) {
        customer.setCustomerRole(Role.CLIENT);
        customer.setCustomerPassword(passwordEncoder.encode(customer.getCustomerPassword()));
        List<Customer> customers = customerRepository.findAll();
        Long lastId = 0L;
        if (!customers.isEmpty()) {
            lastId = customers
                    .stream()
                    .reduce((first, second) -> second)
                    .orElseThrow().getId();
        }
        customer.setId(lastId + 1);

        customerRepository.save(customer);
        log.info("successfully registered new customer with login " + customer.getCustomerLogin());
    }

    /**
     * using for sign in customer
     *
     * @param customer customer
     * @return {@link CustomerResponse}
     */
    public CustomerResponse signInCustomer(Customer customer) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getCustomerLogin(),
                customer.getCustomerPassword()));
        UserDetails signInUser = userDetailsService.loadUserByUsername(customer.getCustomerLogin());
        String jwtToken = jwtService.generateToken(signInUser);
        return new CustomerResponse(signInUser.getUsername(), jwtToken);
    }

}
