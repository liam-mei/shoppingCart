package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>
{
    User findByUsername(String username);
    User findByEmail(String email);
    User findByCustomerPhone(String phone);

    boolean existsByUsername(String userName);
    boolean existsByEmail(String email);
    boolean existsByCustomerPhone(String phone);
}
