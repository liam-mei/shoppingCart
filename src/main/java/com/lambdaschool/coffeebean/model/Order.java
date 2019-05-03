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
    private Long orderId;

    private String paymentDetails;

    //yyyy-mm-dd HH:MM:SS
    private Date shipDateTime;

    private boolean shippedStatus = false;

    //yyyy-mm-dd HH:MM:SS
    private Date createdAt = new Date();

    //*** ManyToOne with user ***
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({
            "reviews", "orderHistory", "cart", "currentPassword",
            "middleName", "lastName", "customerPhone", "receiveEmails",
            "addresses", "createdAt", "updatedAt"})
    private User user;

    // One To Many with OrderItem - don't ignore product - I want to see that
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonIgnoreProperties({"order", "createdAt", "updatedAt"})
    private Set<OrderItem> itemsInOrder;

    @ManyToOne
    @JoinColumn(name = "shippingAddressId")
    @JsonIgnoreProperties({
            "user", "ordersUsingThisAsShipping", "ordersUsingThisAsBilling",
            "createdAt", "updatedAt"})
    private Address shippingAddress;

    @ManyToOne
    @JoinColumn(name = "billingAddressId")
    @JsonIgnoreProperties({
            "user", "ordersUsingThisAsShipping", "ordersUsingThisAsBilling",
            "createdAt", "updatedAt"})
    private Address billingAddress;

    public Order()
    {
    }


    public Long getOrderId()
    {
        return orderId;
    }

    public void setOrderId(Long orderId)
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

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
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

    public Address getShippingAddress()
    {
        return shippingAddress;
    }

    public void setShippingAddress(Address shippingAddress)
    {
        this.shippingAddress = shippingAddress;
    }

    public Address getBillingAddress()
    {
        return billingAddress;
    }

    public void setBillingAddress(Address billingAddress)
    {
        this.billingAddress = billingAddress;
    }
}
