package com.test.product.service.job;

import com.test.product.model.entity.Order;
import com.test.product.service.exception.ScheduleFailedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
public class OrderJobScheduler {
    private final ScheduledExecutorService threadPoolExecutor;
    private final OrderJobExecutor orderJobExecutor;
    @Value("${product.order-job.core-pool-size:4}")
    private int corePoolSize;
    @Value("${product.order.delete-after-creation:10L}")
    private Long delay;

    public OrderJobScheduler(OrderJobExecutor orderJobExecutor) {
        this.threadPoolExecutor = new ScheduledThreadPoolExecutor(corePoolSize);
        this.orderJobExecutor = orderJobExecutor;
    }

    /**
     * using for scheduling to check not paid order by order id
     *
     * @param order order for scheduling
     */
    public void scheduleCheckNotPaidOrder(Order order) {
        try {
            threadPoolExecutor
                    .schedule(() -> orderJobExecutor.checkNotPaidOrderForDeletion(order), 1, TimeUnit.MINUTES);
            log.info("job for checking not payed order was planned ");
        } catch (Exception e) {
            log.error("something went wrong during the scheduling");
            throw new ScheduleFailedException("something went wrong during scheduling");
        }

    }
//
//    /**
//     * using for check not paid order for deletion by id
//     *
//     * @param order order for checking
//     */
//    private void checkNotPaidOrderForDeletion(Order order) {
//        if (order.getOrderStatus() == OrderStatus.CREATED) {
//            getOrderItemsByOrder(order)
//                    .forEach(orderItem -> productService
//                            .increaseQuantityProduct(orderItem.getProduct().getId(), orderItem.getQuantity()));
//            log.info("increasing quantity product");
//            deleteOrder(order);
//        }
//    }
//
//    private void deleteOrder(Order order) {
//        orderRepository.deleteById(order.getId());
//        log.info("order was deleted");
//    }
//
//    private List<OrderItem> getOrderItemsByOrder(Order order) {
//        return orderItemService.getOrderItemsByOrder(order);
//    }
}
