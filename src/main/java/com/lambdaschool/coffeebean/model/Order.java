package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "orders")
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    private String paymentDetails;

    //yyyy-mm-dd HH:MM:SS
    private Date shipDateTime;

    private boolean shippedStatus = false;

    //yyyy-mm-dd HH:MM:SS
    private Date orderDateTime = new Date();

    //*** ManyToOne with user ***
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({
            "reviews", "orderHistory", "cart", "currentPassword",
            "middleName", "lastName", "customerPhone", "receiveEmails"})
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnoreProperties({"order"})
    private Set<OrderItem> itemsInOrder;


    public Order()
    {
    }


    public long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(long orderId)
    {
        this.orderId = orderId;
    }

    public String getPaymentDetails()
    {
        return paymentDetails;
    }

    public void setPaymentDetails(String paymentDetails)
    {
        this.paymentDetails = paymentDetails;
    }

    public Date getShipDateTime()
    {
        return shipDateTime;
    }

    public void setShipDateTime(Date shipDateTime)
    {
        this.shipDateTime = shipDateTime;
    }

    public boolean isShippedStatus()
    {
        return shippedStatus;
    }

    public void setShippedStatus(boolean shippedStatus)
    {
        this.shippedStatus = shippedStatus;
    }

    public Date getOrderDateTime()
    {
        return orderDateTime;
    }

    public void setOrderDateTime(Date orderDateTime)
    {
        this.orderDateTime = orderDateTime;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Set<OrderItem> getItemsInOrder()
    {
        return itemsInOrder;
    }

    public void setItemsInOrder(Set<OrderItem> itemsInOrder)
    {
        this.itemsInOrder = itemsInOrder;
    }
}
