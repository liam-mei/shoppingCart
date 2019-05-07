package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.exceptions.BadRequestException;
import com.lambdaschool.coffeebean.exceptions.ForbiddenException;
import com.lambdaschool.coffeebean.model.Cart;
import com.lambdaschool.coffeebean.model.CartItem;
import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.service.CartService;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import com.lambdaschool.coffeebean.service.CurrentUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

@Api(value = "Some value... by DKM", description = "Cart Controller by DKM")
@RestController
@RequestMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController extends CheckIsAdmin
{
    @Autowired
    CartService cartService;

    // ==================== CART ==============================

//    @GetMapping("/test")
//    public List<Cart> testCartRepo()
//    {
//        return cartrepos.findAll();
//    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCartByUserId(@PathVariable long userId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userId || isAdmin)
        {
            Cart foundCart = cartService.getCartByCartId(userId);
            return new ResponseEntity<>(foundCart, HttpStatus.OK);
        } else
        {
//            throw new IOException("testMessage?");
//            throw new org.springframework.web.client.HttpClientErrorException(HttpStatus.FORBIDDEN, "masdfasdf");
//            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "message");
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "searched cart does not match current user's cart");
//            throw new AuthenticationException(userId, "cartId does not belong to you");
//            return doesUsernameMatch(currentUserId, userId, false);
        }
    }

    @GetMapping("/{userId}/orderbydate")
    public ResponseEntity<?> getCartByUserIdOrderByDate(@PathVariable long userId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userId || isAdmin)
        {
            List<CartItem> cart = cartService.getCartByUserIdOrderByDate(currentuser.getCartId());
            return new ResponseEntity<>(cart, HttpStatus.OK);

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "searched cart does not match current user's cart");
        }
    }


    @PostMapping("/addtocart/{cartId}/{productId}/{quantity}")
    public ResponseEntity<?> addItemToCart(@PathVariable long cartId,
                                                  @PathVariable long productId,
                                                  @PathVariable int quantity)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            HashMap<String, Object> cartDetails = cartService.addItemToCart(currentUserCartId, productId, quantity);
            return new ResponseEntity<>(cartDetails, HttpStatus.OK);
        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being added to does not match current user's cart");
        }
    }

    @PutMapping("/modifyquantityincart/{cartId}/{productId}/{quantity}")
    public ResponseEntity<?> updateQuantityInCart(@PathVariable long cartId,
                                       @PathVariable long productId,
                                       @PathVariable int quantity)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            HashMap<String, Object> cartDetails = cartService.updateQuantityInCart(currentUserCartId, productId, quantity);
            return new ResponseEntity<>(cartDetails, HttpStatus.OK);
        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being modified does not match current user's cart");
        }

    }

    @DeleteMapping("/remove/{cartId}/{productId}")
    public ResponseEntity<?> deleteOneItemFromCart(@PathVariable long cartId, @PathVariable long productId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            cartService.deleteOneItemFromCart(currentUserCartId, productId);
            return new ResponseEntity<>("You have deleted product: " + productId + " from user: " + currentuser.getCurrentUserId(), HttpStatus.OK);

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being modified does not match current user's cart");
        }

    }

    @DeleteMapping("/modifytozero/{cartId}/{productId}")
    public ResponseEntity<?> updateToZero(@PathVariable long cartId, @PathVariable long productId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            cartService.updateToZero(currentUserCartId, productId);
            return new ResponseEntity<>("There are now 0 of " + productId + " in " + currentuser.getCurrentUserId() + "'s cart.", HttpStatus.OK);

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being modified does not match current user's cart");
        }


    }

    @DeleteMapping("/deleteall/{cartId}")
    public ResponseEntity<?> deleteAllItemsFromCart(@PathVariable long cartId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            cartService.deleteAllItemsFromCart(currentUserCartId);
            return new ResponseEntity<>("You have deleted all itemsInCart in cart from user :" + currentUserId, HttpStatus.OK);

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being modified does not match current user's cart");
        }


    }

//    // ============ BUY ================

    @PostMapping("/buy")
    public ResponseEntity<?> buyItemsInCart(@RequestBody Order newOrder)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Set<CartItem> currentCartItems = cartService.getCartByCartId(currentUserId).getItemsInCart();

        if (currentCartItems.isEmpty())
            throw new BadRequestException(HttpStatus.BAD_REQUEST, "Your cart is empty");

        HashMap<String, Object> missingAddress = checkIfOrderHasBillingAndShipping(newOrder);
        if (!missingAddress.isEmpty())
            return new ResponseEntity<>(missingAddress, HttpStatus.BAD_REQUEST);

        HashMap<String, Object> productConstraintError = cartService.checkInventory(currentCartItems);

        if (productConstraintError.containsKey("error"))
            return new ResponseEntity<>(productConstraintError, HttpStatus.BAD_REQUEST);

        Order savedOrder = cartService.buyItemsInCart(newOrder, currentCartItems, currentUserId, currentuser.getCartId());

        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

}
