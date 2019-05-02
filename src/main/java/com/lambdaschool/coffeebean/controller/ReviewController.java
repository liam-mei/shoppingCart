package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.exceptions.ForbiddenException;
import com.lambdaschool.coffeebean.model.Review;
import com.lambdaschool.coffeebean.repository.ReviewRepository;
import com.lambdaschool.coffeebean.repository.UserRepository;
import com.lambdaschool.coffeebean.service.CurrentUser;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Api(value = "Some value... by DKM", description = "Review Controller by DKM")
@RestController
@RequestMapping(path = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController
{
    @Autowired
    ReviewRepository reviewrepos;

    @Autowired
    UserRepository userrepos;

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
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        newReview.setReviewId(null);
        newReview.setReviewer(userrepos.findById(currentUserId).get());
        return reviewrepos.save(newReview);
    }

    @PutMapping
    public Object updateReview(@RequestBody Review updatedReview)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Review foundReview = reviewrepos.getReviewByUserIdAndReviewId(currentUserId, updatedReview.getReviewId());

        if (foundReview != null)
        {
            if (updatedReview.getReviewedProduct() == null) updatedReview.setReviewedProduct(foundReview.getReviewedProduct());
            if (updatedReview.getReviewBody() == null) updatedReview.setReviewBody(foundReview.getReviewBody());
            if (updatedReview.getHeadline() == null) updatedReview.setHeadline(foundReview.getHeadline());
            if (updatedReview.getStars() == null) updatedReview.setStars(foundReview.getStars());

            updatedReview.setReviewer(foundReview.getReviewer());
            updatedReview.setCreatedAt(foundReview.getCreatedAt());
            updatedReview.setUpdatedAt(new Date());
            return reviewrepos.save(updatedReview);
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "review being modified does not belong to current user");
        }
    }

    @DeleteMapping("/{reviewId}")
    public Review deleteReviewById(@PathVariable long reviewId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Review foundReview = reviewrepos.getReviewByUserIdAndReviewId(currentUserId, reviewId);

        if (foundReview != null)
        {
            reviewrepos.deleteById(reviewId);
            return foundReview;
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "review being deleted does not belong to current user");
        }
    }
}
