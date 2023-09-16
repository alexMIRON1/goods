package com.test.product.service;

import com.test.product.model.entity.Customer;
import com.test.product.model.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * class responsible for implementation {@link UserDetailsService}
 *
 * @author Oleksandr Myronenko
 */
@Service("userDetailsService")
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final CustomerRepository customerRepository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        Customer customer = customerRepository.findCustomerByCustomerLogin(s)
                .orElseThrow(() -> new NoSuchElementException("user with this login " + s + " does not exist"));
        List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" +
                customer.getCustomerRole().name()));
        return new org.springframework.security.core.userdetails.User(customer.getCustomerLogin(),
                customer.getCustomerPassword(), authorities);

    }
}