package com.lambdaschool.coffeebean.service;

import com.lambdaschool.coffeebean.model.Review;

import java.util.List;

public interface ReviewService
{
    List<Review> getAllReviews();

    Review getReviewById(long reviewId);

    Review getReviewByUserIdAndReviewId(long currentUserId, long reviewId);

    Review addNewReview(Review newReview, long userId);

    Review updateReview(Review updatedReview, Review foundReview);

    Review deleteReviewById(long reviewId);
}
