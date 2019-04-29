package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.exceptions.ForbiddenException;
import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.model.User;
import com.lambdaschool.coffeebean.repository.CartRepository;
import com.lambdaschool.coffeebean.repository.OrderRepository;
import com.lambdaschool.coffeebean.repository.UserRepository;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import com.lambdaschool.coffeebean.service.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController extends CheckIsAdmin
{
    @Autowired
    UserRepository userrepos;

    @Autowired
    OrderRepository orderrepos;

    @Autowired
    CartRepository cartrepos;

    @GetMapping("/userid/{userid}")
    public Object findUserByUserid(@PathVariable long userid)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userid || isAdmin)
        {
            return userrepos.findById(userid).get();

        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "user being searched does not match current user");
        }
    }

    @GetMapping("/username/{username}")
    public Object findUserByUsername(@PathVariable String username)
    {
        {
            CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String currentUsername = currentuser.getUsername();

            boolean isAdmin = testIsAdmin(currentuser);

            if (currentUsername.equalsIgnoreCase(username) || isAdmin)
            {
                return userrepos.findByUsername(username);

            } else
            {
                throw new ForbiddenException(HttpStatus.FORBIDDEN, "user being searched does not match current user");
            }
        }
    }

    @PutMapping("/update")
    public Object updateUser(@RequestBody User updatedUser)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        User foundUser = userrepos.findById(currentUserId).get();

        String currentEncryptedPassword = foundUser.getPassword();
        String unencryptedCurrentPassword = updatedUser.getCurrentPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        HashMap<String, Object> returnObject = new HashMap();

        if (passwordEncoder.matches(unencryptedCurrentPassword, currentEncryptedPassword))
        {
            String updatedEmail = updatedUser.getEmail();
            String updatedUsername = updatedUser.getUsername();
            String updatedPhone = updatedUser.getCustomerPhone();
            if (updatedUsername != null &&
                    userrepos.findByUsername(updatedUsername) != null &&
                    !updatedUsername.equalsIgnoreCase(foundUser.getUsername()))
                returnObject.put("usernameExists", true);
            if (updatedEmail != null &&
                    userrepos.findByEmail(updatedEmail) != null &&
                    !updatedEmail.equalsIgnoreCase(foundUser.getEmail()))
                returnObject.put("emailExists", true);
            if (updatedPhone != null &&
                    userrepos.findByCustomerPhone(updatedPhone) != null &&
                    !updatedPhone.equalsIgnoreCase(foundUser.getCustomerPhone()))
                returnObject.put("phoneExists", true);
            if (!returnObject.isEmpty()) return returnObject;

            // User - personal details field - can be user changed
            if (updatedUser.getCustomerPhone() == null) updatedUser.setCustomerPhone(foundUser.getCustomerPhone());
            if (updatedUser.getMiddleName() == null) updatedUser.setMiddleName(foundUser.getMiddleName());
            if (updatedUser.getFirstName() == null) updatedUser.setFirstName(foundUser.getFirstName());
            if (updatedUser.getLastName() == null) updatedUser.setLastName(foundUser.getLastName());
            if (updatedUser.getUsername() == null) updatedUser.setUsername(foundUser.getUsername());
            if (updatedUser.getPassword() == null) updatedUser.setPassword(updatedUser.getCurrentPassword());
            if (updatedUser.getEmail() == null) updatedUser.setEmail(foundUser.getEmail());
            if (updatedUser.isReceiveEmails() == true)
            {
                updatedUser.setReceiveEmails(true);
            }
            else
            {
                updatedUser.setReceiveEmails(false);
            }

            // fields user can't change or can't change at this url
            updatedUser.setCart(cartrepos.findById(currentuser.getCartId()).get());
            updatedUser.setOrderHistory(foundUser.getOrderHistory());
            updatedUser.setReviews(foundUser.getReviews());
            updatedUser.setAddresses(foundUser.getAddresses());
            updatedUser.setUserId(currentUserId);
            updatedUser.setCurrentPassword(null);
            updatedUser.setRole("user");

            return userrepos.save(updatedUser);
        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "passwords do not match");
        }
    }

    // ================ ORDERS =========================
    @GetMapping("/orders/orderid/{orderid}")
    public Object findOrderItemsByOrderid(@PathVariable long orderid)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = testIsAdmin(currentuser);

        Order foundOrder = orderrepos.findOrderByUserIdAndOrderId(currentuser.getCurrentUserId(), orderid);

        if (foundOrder != null || isAdmin)
        {
            return orderrepos.findById(orderid).get();
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "order being searched does not belong to current user");
        }
    }

    @GetMapping("/orders/allorders/{userid}")
    public Object getOrderItemsByUserid(@PathVariable long userid)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userid || isAdmin)
        {
            return orderrepos.findAllUserOrdersByUserId(currentUserId);
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "orders being searched does not belong to current user");
        }
    }

}
