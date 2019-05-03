package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.exceptions.ForbiddenException;
import com.lambdaschool.coffeebean.model.Address;
import com.lambdaschool.coffeebean.repository.AddressRepository;
import com.lambdaschool.coffeebean.repository.UserRepository;
import com.lambdaschool.coffeebean.service.CheckIsAdmin;
import com.lambdaschool.coffeebean.service.CurrentUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@CrossOrigin
@Api(value = "Some value... by DKM", description = "Address Controller by DKM")
@RestController
@RequestMapping(path = "/address", produces = MediaType.APPLICATION_JSON_VALUE)
public class AddressController extends CheckIsAdmin
{
    @Autowired
    AddressRepository addressrepos;

    @Autowired
    UserRepository userrepos;

//    @GetMapping
//    List<Address> getAllAddresses()
//    {
//        return addressrepos.findAll();
//    }

    @GetMapping("/{addressId}")
    Object getAddressById(@PathVariable long addressId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Address foundAddress = addressrepos.findAddressByUserIdAndAddressId(currentUserId, addressId);

        if (foundAddress != null)
        {
            return foundAddress;
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "address being searched does not belong to current user");
        }
    }

    @GetMapping("/displayedaddresses")
    List<Address> getDisplayedAddresses()
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        return addressrepos.findDisplayAddresses(currentUserId);
    }

    @GetMapping("/hiddenaddresses")
    List<Address> getHiddenAddresses()
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        return addressrepos.findHiddenAddresses(currentUserId);
    }

    @PostMapping
    Address postNewAddress(@RequestBody Address newAddress)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        newAddress.setAddressId(null);
        newAddress.setUser(userrepos.findById(currentUserId).get());
        return addressrepos.save(newAddress);
    }

    @PutMapping
    Object updateAddressById(@RequestBody Address updatedAddress)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Address foundAddress = addressrepos.findAddressByUserIdAndAddressId(currentUserId, updatedAddress.getAddressId());

        if ( foundAddress != null )
        {
            if (updatedAddress.getZipcode() == null ) updatedAddress.setZipcode(foundAddress.getZipcode());
            if (updatedAddress.getStreet() == null ) updatedAddress.setStreet(foundAddress.getStreet());
            if (updatedAddress.getState() == null ) updatedAddress.setState(foundAddress.getState());
            if (updatedAddress.getCity() == null ) updatedAddress.setCity(foundAddress.getCity());

            updatedAddress.setUser(userrepos.findById(currentUserId).get());
            updatedAddress.setCreatedAt(foundAddress.getCreatedAt());
            updatedAddress.setDisplay(foundAddress.isDisplay());
            updatedAddress.setUpdatedAt(new Date());
            return addressrepos.save(updatedAddress);
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "address being modified does not belong to current user");
        }
    }

    @PutMapping("/toggledisplay/{addressId}")
    Object toggleIfAddressDisplayed(@PathVariable long addressId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Address foundAddress = addressrepos.findAddressByUserIdAndAddressId(currentUserId, addressId);

        if (foundAddress != null)
        {
            foundAddress.setDisplay(!foundAddress.isDisplay());
            return addressrepos.save(foundAddress);
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "address being modified does not belong to current user");
        }
    }


}
