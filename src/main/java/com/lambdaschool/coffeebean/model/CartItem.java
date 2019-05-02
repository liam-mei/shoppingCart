package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Table(name = "cartItems")
@Entity
public class CartItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartItemId;

    private int quantity;

    private Date createdAt = new Date();

    // ManyToOne with Product - owner
//    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties({
            "cartItems", "productReviews", "suppliers",
            "createdAt", "updatedAt"})
    private Product product;

    // ManyToOne with Cart
    @ManyToOne
    @JoinColumn(name = "cartId")
    @JsonIgnoreProperties({"user", "itemsInCart"})
    private Cart cart;

    public CartItem()
    {
    }

    public long getCartItemId()
    {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId)
    {
        this.cartItemId = cartItemId;
    }

    public int getQuantity()
    {
        return quantity;
    }

    public void setQuantity(int quantity)
    {
        this.quantity = quantity;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Product getProduct()
    {
        return product;
    }

    public void setProduct(Product product)
    {
        this.product = product;
    }

    public Cart getCart()
    {
        return cart;
    }

    public void setCart(Cart cart)
    {
        this.cart = cart;
    }
}
