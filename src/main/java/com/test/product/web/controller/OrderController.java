package com.test.product.web.controller;

import com.test.product.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * this class responsible for endpoint which allowed make operations with order
 *
 * @author Oleksandr Myronenko
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public void createOrder() {
        orderService.createOrder();
    }

    @PutMapping
    public void payOrder() {
        orderService.payOrder();
    }
}
