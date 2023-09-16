package com.test.product.model.repository;

import com.test.product.model.entity.Order;
import com.test.product.model.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * interface describes working with {@link OrderItem} table
 *
 * @author Oleksandr Myronenko
 */
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    /**
     * using for finding list of {@link OrderItem} by {@link Order}
     *
     * @param order {@link Order}
     * @return list of {@link OrderItem}
     */
    List<OrderItem> findByOrder(Order order);

    /**
     * using for deleting order items by order
     *
     * @param order order
     */
    void deleteByOrder(Order order);
}
