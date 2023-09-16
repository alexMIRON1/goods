package com.test.product.web.controller;

import com.test.product.service.OrderTransactionService;
import com.test.product.web.dto.ClientWantedProduct;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * this class responsible for endpoint which allowed make operations with order
 *
 * @author Oleksandr Myronenko
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderTransactionService orderTransactionService;

    @PostMapping
    public Long createOrder(@RequestBody List<ClientWantedProduct> clientWantedProducts) {
        return orderTransactionService.createOrder(clientWantedProducts);
    }

    @PutMapping("/{orderId}")
    public void payOrder(@PathVariable Long orderId) {
        orderTransactionService.payOrderById(orderId);
    }
}
