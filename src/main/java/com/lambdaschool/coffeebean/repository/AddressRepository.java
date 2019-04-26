package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long>
{
}
