package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Address;
import com.lambdaschool.coffeebean.repository.AddressRepository;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@Api(value = "Some value... by DKM", description = "Address Controller by DKM")
@RestController
@RequestMapping(path = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController extends CheckIsAdmin
{
    @Autowired
    AddressRepository addressrepos;

    @GetMapping
    List<Address> getAllAddresses()
    {
        return addressrepos.findAll();
    }

    @GetMapping("/{addressId}")
    Address getAddressById(@PathVariable long addressId)
    {
        return addressrepos.findById(addressId).get();
    }

    @GetMapping("/displayedaddresses")
    List<Address> getDisplayedAddresses()
    {
        return null;
    }

    @GetMapping("/hiddenaddresses")
    List<Address> getHiddenAddresses()
    {
        return null;
    }

    @PostMapping
    Address postNewAddress(@RequestBody Address newAddress)
    {
        return addressrepos.save(newAddress);
    }

    @PutMapping
    Address updateAddressById(@RequestBody Address updatedAddress)
    {
        return addressrepos.save(updatedAddress);
    }

    @PutMapping("/toggledisplay/{addressId}")
    Address toggleIfAddressDisplayed(@PathVariable long addressId)
    {
        Address foundAddress = addressrepos.findById(addressId).get();
        foundAddress.setDisplay(!foundAddress.isDisplay());
        return addressrepos.save(foundAddress);
    }


}
