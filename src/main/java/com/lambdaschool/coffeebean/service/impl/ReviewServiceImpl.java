package com.lambdaschool.coffeebean.service.impl;

import com.lambdaschool.coffeebean.model.Review;
import com.lambdaschool.coffeebean.repository.ReviewRepository;
import com.lambdaschool.coffeebean.repository.UserRepository;
import com.lambdaschool.coffeebean.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ReviewServiceImpl implements ReviewService
{
    @Autowired
    ReviewRepository reviewRepos;

    @Autowired
    UserRepository userRepos;

    @Override
    public List<Review> getAllReviews()
    {
        return reviewRepos.findAll();
    }

    @Override
    public Review getReviewById(long reviewId)
    {
        return reviewRepos.findById(reviewId)
                .orElseThrow(()->new EntityNotFoundException(Long.toString(reviewId)));
    }

    @Transactional
    @Override
    public Review addNewReview(Review newReview, long userId)
    {
        newReview.setReviewId(null);
        newReview.setReviewer(userRepos.findById(userId).get());
        return reviewRepos.save(newReview);
    }

    @Override
    public Review getReviewByUserIdAndReviewId(long currentUserId, long reviewId)
    {
        return reviewRepos.getReviewByUserIdAndReviewId(currentUserId, reviewId);
    }

    @Transactional
    @Override
    public Review updateReview(Review updatedReview, Review foundReview)
    {
        if (updatedReview.getReviewedProduct() == null) updatedReview.setReviewedProduct(foundReview.getReviewedProduct());
        if (updatedReview.getReviewBody() == null) updatedReview.setReviewBody(foundReview.getReviewBody());
        if (updatedReview.getHeadline() == null) updatedReview.setHeadline(foundReview.getHeadline());
        if (updatedReview.getStars() == null) updatedReview.setStars(foundReview.getStars());

        updatedReview.setReviewer(foundReview.getReviewer());
        updatedReview.setCreatedAt(foundReview.getCreatedAt());
        updatedReview.setUpdatedAt(new Date());
        return reviewRepos.save(updatedReview);
    }

    @Transactional
    @Override
    public Review deleteReviewById(long reviewId)
    {
        Review foundReview = reviewRepos.findById(reviewId).get();
        reviewRepos.deleteById(reviewId);
        return foundReview;
    }
}
