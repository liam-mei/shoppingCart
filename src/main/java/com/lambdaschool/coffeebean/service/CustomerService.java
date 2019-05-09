package com.lambdaschool.coffeebean.service;

import com.lambdaschool.coffeebean.model.User;

public interface CustomerService
{
    User findUserByUserid(long userid);

    User findUserByUsername(String username);

    boolean doPasswordsMatch(User updatedUser, User foundUser);

    User updateUser(User updatedUser, User foundUser);
}
