package com.lambdaschool.coffeebean.service;

import com.lambdaschool.coffeebean.model.Order;

import java.util.List;

public interface OrderService
{
    List<Order> findAllOrders();

    Order findOrderByOrderId(long orderId);

    List<Order> findUnshippedOrders();

    List<Order> findShippedOrders();

    Order updateShippingStatus(long orderId, boolean status);
}
