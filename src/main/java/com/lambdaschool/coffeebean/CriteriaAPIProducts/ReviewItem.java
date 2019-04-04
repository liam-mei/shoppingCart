package com.lambdaschool.coffeebean.model;

public interface ReviewItem
{
    long getProductid();

    String getProductname();

    String getDescription();

    String getImage();

    double getPrice();

    Double getAvgRating();

    Integer getReviewCount();

    Integer getTotalProductCount();
}
