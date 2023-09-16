package com.test.product.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClientWantedProduct {
    private Long productId;
    private Integer wantedProductQuantity;
    private Long clientId;
}
