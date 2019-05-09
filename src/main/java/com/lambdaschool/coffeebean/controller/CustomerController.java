package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.exceptions.ForbiddenException;
import com.lambdaschool.coffeebean.model.Order;
import com.lambdaschool.coffeebean.model.User;
import com.lambdaschool.coffeebean.repository.OrderRepository;
import com.lambdaschool.coffeebean.repository.UserRepository;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import com.lambdaschool.coffeebean.service.CurrentUser;
import com.lambdaschool.coffeebean.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    CustomerService customerService;

    @GetMapping("/userid/{userid}")
    public Object findUserByUserid(@PathVariable long userid)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        boolean isAdmin = testIsAdmin(currentuser);

        if (currentUserId == userid || isAdmin)
        {
            return customerService.findUserByUserid(userid);

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
                return customerService.findUserByUsername(username);

            } else
            {
                throw new ForbiddenException(HttpStatus.FORBIDDEN, "user being searched does not match current user");
            }
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User updatedUser)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        User foundUser = customerService.findUserByUserid(currentUserId);

        boolean passwordsMatch = customerService.doPasswordsMatch(updatedUser, foundUser);

        if (passwordsMatch)
        {
            HashMap<String, Object> nonUniqueParameters = checkUpdatedUserParemeters(updatedUser, foundUser, userrepos);
            if (!nonUniqueParameters.isEmpty()) return new ResponseEntity<>(nonUniqueParameters, HttpStatus.FORBIDDEN);

            User savedUser = customerService.updateUser(updatedUser, foundUser);
            return new ResponseEntity<>(savedUser, HttpStatus.OK);
        } else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "passwords do not match");
        }
    }

    // ================ ORDERS =========================
    @GetMapping("/orders/orderid/{orderid}")
    public Object findOrderByOrderid(@PathVariable long orderid)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Order foundOrder = orderrepos.findOrderByUserIdAndOrderId(currentuser.getCurrentUserId(), orderid);

        if (foundOrder != null)
        {
            return orderrepos.findById(orderid).get();
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "order being searched does not belong to current user");
        }
    }

    @GetMapping("/orders/allorders/{userid}")
    public Object findAllOrdersByUserid(@PathVariable long userid)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        if (currentUserId == userid)
        {
            return orderrepos.findAllUserOrdersByUserId(currentUserId);
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "orders being searched does not belong to current user");
        }
    }

}
