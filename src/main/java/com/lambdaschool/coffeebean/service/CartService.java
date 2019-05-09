package com.lambdaschool.coffeebean.service;

import com.lambdaschool.coffeebean.model.Cart;
import com.lambdaschool.coffeebean.model.CartItem;
import com.lambdaschool.coffeebean.model.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public interface CartService
{
    Cart getCartByCartId(long cartId);

    List<CartItem> getCartByUserIdOrderByDate(long cartId);

    HashMap<String, Object> addItemToCart(long cartId,
                                          long productId,
                                          int quantity);

    HashMap<String, Object> updateQuantityInCart(long cartId,
                                long productId,
                                int quantity);

    void deleteOneItemFromCart(long cartId, long productId);

    void updateToZero(long cartId, long productId);

    void deleteAllItemsFromCart(long cartId);

    HashMap<String, Object> checkInventory(Set<CartItem> currentCartItems);

    Order buyItemsInCart(Order newOrder, Set<CartItem> currentCartItems, long currentUserId, long cartId);
}
