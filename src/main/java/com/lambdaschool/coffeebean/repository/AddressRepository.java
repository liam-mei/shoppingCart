package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long>
{
    @Query(value = "SELECT * FROM addresses WHERE (user_id = :userId AND address_id = :addressId)", nativeQuery = true)
    Address findAddressByUserIdAndAddressId(long userId, long addressId);

    @Query(value = "SELECT * FROM addresses WHERE (user_id = :userId AND display = 1)", nativeQuery = true)
    List<Address> findDisplayAddresses(long userId);

    @Query(value = "SELECT * FROM addresses WHERE (user_id = :userId AND display = 0)", nativeQuery = true)
    List<Address> findHiddenAddresses(long userId);
}
