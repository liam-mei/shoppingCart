package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Cart;
import com.lambdaschool.coffeebean.model.User;
import com.lambdaschool.coffeebean.repository.UserRepository;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController extends CheckIsAdmin
{

    @Autowired
    // private UserService userService;
    private UserRepository userrepos;

    @GetMapping("")
    public List<User> listAllUsers()
    {
        return userrepos.findAll();
    }

    @GetMapping("/{userId}")
    public User findUserByUserid(@PathVariable long userId)
    {
        return userrepos.findById(userId).get();
    }

    @PostMapping("/addadmin")
    public Object addNewUser(@RequestBody User newuser) throws URISyntaxException
    {
        HashMap<String, Object> returnObject = CheckIsAdmin.checkUniqueUserParameters(newuser, userrepos);
        if (!returnObject.isEmpty()) return returnObject;

        newuser.setCart(new Cart());
        newuser.setUserId(null);
        newuser.setRole("admin");
        return userrepos.save(newuser);
    }

    @DeleteMapping("/{id}")
    public HashMap deleteUserById(@PathVariable long id)
    {
        var foundUser = userrepos.findById(id);
        if (foundUser.isPresent())
        {
            userrepos.deleteById(id);

            return new HashMap<>()
            {{
                put("username", foundUser.get().getUsername());
                put("userid", foundUser.get().getUserId());
                put("deleted", true);
            }};
        } else
        {
            return new HashMap<>()
            {{
                put("error", "user not found");
                put("deleted", false);
            }};
        }
    }
}