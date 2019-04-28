package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CartRepository extends JpaRepository<Cart, Long>
{
    @Query(value = "SELECT * FROM carts WHERE cart_id = :cartId", nativeQuery = true)
    Cart getCartByCartId(Long cartId);
}
