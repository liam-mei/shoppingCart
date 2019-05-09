package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(value = "Some value... by DKM", description = "Order Controller by DKM")
@RestController
@RequestMapping(path = "/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController
{
    @Autowired
    OrderService orderService;

    @ApiOperation(value = "find all orders - DKM", response = Order.class)
    @GetMapping
    public ResponseEntity<?> findAllOrders()
    {
        List<Order> allOrders = orderService.findAllOrders();
        return new ResponseEntity<>(allOrders, HttpStatus.OK);
    }

    @GetMapping("/{orderid}")
    public ResponseEntity<?> findOrderByOrderId(@PathVariable long orderid)
    {
        Order foundOrder = orderService.findOrderByOrderId(orderid);
        return new ResponseEntity<>(foundOrder, HttpStatus.OK);
    }

    @GetMapping("/unshipped")
    public ResponseEntity<?> findUnshippedOrders()
    {
        List<Order> unshippedOrders = orderService.findUnshippedOrders();
        return new ResponseEntity<>(unshippedOrders, HttpStatus.OK);
    }

    @GetMapping("/shipped")
    public ResponseEntity<?> findShippedOrders()
    {
        List<Order> shippedOrders = orderService.findShippedOrders();
        return new ResponseEntity<>(shippedOrders, HttpStatus.OK);
    }

    @PutMapping("/updateshippingstatus/{orderid}/{status}")
    public ResponseEntity<?> updateShippingStatus(@PathVariable long orderid, @PathVariable boolean status)
    {
        Order updatedOrder = orderService.updateShippingStatus(orderid, status);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }
}