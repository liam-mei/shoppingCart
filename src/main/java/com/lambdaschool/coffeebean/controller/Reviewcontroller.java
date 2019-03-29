package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Review;
import com.lambdaschool.coffeebean.repository.Reviewrepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "Some value... by DKM", description = "Review Controller by DKM")
@RestController
@RequestMapping(path = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class Reviewcontroller
{
    @Autowired
    Reviewrepository reviewrepos;

    @GetMapping
    public List<Review> getAllReviews()
    {
        return reviewrepos.findAll();
    }
}
