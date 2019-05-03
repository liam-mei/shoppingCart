package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.exceptions.BadRequestException;
import com.lambdaschool.coffeebean.exceptions.ForbiddenException;
import com.lambdaschool.coffeebean.model.Cart;
import com.lambdaschool.coffeebean.model.CartItem;
import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.repository.*;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import com.lambdaschool.coffeebean.service.CurrentUser;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Api(value = "Some value... by DKM", description = "Cart Controller by DKM")
@RestController
@RequestMapping(path = "/cart", produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController extends CheckIsAdmin
{
    UserRepository userrepos;
    OrderRepository orderrepos;
    ProductRepository productrepos;
    CartRepository cartrepos;
    CartItemRepository cartitemrepos;

    public CartController(UserRepository userrepos, OrderRepository orderrepos, ProductRepository productrepos,
                          CartRepository cartrepos, CartItemRepository cartitemrepos)
    {
        this.userrepos = userrepos;
        this.orderrepos = orderrepos;
        this.productrepos = productrepos;
        this.cartrepos = cartrepos;
        this.cartitemrepos = cartitemrepos;
    }

    // ==================== CART ==============================

//    @GetMapping("/test")
//    public List<Cart> testCartRepo()
//    {
//        return cartrepos.findAll();
//    }

    @GetMapping("/{userId}")
    public Cart getCartByUserId(@PathVariable long userId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userId || isAdmin)
        {
            return cartrepos.findById(currentuser.getCartId()).get();

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
    public List<CartItem> getCartByUserIdOrderByDate(@PathVariable long userId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userId || isAdmin)
        {
            return cartitemrepos.findCartItemsByDate(currentuser.getCartId());

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "searched cart does not match current user's cart");
        }
    }


    @PostMapping("/addtocart/{cartId}/{productId}/{quantity}")
    public HashMap<String, Object> postItemToCart(@PathVariable long cartId,
                                                  @PathVariable long productId,
                                                  @PathVariable int quantity)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);
        HashMap<String, Object> returnObject = new HashMap<>();

        if (currentUserCartId == cartId || isAdmin)
        {
            CartItem foundCartItem = cartitemrepos.findCartItem(cartId, productId);
            if (foundCartItem != null)
            {
                int previousQuantity = foundCartItem.getQuantity();
                int total = previousQuantity + quantity;
                cartitemrepos.modifyQuantityInCart(currentUserCartId, productId, total);

                returnObject.put("cartId", cartId);
                returnObject.put("previouslyExisted", true);
                returnObject.put("productName", foundCartItem.getProduct().getProductName());
                returnObject.put("previousQuantity", previousQuantity);
                returnObject.put("quantityToBeAdded", quantity);
                returnObject.put("totalQuantity", total);
            } else
            {
                cartitemrepos.postCartItemToCart(new Date(), quantity, currentUserCartId, productId);
                returnObject.put("cartId", cartId);
                returnObject.put("prevouslyExisted", false);
                returnObject.put("quantityToBeAdded", quantity);
            }
            return returnObject;
        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being added to does not match current user's cart");
        }
    }

    @PutMapping("/modifyquantityincart/{cartId}/{productId}/{quantity}")
    public Object modifyQuantityInCart(@PathVariable long cartId,
                                       @PathVariable long productId,
                                       @PathVariable int quantity)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            CartItem foundCartItem = cartitemrepos.findCartItem(currentUserCartId, productId);
            if (foundCartItem != null)
            {
                int previousQuantity = foundCartItem.getQuantity();
                cartitemrepos.modifyQuantityInCart(cartId, productId, quantity);
                return "There were " + previousQuantity + ", but now there are " + quantity + " of " +
                        foundCartItem.getProduct().getProductName() + " in " + currentuser.getUsername() + "'s cart.";
            } else
            {
                return cartId + " does not have productId: " + productId + "in their cart.";
            }

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being modified does not match current user's cart");
        }

    }

    @DeleteMapping("/remove/{cartId}/{productId}")
    public Object deleteOneItemFromCart(@PathVariable long cartId, @PathVariable long productId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            cartitemrepos.deleteOneItemFromCart(currentUserCartId, productId);
            return "You have deleted product: " + productId + " from user: " + currentuser.getCurrentUserId();

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being modified does not match current user's cart");
        }

    }

    @DeleteMapping("/modifytozero/{cartId}/{productId}")
    public Object modifyToZero(@PathVariable long cartId, @PathVariable long productId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            cartitemrepos.modifyQuantityInCart(currentUserCartId, productId, 0);
            return "There are now 0 of " + productId + " in " + currentuser.getCurrentUserId() + "'s cart.";

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "cart being modified does not match current user's cart");
        }


    }

    @DeleteMapping("/deleteall/{cartId}")
    public Object deleteAllItemsFromCart(@PathVariable long cartId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();
        long currentUserCartId = currentuser.getCartId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserCartId == cartId || isAdmin)
        {
            cartitemrepos.deleteAllItemsFromCart(currentUserCartId);
            return "You have deleted all itemsInCart in cart from user :" + currentUserId;

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

        Set<CartItem> currentCartItems = cartrepos.getCartByCartId(currentUserId).getItemsInCart();

        if (currentCartItems.isEmpty()) throw new BadRequestException(HttpStatus.BAD_REQUEST, "Your cart is empty");

        HashMap<String, Object> missingAddress = checkIfOrderHasBillingAndShipping(newOrder);
        if (!missingAddress.isEmpty()) return new ResponseEntity<>(missingAddress, HttpStatus.BAD_REQUEST);

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

        if (productsWithConstraint.size() > 0)
        {
            HashMap<Object, Object> productConstraintError = new HashMap<>();
            productConstraintError.put("error", "inventory constraint - one or more items do not have sufficient inventory to support ordered quantity");
            productConstraintError.put("productsWithConstraint", productsWithConstraint);
            return new ResponseEntity<>(productConstraintError, HttpStatus.BAD_REQUEST);
        }

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

        cartitemrepos.deleteAllItemsFromCart(currentuser.getCartId());

        return new ResponseEntity<>(savedOrder, HttpStatus.OK);
    }

}
