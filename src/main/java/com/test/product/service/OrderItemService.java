package com.test.product.service;

import com.test.product.model.entity.Order;
import com.test.product.model.entity.OrderItem;
import com.test.product.model.entity.Product;
import com.test.product.model.repository.OrderItemRepository;
import com.test.product.service.exception.WrongInputException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * this class responsible for main business logic for {@link OrderItem}
 *
 * @author Oleksandr Myronenko
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;

    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new WrongInputException("Such order item does not exist"));
    }

    public Long createOrderItem(Order order, Product product, Integer quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItemRepository.save(orderItem);
        log.info("order item was created");
        return orderItem.getId();
    }

    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }
}
