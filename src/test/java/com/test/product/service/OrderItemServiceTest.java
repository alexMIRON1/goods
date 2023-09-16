package com.test.product.service;

import com.test.product.model.entity.Order;
import com.test.product.model.entity.OrderItem;
import com.test.product.model.entity.Product;
import com.test.product.model.repository.OrderItemRepository;
import com.test.product.service.exception.WrongInputException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * tests for {@link OrderItemService} class
 *
 * @author Oleksandr Myronenko
 */
@SpringBootTest
class OrderItemServiceTest {

    @Autowired
    private OrderItemService orderItemService;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @Test
    void testGetOrderItemById() {
        Long orderItemId = 1L;
        OrderItem expectedOrderItem = new OrderItem();
        expectedOrderItem.setId(orderItemId);

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.of(expectedOrderItem));
        OrderItem result = orderItemService.getOrderItemById(orderItemId);
        assertEquals(orderItemId, result.getId());
        verify(orderItemRepository, times(1)).findById(orderItemId);
    }

    @Test
    void testGetOrderItemByIdNotFound() {
        Long orderItemId = 1L;

        when(orderItemRepository.findById(orderItemId)).thenReturn(Optional.empty());

        assertThrows(WrongInputException.class, () -> orderItemService.getOrderItemById(orderItemId));
        verify(orderItemRepository, times(1)).findById(orderItemId);
    }

    @Test
    void testCreateOrderItem() {
        // Arrange
        Order order = new Order();
        Product product = new Product();
        Integer quantity = 3;

        OrderItem expectedOrderItem = new OrderItem();
        expectedOrderItem.setOrder(order);
        expectedOrderItem.setProduct(product);
        expectedOrderItem.setQuantity(quantity);

        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(expectedOrderItem);

        Long result = orderItemService.createOrderItem(order, product, quantity);
        assertEquals(expectedOrderItem.getId(), result);
        verify(orderItemRepository, times(1)).save(any(OrderItem.class));
    }

    @Test
    void testGetOrderItemsByOrder() {
        Order order = new Order();
        List<OrderItem> expectedOrderItems = new ArrayList<>();

        when(orderItemRepository.findByOrder(order)).thenReturn(expectedOrderItems);

        List<OrderItem> result = orderItemService.getOrderItemsByOrder(order);

        assertEquals(expectedOrderItems, result);
        verify(orderItemRepository, times(1)).findByOrder(order);
    }

    @Test
    void testDeleteOrderItemsByOrder() {
        Order order = new Order();
        orderItemService.deleteOrderItemsByOrder(order);
        verify(orderItemRepository, times(1)).deleteByOrder(order);
    }
}