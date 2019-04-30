package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.lambdaschool.coffeebean.controller.View;

import javax.persistence.*;


@Entity
@Table(name = "reviews")
public class Review
{
    @JsonView(View.Essential.class)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    @JsonView(View.Essential.class)
    private String headline;

    @JsonView(View.Essential.class)
    private String reviewBody;

    @JsonView(View.Essential.class)
    Integer stars;

    // ManyToOne with User - Owner
    @JsonView(View.Essential.class)
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({
            "reviews", "orderHistory", "cart", "currentPassword",
            "middleName", "lastName", "customerPhone", "receiveEmails",
            "addresses", "email", "firstName"})
    private User reviewer;

    // ManyToOne with Product - Owner
    @JsonView(View.Essential.class)
    @ManyToOne
    @JoinColumn(name = "productId")
    @JsonIgnoreProperties({"productReviews", "cartItems", "suppliers"})
    private Product reviewedProduct;

    public Review()
    {
    }

    public long getReviewId()
    {
        return reviewId;
    }

    public void setReviewId(Long reviewId)
    {
        this.reviewId = reviewId;
    }

    public String getHeadline()
    {
        return headline;
    }

    public void setHeadline(String headline)
    {
        this.headline = headline;
    }

    public String getReviewBody()
    {
        return reviewBody;
    }

    public void setReviewBody(String reviewBody)
    {
        this.reviewBody = reviewBody;
    }

    public Integer getStars()
    {
        return stars;
    }

    public void setStars(Integer stars)
    {
        this.stars = stars;
    }

    public User getReviewer()
    {
        return reviewer;
    }

    public void setReviewer(User reviewer)
    {
        this.reviewer = reviewer;
    }

    public Product getReviewedProduct()
    {
        return reviewedProduct;
    }

    public void setReviewedProduct(Product reviewedProduct)
    {
        this.reviewedProduct = reviewedProduct;
    }
}
