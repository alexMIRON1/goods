package com.test.product.service;

import com.test.product.model.entity.Order;
import com.test.product.model.entity.OrderItem;
import com.test.product.model.entity.OrderStatus;
import com.test.product.model.entity.Product;
import com.test.product.model.repository.OrderRepository;
import com.test.product.service.job.OrderJobScheduler;
import com.test.product.web.dto.ClientWantedProduct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * tests for {@link OrderTransactionService} class
 *
 * @author Oleksandr Myronenko
 */
@SpringBootTest
class OrderTransactionServiceTest {
    @Autowired
    private OrderTransactionService orderTransactionService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private ProductService productService;

    @MockBean
    private OrderItemService orderItemService;

    @MockBean
    private CustomerService customerService;

    @MockBean
    private OrderJobScheduler orderJobScheduler;

    @Test
    void testCreateOrder() {
        List<ClientWantedProduct> clientWantedProducts = new ArrayList<>();
        ClientWantedProduct clientWantedProduct = new ClientWantedProduct();
        clientWantedProduct.setProductId(1L);
        clientWantedProduct.setWantedProductQuantity(2);
        clientWantedProduct.setClientId(3L);
        clientWantedProducts.add(clientWantedProduct);
        Product product = new Product();
        product.setId(1L);
        product.setProductPrice(10.0);

        when(orderItemService.createOrderItem(any(Order.class), any(Product.class), any(Integer.class))).thenReturn(1L);
        when(orderItemService.getOrderItemById(1L)).thenReturn(new OrderItem());
        when(productService.getProductById(1L)).thenReturn(product);
        orderTransactionService.createOrder(clientWantedProducts);
        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderJobScheduler, times(1)).scheduleCheckNotPaidOrder(any(Order.class));
    }

    @Test
    void testPayOrderById() {
        Long orderId = 1L;
        Order order = new Order();
        order.setId(orderId);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderTransactionService.payOrderById(orderId);

        assertEquals(OrderStatus.PAID, order.getOrderStatus());
        assertNotNull(order.getOrderPurchasedTime());
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void testPayOrderByIdNotFound() {
        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> orderTransactionService.payOrderById(orderId));
        verify(orderRepository, times(0)).save(any(Order.class));
    }
}
