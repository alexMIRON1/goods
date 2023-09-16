package com.test.product.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * customer response class when happened authentication
 *
 * @author Oleksandr Myronenko
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String customerLogin;
    private String jwtToken;
}
