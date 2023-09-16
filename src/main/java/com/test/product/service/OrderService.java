package com.test.product.service;

import com.test.product.model.entity.Order;
import com.test.product.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * this class indirection between model layer and service layer, and responsible for main logic with {@link Order}
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepository orderRepository;

    /**
     * using for deletion order by order {@link Order}
     *
     * @param order order to deletion
     */
    public void deleteOrder(Order order) {
        orderRepository.deleteById(order.getId());
        log.info("order was deleted");
    }
}
