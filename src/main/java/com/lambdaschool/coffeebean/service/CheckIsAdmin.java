package com.lambdaschool.coffeebean.service;

import com.lambdaschool.coffeebean.model.User;
import com.lambdaschool.coffeebean.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.HashMap;
import java.util.List;

public class CheckIsAdmin
{
    protected boolean testIsAdmin(CurrentUser currentuser)
    {
        List<? extends SimpleGrantedAuthority> authorities = currentuser.getAuthorities2();
        boolean isAdmin = false;

        for (SimpleGrantedAuthority authority : authorities)
        {
            if (authority.getAuthority().equalsIgnoreCase("ROLE_ADMIN")) isAdmin = true;
        }

        return isAdmin;
    }

    protected HashMap<String, Object> doesUsernameMatch(Long currentUserId, Long userId, boolean matches)
    {
        return new HashMap<>()
        {{
            put("YourUserId", currentUserId);
            put("SearchedUserId", userId);
            put("UserIdMatches", matches);
        }};
    }

    public static HashMap<String, Object> CheckUsernameEmailIsUnique(User newuser, UserRepository userrepos)
    {
        String username = newuser.getUsername();
        String email = newuser.getEmail();
        String phone = newuser.getCustomerPhone();
        HashMap<String, Object> returnObject = new HashMap<>();

        if (username != null && userrepos.existsByUsername(username)) returnObject.put("usernameAlreadyExists", true);
        if (phone != null && userrepos.existsByCustomerPhone(phone)) returnObject.put("phoneAlreadyExists", true);
        if (email != null && userrepos.existsByEmail(email)) returnObject.put("emailAlreadyExists", true);
        return returnObject;
    }
}
