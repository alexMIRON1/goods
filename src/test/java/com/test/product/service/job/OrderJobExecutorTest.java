package com.test.product.service.job;

import com.test.product.model.entity.Order;
import com.test.product.model.entity.OrderItem;
import com.test.product.model.entity.OrderStatus;
import com.test.product.model.entity.Product;
import com.test.product.service.OrderItemService;
import com.test.product.service.OrderService;
import com.test.product.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * tests for {@link OrderJobExecutor} class
 *
 * @author Oleksandr Myronenko
 */
@SpringBootTest
class OrderJobExecutorTest {
    @Autowired
    private OrderJobExecutor orderJobExecutor;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderService orderService;


    @Test
    void testCheckNotPaidOrderForDeletion() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.CREATED);

        List<OrderItem> orderItems = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        Product product = new Product();
        Long productId = 1L;
        product.setId(productId);
        orderItem.setProduct(product);
        orderItem.setQuantity(2);
        orderItems.add(orderItem);

        when(orderItemService.getOrderItemsByOrder(order)).thenReturn(orderItems);

        orderJobExecutor.checkNotPaidOrderForDeletion(order);
        verify(productService, times(1)).increaseQuantityProduct(anyLong(), anyInt());
        verify(orderItemService, times(1)).deleteOrderItemsByOrder(order);
        verify(orderService, times(1)).deleteOrder(order);
    }

    @Test
    void testCheckNotPaidOrderForDeletionOrderNotCreated() {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.PAID);
        orderJobExecutor.checkNotPaidOrderForDeletion(order);
        verify(productService, times(0)).increaseQuantityProduct(anyLong(), anyInt());
        verify(orderItemService, times(0)).deleteOrderItemsByOrder(order);
        verify(orderService, times(0)).deleteOrder(order);
    }
}
