package com.lambdaschool.coffeebean.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;


public class CurrentUser extends User
{
    private long currentUserId;
    private String customerName, email;
    private long cartId;
    private List<? extends SimpleGrantedAuthority> authority;

    public CurrentUser(String username, String password, List<? extends SimpleGrantedAuthority> authority, String customerName, long currentUserId, String email, long cartId)
    {
        super(username, password, authority);
        this.customerName = customerName;
        this.currentUserId = currentUserId;
        this.authority = authority;
        this.email = email;
        this.cartId = cartId;
    }

    public long getCurrentUserId()
    {
        return currentUserId;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public List<? extends SimpleGrantedAuthority> getAuthorities2()
    {
        return authority;
    }

    public String getEmail()
    {
        return email;
    }

    // ========= Setters ==============

    public void setCurrentUserId(long currentUserId)
    {
        this.currentUserId = currentUserId;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setAuthority(List<? extends SimpleGrantedAuthority> authority)
    {
        this.authority = authority;
    }

    public long getCartId()
    {
        return cartId;
    }

    public void setCartId(long cartId)
    {
        this.cartId = cartId;
    }
}
