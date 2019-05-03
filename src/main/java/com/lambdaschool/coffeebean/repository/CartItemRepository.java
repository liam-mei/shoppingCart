package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long>
{
    @Query(value = "SELECT * FROM cart_items WHERE (cart_id = :cartId AND product_id = :productId)", nativeQuery = true)
    CartItem findCartItem(long cartId, long productId);


    @Query(value = "SELECT * FROM cart_items WHERE cart_id = :cartId ORDER BY created_at DESC", nativeQuery = true)
    List<CartItem> findCartItemsByDate(long cartId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO cart_items (created_at, quantity, cart_id, product_id) VALUES (:createdAt, :quantity, :cartId, :productId)", nativeQuery = true)
    void postCartItemToCart(Date createdAt, int quantity, long cartId, long productId);

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
