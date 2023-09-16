package com.test.product.service;

import com.test.product.model.entity.Order;
import com.test.product.model.entity.OrderItem;
import com.test.product.model.entity.Product;
import com.test.product.model.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

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

    /**
     * using for getting order item by order item id
     *
     * @param orderItemId order item id
     * @return order item
     */
    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new NoSuchElementException("Such order item does not exist"));
    }

    /**
     * using for creation order item
     *
     * @param order    {@link Order}
     * @param product  {@link Product}
     * @param quantity quantity of product
     * @return order item id
     */
    public Long createOrderItem(Order order, Product product, Integer quantity) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setProduct(product);
        orderItem.setQuantity(quantity);
        orderItemRepository.save(orderItem);
        log.info("order item was created");
        return orderItem.getId();
    }

    /**
     * using for getting order items by order id
     *
     * @param order order
     * @return list of {@link OrderItem}
     */
    public List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemRepository.findByOrder(order);
    }

    /**
     * using for deletion order items by order
     *
     * @param order {@link Order}
     */
    public void deleteOrderItemsByOrder(Order order) {
        orderItemRepository.deleteByOrder(order);
        log.info("order items was deleted");
    }
}
