package com.lambdaschool.coffeebean.service.impl;

import com.lambdaschool.coffeebean.model.User;
import com.lambdaschool.coffeebean.repository.OrderRepository;
import com.lambdaschool.coffeebean.repository.UserRepository;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import com.lambdaschool.coffeebean.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;

@Transactional
@Service
public class CustomerServiceImpl extends CheckIsAdmin implements CustomerService
{
    @Autowired
    UserRepository userrepos;

    @Autowired
    OrderRepository orderrepos;

    @Override
    public User findUserByUserid(long userid)
    {
        return userrepos.findById(userid)
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(userid)));
    }

    @Override
    public User findUserByUsername(String username)
    {
        User foundUser = userrepos.findByUsername(username);
        if (foundUser != null)
        {
            return foundUser;
        }
        else
        {
            throw new EntityNotFoundException(username);
        }
    }

    @Override
    public boolean doPasswordsMatch(User updatedUser, User foundUser)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String unencryptedPassword = updatedUser.getCurrentPassword();
        String currentEncryptedPassword = foundUser.getPassword();
        return passwordEncoder.matches(unencryptedPassword, currentEncryptedPassword);
    }

    @Transactional
    @Override
    public User updateUser(User updatedUser, User foundUser)
    {
        // User - personal details field - can be user changed
        if (updatedUser.getCustomerPhone() == null) updatedUser.setCustomerPhone(foundUser.getCustomerPhone());
        if (updatedUser.getMiddleName() == null) updatedUser.setMiddleName(foundUser.getMiddleName());
        if (updatedUser.getFirstName() == null) updatedUser.setFirstName(foundUser.getFirstName());
        if (updatedUser.getLastName() == null) updatedUser.setLastName(foundUser.getLastName());
        if (updatedUser.getUsername() == null) updatedUser.setUsername(foundUser.getUsername());
        if (updatedUser.getPassword() == null) updatedUser.setPassword(updatedUser.getCurrentPassword());
        if (updatedUser.getEmail() == null) updatedUser.setEmail(foundUser.getEmail());
        if (updatedUser.isReceiveEmails())
        {
            updatedUser.setReceiveEmails(true);
        }
        else
        {
            updatedUser.setReceiveEmails(false);
        }

        // fields user can't change or can't change at this url
        updatedUser.setOrderHistory(foundUser.getOrderHistory());
        updatedUser.setAddresses(foundUser.getAddresses());
        updatedUser.setCreatedAt(foundUser.getCreatedAt());
        updatedUser.setReviews(foundUser.getReviews());
        updatedUser.setUserId(foundUser.getUserId());
        updatedUser.setCart(foundUser.getCart());
        updatedUser.setCurrentPassword(null);
        updatedUser.setUpdatedAt(new Date());
        updatedUser.setRole("user");

        return userrepos.save(updatedUser);
    }
}
