package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "addresses")
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long addressId;

    private String street;

    private String city;

    private String state;

    private String zipcode;

    private boolean display;

    private Date createdAt = new Date();

    private Date updatedAt = new Date();

    //ManyToOne to User
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({
            "reviews", "orderHistory", "cart", "currentPassword",
            "middleName", "lastName", "customerPhone", "receiveEmails",
            "addresses", "createdAt", "updatedAt"})
    private User user;

    //OneToMany to Order
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "shippingAddress")
    @JsonIgnoreProperties({
            "user", "itemsInOrder", "shippingAddress", "billingAddress",
            "paymentDetails", "shipDateTime", "shippedStatus", "orderDateTime", "createdAt"})
    private Set<Order> ordersUsingThisAsShipping;

    //OneToMany with Order
    @OneToMany(cascade = CascadeType.DETACH, mappedBy = "billingAddress")
    @JsonIgnoreProperties({
            "user", "itemsInOrder", "shippingAddress", "billingAddress",
            "paymentDetails", "shipDateTime", "shippedStatus", "orderDateTime", "createdAt"})
    private Set<Order> ordersUsingThisAsBilling;

    public Address()
    {
    }

    public Long getAddressId()
    {
        return addressId;
    }

    public void setAddressId(Long addressId)
    {
        this.addressId = addressId;
    }

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getState()
    {
        return state;
    }

    public void setState(String state)
    {
        this.state = state;
    }

    public String getZipcode()
    {
        return zipcode;
    }

    public void setZipcode(String zipcode)
    {
        this.zipcode = zipcode;
    }

    public boolean isDisplay()
    {
        return display;
    }

    public void setDisplay(boolean display)
    {
        this.display = display;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Set<Order> getOrdersUsingThisAsShipping()
    {
        return ordersUsingThisAsShipping;
    }

    public void setOrdersUsingThisAsShipping(Set<Order> ordersUsingThisAsShipping)
    {
        this.ordersUsingThisAsShipping = ordersUsingThisAsShipping;
    }

    public Set<Order> getOrdersUsingThisAsBilling()
    {
        return ordersUsingThisAsBilling;
    }

    public void setOrdersUsingThisAsBilling(Set<Order> ordersUsingThisAsBilling)
    {
        this.ordersUsingThisAsBilling = ordersUsingThisAsBilling;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }
}
