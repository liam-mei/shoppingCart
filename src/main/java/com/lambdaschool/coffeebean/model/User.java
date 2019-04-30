package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.lambdaschool.coffeebean.controller.View;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
public class User
{
    @JsonView({View.Essential.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @JsonView({View.Essential.class})
    @Column(length = 250, unique = true)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    // set default privilege to be user
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String role = "user";

    private String currentPassword;

    // ==================================================================

    @JsonView({View.Essential.class})
    private String firstName;

    private String middleName;

    private String lastName;

    @Column(length = 250, unique = true)
    private String customerPhone;

    @JsonView({View.Essential.class})
    @Column(length = 250, unique = true)
    private String email;

    // Used for SendGrid Promo Emails
    private boolean receiveEmails = false;

    // ================ Relational Tables ================

    // *** OneToMany with Order ***
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnoreProperties({"user", "itemsInOrder", "shippingAddress", "billingAddress",})
    private Set<Order> orderHistory;

//    // OneToOne with Cart
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cartId")
    @JsonIgnoreProperties({"user"})
    private Cart cart;

    // OneToMany with Reviews
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "reviewer")
    @JsonIgnoreProperties({"reviewer", "reviewedProduct"})
    private Set<Review> reviews;

    // OneToMany with Address
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonIgnoreProperties({
            "reviews", "orderHistory", "cart", "currentPassword",
            "middleName", "lastName", "customerPhone", "receiveEmails",
            "user", "ordersUsingThisAsShipping", "ordersUsingThisAsBilling"})
    private Set<Address> addresses;

    // ========================================================


    public User()
    {
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public List<SimpleGrantedAuthority> getAuthority()
    {
        String myRole = "ROLE_" + this.role.toUpperCase();
        return Collections.singletonList(new SimpleGrantedAuthority(myRole));
    }

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getRole()
    {
        return role;
    }

    public void setRole(String role)
    {
        this.role = role;
    }

    public String getCurrentPassword()
    {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword)
    {
        this.currentPassword = currentPassword;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getMiddleName()
    {
        return middleName;
    }

    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getCustomerPhone()
    {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone)
    {
        this.customerPhone = customerPhone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public boolean isReceiveEmails()
    {
        return receiveEmails;
    }

    public void setReceiveEmails(boolean receiveEmails)
    {
        this.receiveEmails = receiveEmails;
    }

    public Set<Order> getOrderHistory()
    {
        return orderHistory;
    }

    public void setOrderHistory(Set<Order> orderHistory)
    {
        this.orderHistory = orderHistory;
    }

    public Cart getCart()
    {
        return cart;
    }

    public void setCart(Cart cart)
    {
        this.cart = cart;
    }

    public Set<Review> getReviews()
    {
        return reviews;
    }

    public void setReviews(Set<Review> reviews)
    {
        this.reviews = reviews;
    }

    public Set<Address> getAddresses()
    {
        return addresses;
    }

    public void setAddresses(Set<Address> addresses)
    {
        this.addresses = addresses;
    }
}