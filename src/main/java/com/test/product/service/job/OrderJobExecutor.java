package com.test.product.service.job;

import com.test.product.model.entity.Order;
import com.test.product.model.entity.OrderItem;
import com.test.product.model.entity.OrderStatus;
import com.test.product.model.repository.OrderRepository;
import com.test.product.service.OrderItemService;
import com.test.product.service.ProductService;
import com.test.product.service.exception.ScheduleFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * class responsible for scheduling and executing jobs for {@link Order}
 *
 * @author Oleksandr Myronenko
 */
@Component
@Slf4j
public class OrderJobExecutor {
    private final ScheduledExecutorService threadPoolExecutor;
    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderItemService orderItemService;
    @Value("${product.order-job.core-pool-size:4}")
    private int corePoolSize;

    public OrderJobExecutor(OrderRepository orderRepository, ProductService productService,
                            OrderItemService orderItemService) {
        this.threadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize);
        this.orderRepository = orderRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
    }

    /**
     * using for scheduling to check not paid order by order id
     *
     * @param order order for scheduling
     */
    public void scheduleCheckNotPaidOrder(Order order) {
        try {
            threadPoolExecutor
                    .schedule(() -> checkNotPaidOrderForDeletion(order), 10L, TimeUnit.MINUTES);
            log.info("job for checking not payed order was planned ");
        } catch (Exception e) {
            log.error("something went wrong during the scheduling");
            throw new ScheduleFailedException("something went wrong during scheduling");
        }

    }

    /**
     * using for check not paid order for deletion by id
     *
     * @param order order for checking
     */
    private void checkNotPaidOrderForDeletion(Order order) {
        if (order.getOrderStatus() == OrderStatus.CREATED) {
            getOrderItemsByOrder(order)
                    .forEach(orderItem -> productService
                            .increaseQuantityProduct(orderItem.getProduct().getId(), orderItem.getQuantity()));
            log.info("increasing quantity product");
            deleteOrder(order);
        }
    }

    private void deleteOrder(Order order) {
        orderRepository.deleteById(order.getId());
        log.info("order was deleted");
    }

    private List<OrderItem> getOrderItemsByOrder(Order order) {
        return orderItemService.getOrderItemsByOrder(order);
    }
}
