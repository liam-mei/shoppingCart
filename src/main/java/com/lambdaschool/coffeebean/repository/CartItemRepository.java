package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;

public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    @Query(value = "SELECT * FROM cart_items WHERE (cart_id = :cartId AND product_id = :productId)", nativeQuery = true)
    CartItem findCartItem(long cartId, long productId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cart_items (date_added, quantity, cart_id, product_id) VALUES (:dateAdded, :quantity, :cartId, :productId)", nativeQuery = true)
    void postCartItemToCart(Date dateAdded, int quantity, long cartId, long productId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE cart_items SET quantity = :quantity WHERE (cart_id = :cartId AND product_id = :productId);", nativeQuery = true)
    void modifyQuantityInCart(long cartId, long productId, int quantity);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_items WHERE cart_id=:cartId AND product_id=:productId", nativeQuery = true)
    void deleteOneItemFromCart(long cartId, long productId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM cart_items WHERE cart_id=:cartId", nativeQuery = true)
    void deleteAllItemsFromCart(long cartId);
}
