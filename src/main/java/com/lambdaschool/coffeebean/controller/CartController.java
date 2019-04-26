package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.CartItem;
import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.repository.*;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import com.lambdaschool.coffeebean.service.CurrentUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@CrossOrigin
@Api(value = "Some value... by DKM", description = "Cart Controller by DKM")
@RestController
@RequestMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController extends CheckIsAdmin
{
    @Autowired
    UserRepository userrepos;

    @Autowired
    OrderRepository orderrepos;

    @Autowired
    ProductRepository productrepos;
//
    @Autowired
    CartRepository cartrepos;

    @Autowired
    CartItemRepository cartitemrepos;

    // ==================== CART ==============================

//    @GetMapping("/test")
//    public List<Cart> testCartRepo()
//    {
//        return cartrepos.findAll();
//    }

    @GetMapping("/{userId}")
    public Object getCartItemsInCartById(@PathVariable long userid)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userid || isAdmin)
        {
            return cartrepos.getCartByUserId(userid);

        } else
        {
            return doesUsernameMatch(currentUserId, userid, false);
        }
    }


    @PostMapping("/addtocart/{userId}/{productId}/{quantity}")
    public HashMap<String, Object> postItemToCart(@PathVariable long userId,
                                                  @PathVariable long productId,
                                                  @PathVariable int quantity)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);
        HashMap<String, Object> returnObject = new HashMap<>();

        if (currentUserId == userId || isAdmin)
        {
            CartItem foundCartItem = cartitemrepos.findCartItem(currentUserCartId, productId);
            if (foundCartItem != null)
            {
                int previousQuantity = foundCartItem.getQuantity();
                int total = previousQuantity + quantity;
                cartitemrepos.modifyQuantityInCart(currentUserCartId, productId, total);

                returnObject.put("userId", currentUserId);
                returnObject.put("previouslyExisted", true);
                returnObject.put("productName", foundCartItem.getProduct().getProductName());
                returnObject.put("previousQuantity", previousQuantity);
                returnObject.put("quantityToBeAdded", quantity);
                returnObject.put("totalQuantity", total);
            } else
            {
                cartitemrepos.postCartItemToCart(new Date(), quantity, currentUserCartId, productId);
                returnObject.put("userId", userId);
                returnObject.put("prevouslyExisted", false);
                returnObject.put("quantityToBeAdded", quantity);
            }
            return returnObject;
        } else
        {
            return doesUsernameMatch(currentUserId, userId, false);
        }
    }

    @PutMapping("/modifyquantityincart/{userId}/{productId}/{quantity}")
    public Object modifyQuantityInCart(@PathVariable long userId,
                                       @PathVariable long productId,
                                       @PathVariable int quantity)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userId || isAdmin)
        {
            CartItem foundCartItem = cartitemrepos.findCartItem(currentuser.getCartId(), productId);
            if (foundCartItem != null)
            {
                int previousQuantity = foundCartItem.getQuantity();
                cartitemrepos.modifyQuantityInCart(userId, productId, quantity);
                return "There were " + previousQuantity + ", but now there are " + quantity + " of " +
                        foundCartItem.getProduct().getProductName() + " in " + currentuser.getUsername() + "'s cart.";
            } else
            {
                return userId + " does not have productId: " + productId + "in their cart.";
            }

        } else
        {
            return doesUsernameMatch(currentUserId, userId, false);
        }

    }

    @DeleteMapping("/remove/{userId}/{productId}")
    public Object deleteOneItemFromCart(@PathVariable long userId, @PathVariable long productId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userId || isAdmin)
        {
            cartitemrepos.deleteOneItemFromCart(currentuser.getCartId(), productId);
            return "You have deleted product: " + productId + " from user: " + userId;

        } else
        {
            return doesUsernameMatch(currentUserId, userId, false);
        }

    }

    @DeleteMapping("/modifytozero/{userId}/{productId}")
    public Object modifyToZero(@PathVariable long userId, @PathVariable long productId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userId || isAdmin)
        {
            cartitemrepos.modifyQuantityInCart(currentuser.getCartId(), productId, 0);
            return "There are now 0 of " + productId + " in " + userId + "'s cart.";

        } else
        {
            return doesUsernameMatch(currentUserId, userId, false);
        }


    }

    @DeleteMapping("/deleteall/{userId}")
    public Object deleteAllItemsFromCart(@PathVariable long userId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userId || isAdmin)
        {
            cartitemrepos.deleteAllItemsFromCart(currentuser.getCartId());
            return "You have deleted all itemsInCart in cart from user :" + userId;

        } else
        {
            return doesUsernameMatch(currentUserId, userId, false);
        }


    }

//    // ============ BUY ================
//
//    // 1. get CurrentUserId from getPrincipal()
//    // 2. delete itemsInCart from cart'
//    // 3. save order with the limited data
//    // 4. return actual order from database with correct information
    @PostMapping("/buy")
    public Object buyItemsInCart(@RequestBody Order newOrder)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Set<CartItem> currentCartItems = cartrepos.getCartByUserId(currentUserId).getItemsInCart();

        ArrayList<Product> productsWithConstraint = new ArrayList<>();

        currentCartItems.forEach(cartItem ->
        {
            if ( cartItem.getProduct().getInventory() < cartItem.getQuantity() )
            {
                productsWithConstraint.add(cartItem.getProduct());
            }
        });

        if (productsWithConstraint.size() > 0)
        {
            HashMap<Object, Object> returnObject = new HashMap<>();
            returnObject.put("inventoryConstraint", true);
            returnObject.put("productsWithConstraint", productsWithConstraint);
            return returnObject;
        }

        newOrder.setUser(userrepos.findById(currentUserId).get());
        Order currentOrder = orderrepos.save(newOrder);
        long currentOrderId = currentOrder.getOrderId();

        currentCartItems.forEach(item ->
        {
            orderrepos.addToOrderItem(currentOrderId, item.getProduct().getProductId(), item.getQuantity());
            productrepos.removeOrderedQtyFromInventory(item.getProduct().getProductId(), item.getQuantity());
        });

        cartitemrepos.deleteAllItemsFromCart(currentUserId);

        return orderrepos.findById(currentOrderId).get();
    }

}
