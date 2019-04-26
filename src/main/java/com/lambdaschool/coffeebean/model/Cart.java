package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Set;

@Table(name = "carts")
@Entity
public class Cart
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;

//    // OneToOne with User - subowner
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({
            "reviews", "orderHistory", "cart", "currentPassword",
            "middleName", "lastName", "customerPhone", "receiveEmails",
            "addresses"})
    private User user;

    // OneToMany with CartItem - owner
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cart")
    // Don't ignore product as we need to see that
    @JsonIgnoreProperties({"cart"})
    private Set<CartItem> itemsInCart;

    public Cart()
    {
    }

    public long getCartId()
    {
        return cartId;
    }

    public void setCartId(long cartId)
    {
        this.cartId = cartId;
    }

    public User getUser()
    {
        return user;
    }

    public void setUser(User user)
    {
        this.user = user;
    }

    public Set<CartItem> getItemsInCart()
    {
        return itemsInCart;
    }

    public void setItemsInCart(Set<CartItem> itemsInCart)
    {
        this.itemsInCart = itemsInCart;
    }
}
