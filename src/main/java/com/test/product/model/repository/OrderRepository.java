package com.test.product.model.repository;

import com.test.product.model.entity.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * this interface describes working with database and executing simple queries for entity {@link  Order}
 *
 * @author Oleksandr Myronenko
 */
@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
