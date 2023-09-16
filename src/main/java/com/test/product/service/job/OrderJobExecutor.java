package com.test.product.service.job;

import com.test.product.model.entity.Order;
import com.test.product.model.entity.OrderStatus;
import com.test.product.service.OrderItemService;
import com.test.product.service.OrderService;
import com.test.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderJobExecutor {
    private final OrderItemService orderItemService;
    private final ProductService productService;
    private final OrderService orderService;

    /**
     * using for check not paid order for deletion by id
     *
     * @param order order for checking
     */
    @Transactional
    public void checkNotPaidOrderForDeletion(Order order) {
        if (order.getOrderStatus() == OrderStatus.CREATED) {
            orderItemService.getOrderItemsByOrder(order)
                    .forEach(orderItem -> productService
                            .increaseQuantityProduct(orderItem.getProduct().getId(), orderItem.getQuantity()));
            log.info("increasing quantity product");
            orderItemService.deleteOrderItemsByOrder(order);
            orderService.deleteOrder(order);
        }
    }
}
