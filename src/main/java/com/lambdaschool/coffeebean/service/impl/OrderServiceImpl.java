package com.lambdaschool.coffeebean.service.impl;

import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.repository.OrderRepository;
import com.lambdaschool.coffeebean.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class OrderServiceImpl implements OrderService
{
    @Autowired
    OrderRepository orderRepos;

    @Override
    public List<Order> findAllOrders()
    {
        return orderRepos.findAll();
    }

    @Override
    public Order findOrderByOrderId(long orderId)
    {
        return orderRepos.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(orderId)));
    }

    @Override
    public List<Order> findUnshippedOrders()
    {
        return orderRepos.findUnshippedOrders();
    }

    @Override
    public List<Order> findShippedOrders()
    {
        return orderRepos.findShippedOrders();
    }

    @Transactional
    @Override
    public Order updateShippingStatus(long orderId, boolean status)
    {
        Order foundOrder = orderRepos.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(orderId)));

        foundOrder.setShippedStatus(status);
        if (status)
        {
            foundOrder.setShipDateTime(new Date());
        }
        else
        {
            foundOrder.setShipDateTime(null);
        }
        return orderRepos.save(foundOrder);
    }
}
