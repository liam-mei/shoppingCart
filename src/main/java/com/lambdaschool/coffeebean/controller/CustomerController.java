package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.model.User;
import com.lambdaschool.coffeebean.repository.OrderRepository;
import com.lambdaschool.coffeebean.repository.UserRepository;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import com.lambdaschool.coffeebean.service.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/customer", produces = MediaType.APPLICATION_JSON_VALUE)
public class CustomerController extends CheckIsAdmin
{
    @Autowired
    UserRepository userrepos;

    @Autowired
    OrderRepository orderrepos;

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
            return doesUsernameMatch(currentUserId, userid, false);
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
                return new HashMap<String, Object>();
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
            if (updatedUser.getUsername() != null && userrepos.findByUsername(updatedUser.getUsername()) != null)
                returnObject.put("usernameExists", true);
            if (updatedUser.getEmail() != null && userrepos.findByEmail(updatedUser.getEmail()) != null)
                returnObject.put("emailExists", true);
            if (!returnObject.isEmpty()) return returnObject;

            // User - personal details field - can be user changed
//            if (updatedUser.getShippingaddress() == null)
//                updatedUser.setShippingaddress(foundUser.getShippingaddress());
//            if (updatedUser.getBillingaddress() == null) updatedUser.setBillingaddress(foundUser.getBillingaddress());
            if (updatedUser.getCustomerPhone() == null) updatedUser.setCustomerPhone(foundUser.getCustomerPhone());
//            if (updatedUser.getPaymentmethod() == null) updatedUser.setPaymentmethod(foundUser.getPaymentmethod());
            if (updatedUser.getMiddleName() == null) updatedUser.setMiddleName(foundUser.getMiddleName());
            if (updatedUser.getFirstName() == null) updatedUser.setFirstName(foundUser.getFirstName());
            if (updatedUser.getLastName() == null) updatedUser.setLastName(foundUser.getLastName());
            if (updatedUser.getUsername() == null) updatedUser.setUsername(foundUser.getUsername());
            if (updatedUser.getPassword() == null) updatedUser.setPassword(foundUser.getPassword());
            if (updatedUser.getEmail() == null) updatedUser.setEmail(foundUser.getEmail());
            if (updatedUser.getRole() == null) updatedUser.setRole(foundUser.getRole());

            // fields user can't change
            updatedUser.setUserId(currentUserId);
            updatedUser.setOrderHistory(foundUser.getOrderHistory());
            updatedUser.setCurrentPassword(null);

            return userrepos.save(updatedUser);
        } else
        {
            returnObject.put("passwordMatches", false);
            return returnObject;
        }
    }

    // ================ ORDERS =========================
    @GetMapping("/orders/orderid/{orderid}")
    public Object findOrderItemsByOrderid(@PathVariable long orderid)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean isAdmin = testIsAdmin(currentuser);
        boolean orderBelongs = false;

        List<Order> userOrders = orderrepos.findAll();

        for ( int i=0; i<userOrders.size(); i++)
        {
            if (userOrders.get(i).getOrderId() == orderid) orderBelongs = true;
        }
        if (orderBelongs || isAdmin)
        {
            return orderrepos.findById(orderid).get();
        }
        else
        {
            return "order does not belong to you";
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
            return orderrepos.findAll();
        }
        else
        {
            return doesUsernameMatch(currentUserId, userid, false);
        }
    }

}
