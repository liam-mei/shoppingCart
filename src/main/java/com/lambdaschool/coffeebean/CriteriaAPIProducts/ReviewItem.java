package com.lambdaschool.coffeebean.CriteriaAPIProducts;

public interface ReviewItem
{
    Long getProduct_id();

    String getProduct_name();

    String getDescription();

    String getImage();

    Double getPrice();

    Double getAvgRating();

    Integer getReviewCount();

    Integer getInventory();
}
