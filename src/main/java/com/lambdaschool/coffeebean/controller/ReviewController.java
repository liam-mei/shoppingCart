package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.exceptions.ForbiddenException;
import com.lambdaschool.coffeebean.model.Review;
import com.lambdaschool.coffeebean.service.CurrentUser;
import com.lambdaschool.coffeebean.service.ReviewService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Some value... by DKM", description = "Review Controller by DKM")
@RestController
@RequestMapping(path = "/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReviewController
{
    @Autowired
    ReviewService reviewService;

    @GetMapping
    public ResponseEntity<?> getAllReviews()
    {
        List<Review> allReviews = reviewService.getAllReviews();
        return new ResponseEntity<>(allReviews, HttpStatus.OK);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<?> getReviewById(@PathVariable long reviewId)
    {
        Review foundReview = reviewService.getReviewById(reviewId);
        return new ResponseEntity<>(foundReview, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewReview(@RequestBody Review newReview)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Review savedReview = reviewService.addNewReview(newReview, currentUserId);
        return new ResponseEntity<>(savedReview, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateReview(@RequestBody Review updatedReview)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Review foundReview = reviewService.getReviewByUserIdAndReviewId(currentUserId, updatedReview.getReviewId());

        if (foundReview != null)
        {
            Review savedReview = reviewService.updateReview(updatedReview, foundReview);
            return new ResponseEntity<>(savedReview, HttpStatus.OK);
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "review being modified does not belong to current user");
        }
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<?> deleteReviewById(@PathVariable long reviewId)
    {
        CurrentUser currentuser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        long currentUserId = currentuser.getCurrentUserId();

        Review foundReview = reviewService.getReviewByUserIdAndReviewId(currentUserId, reviewId);

        if (foundReview != null)
        {
            Review deletedReview = reviewService.deleteReviewById(reviewId);
            return new ResponseEntity<>(deletedReview, HttpStatus.OK);
        }
        else
        {
            throw new ForbiddenException(HttpStatus.FORBIDDEN, "review being deleted does not belong to current user");
        }
    }
}
