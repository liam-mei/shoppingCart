package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Review;
import com.lambdaschool.coffeebean.repository.ReviewRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value = "Some value... by DKM", description = "Review Controller by DKM")
@RestController
@RequestMapping(path = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController
{
    @Autowired
    ReviewRepository reviewrepos;

    @GetMapping
    public List<Review> getAllReviews()
    {
        return reviewrepos.findAll();
    }

    @GetMapping("/{reviewId}")
    public Review getReviewById(@PathVariable long reviewId)
    {
        return reviewrepos.findById(reviewId).get();
    }

    @PostMapping
    public Review postNewReview(@RequestBody Review newReview)
    {
        return reviewrepos.save(newReview);
    }

    @PutMapping
    public Review updateReview(@RequestBody Review updatedReview)
    {
        Optional<Review> foundReview = reviewrepos.findById(updatedReview.getReviewId());

        if (foundReview.isPresent())
        {
            if (updatedReview.getHeadline() == null) updatedReview.setHeadline(foundReview.get().getHeadline());
            if (updatedReview.getReviewBody() == null) updatedReview.setReviewBody(foundReview.get().getReviewBody());
            if (updatedReview.getReviewedProduct() == null) updatedReview.setReviewedProduct(foundReview.get().getReviewedProduct());
            if (updatedReview.getReviewer() == null) updatedReview.setReviewer(foundReview.get().getReviewer());
            if (updatedReview.getStars() == null) updatedReview.setStars(foundReview.get().getStars());

            return reviewrepos.save(updatedReview);
        }
        else
        {
            return null;
        }
    }

    @DeleteMapping("/{reviewId}")
    public Review deleteReviewById(@PathVariable long reviewId)
    {
        Optional<Review> foundReview = reviewrepos.findById(reviewId);
        if (foundReview.isPresent())
        {
            reviewrepos.deleteById(reviewId);
            return foundReview.get();
        }
        else
        {
            return null;
        }
    }
}
