package com.test.product.service;

import com.test.product.model.entity.Order;
import com.test.product.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * this class responsible for main business logic for {@link Order}
 *
 * @author Oleksandr Myronenko
 */
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void createOrder(){
        //TODO: make logic for creating order
    }
    public void payOrder(){
        //TODO:make logic for paying order
    }
}
