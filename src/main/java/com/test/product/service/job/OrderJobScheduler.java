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
                    .schedule(() -> orderJobExecutor.checkNotPaidOrderForDeletion(order), delay, TimeUnit.MINUTES);
            log.info("job for checking not payed order was planned ");
        } catch (RuntimeException e) {
            log.error("something went wrong during the scheduling");
            throw new ScheduleFailedException("something went wrong during scheduling");
        }

    }
}
