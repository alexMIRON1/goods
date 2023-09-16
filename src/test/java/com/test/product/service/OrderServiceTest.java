package com.test.product.service;

import com.test.product.model.entity.Order;
import com.test.product.model.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * tests for {@link OrderService} class
 *
 * @author Oleksandr Myronenko
 */
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @Test
    void testDeleteOrder() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);
        orderService.deleteOrder(order);
        verify(orderRepository, times(1)).deleteById(orderId);
    }
}