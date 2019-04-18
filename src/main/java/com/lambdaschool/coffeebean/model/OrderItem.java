package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Table(name = "orderItems")
@Entity
public class OrderItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderItemId;

    int quantity;

    // ManyToOne with Product - owner
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties({"cartItems", "productReviews", "suppliers"})
    private Product product;

    // ManyToOne with Order
    @ManyToOne
    @JoinColumn(name = "orderId")
    @JsonIgnoreProperties({"user", "itemsInOrder"})
    private Order order;

    public OrderItem()
    {
    }

    public long getOrderItemId()
    {
        return orderItemId;
    }

    public void setOrderItemId(long orderItemId)
    {
        this.orderItemId = orderItemId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Order getOrder()
    {
        return order;
    }

    public void setOrder(Order order)
    {
        this.order = order;
    }
}
