package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;


@Entity
@Table(name = "reviews")
public class Review
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewId;

    private String headline;

    private String reviewBody;

    Integer stars;

    // ManyToOne with User - Owner
    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonIgnoreProperties({
            "reviews", "orderHistory", "cart", "currentPassword",
            "middleName", "lastName", "customerPhone", "receiveEmails"})
    private User reviewer;

    // ManyToOne with Product - Owner
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

    public void setReviewId(long reviewId)
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
