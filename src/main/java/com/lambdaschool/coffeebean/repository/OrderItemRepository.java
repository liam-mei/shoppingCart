package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>
{
}
