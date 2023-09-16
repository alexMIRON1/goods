package com.test.product.web.controller;

import com.test.product.model.entity.Customer;
import com.test.product.service.AuthenticationService;
import com.test.product.web.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * provides authentication endpoints
 *
 * @author Oleksandr Myronenko
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public void registerUser(@RequestBody Customer customer) {
        authenticationService.createCustomer(customer);
    }

    @GetMapping("/signIn")
    public CustomerResponse signIn(@RequestBody Customer customer) {
        return authenticationService.signInCustomer(customer);
    }

}
