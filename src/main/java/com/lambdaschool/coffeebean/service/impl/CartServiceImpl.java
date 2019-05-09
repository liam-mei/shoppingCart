package com.lambdaschool.coffeebean.service.impl;

import com.lambdaschool.coffeebean.model.Cart;
import com.lambdaschool.coffeebean.model.CartItem;
import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.repository.*;
import com.lambdaschool.coffeebean.service.CartService;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.*;

@Transactional
@Service
public class CartServiceImpl implements CartService
{
    UserRepository userrepos;
    OrderRepository orderrepos;
    ProductRepository productrepos;
    CartRepository cartrepos;
    CartItemRepository cartitemrepos;

    public CartServiceImpl(UserRepository userrepos, OrderRepository orderrepos, ProductRepository productrepos,
                          CartRepository cartrepos, CartItemRepository cartitemrepos)
    {
        this.userrepos = userrepos;
        this.orderrepos = orderrepos;
        this.productrepos = productrepos;
        this.cartrepos = cartrepos;
        this.cartitemrepos = cartitemrepos;
    }

    @Override
    public Cart getCartByCartId(long cartId)
    {
        return cartrepos.findById(cartId)
                .orElseThrow(()->new EntityNotFoundException(Long.toString(cartId)));
    }

    @Override
    public List<CartItem> getCartByUserIdOrderByDate(long cartId)
    {
        return cartitemrepos.findCartItemsByDate(cartId);
    }

    @Override
    public HashMap<String, Object> addItemToCart(long cartId, long productId, int quantity)
    {
        HashMap<String, Object> returnObject = new HashMap<>();
        CartItem foundCartItem = cartitemrepos.findCartItem(cartId, productId);
        if (foundCartItem != null)
        {
            int previousQuantity = foundCartItem.getQuantity();
            int total = previousQuantity + quantity;
            cartitemrepos.modifyQuantityInCart(cartId, productId, total);

            returnObject.put("cartId", cartId);
            returnObject.put("previouslyExisted", true);
            returnObject.put("productName", foundCartItem.getProduct().getProductName());
            returnObject.put("previousQuantity", previousQuantity);
            returnObject.put("quantityToBeAdded", quantity);
            returnObject.put("totalQuantity", total);
        } else
        {
            cartitemrepos.postCartItemToCart(new Date(), quantity, cartId, productId);
            returnObject.put("cartId", cartId);
            returnObject.put("previouslyExisted", false);
            returnObject.put("quantityToBeAdded", quantity);
        }
        return returnObject;
    }

    @Override
    public HashMap<String, Object> updateQuantityInCart(long cartId, long productId, int quantity)
    {
        HashMap<String, Object> returnObject = new HashMap<>();
        CartItem foundCartItem = cartitemrepos.findCartItem(cartId, productId);
        if (foundCartItem != null)
        {
            int previousQuantity = foundCartItem.getQuantity();
            cartitemrepos.modifyQuantityInCart(cartId, productId, quantity);

            returnObject.put("cartId", cartId);
            returnObject.put("productName", foundCartItem.getProduct().getProductName());
            returnObject.put("previousQuantity", previousQuantity);
            returnObject.put("currentQuantity", quantity);
            return returnObject;
        } else
        {
            throw new EntityNotFoundException(cartId + " does not have productId: " + productId + "in their cart.");
        }
    }

    @Override
    public void deleteOneItemFromCart(long cartId, long productId)
    {
        cartitemrepos.deleteOneItemFromCart(cartId, productId);
    }

    @Override
    public void updateToZero(long cartId, long productId)
    {
        cartitemrepos.modifyQuantityInCart(cartId, productId, 0);
    }

    @Override
    public void deleteAllItemsFromCart(long cartId)
    {
        cartitemrepos.deleteAllItemsFromCart(cartId);
    }

    @Override
    public HashMap<String, Object> checkInventory(Set<CartItem> currentCartItems)
    {
        ArrayList<HashMap<String, Object>> productsWithConstraint = new ArrayList<>();

        currentCartItems.forEach(cartItem ->
        {
            if ( cartItem.getProduct().getInventory() < cartItem.getQuantity() )
            {
                Product cartProduct = cartItem.getProduct();
                HashMap<String, Object> constraintObj = new HashMap<>();
                constraintObj.put("productId", cartProduct.getProductId());
                constraintObj.put("productName", cartProduct.getProductName());
                constraintObj.put("inventory", cartProduct.getInventory());
                constraintObj.put("cartQuantity", cartItem.getQuantity());
                productsWithConstraint.add(constraintObj);
            }
        });

        HashMap<String, Object> productConstraintError = new HashMap<>();
        if (productsWithConstraint.size() > 0) productConstraintError.put("error", "One or more products do not have sufficient inventory");
        productConstraintError.put("productsWithConstraint", productsWithConstraint);
        return productConstraintError;
    }

    @Override
    public Order buyItemsInCart(Order newOrder, Set<CartItem> currentCartItems, long currentUserId, long cartId)
    {
        newOrder.setUser(userrepos.findById(currentUserId).get());
        newOrder.setShippedStatus(false);
        newOrder.setOrderId(null);
        Order savedOrder = orderrepos.save(newOrder);
        long savedOrderId = savedOrder.getOrderId();

        currentCartItems.forEach(item ->
        {
            orderrepos.addToOrderItem(savedOrderId, item.getProduct().getProductId(), item.getQuantity());
            productrepos.removeOrderedQtyFromInventory(item.getProduct().getProductId(), item.getQuantity());
        });

        cartitemrepos.deleteAllItemsFromCart(cartId);

        return savedOrder;
    }
}
