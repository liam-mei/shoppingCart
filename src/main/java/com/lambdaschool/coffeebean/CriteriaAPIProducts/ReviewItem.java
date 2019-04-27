package com.lambdaschool.coffeebean.CriteriaAPIProducts;

public interface ReviewItem
{
    Long getProductId();

    String getProductName();

    String getDescription();

    String getImage();

    Double getPrice();

    Double getAvgRating();

    Integer getReviewCount();

    Integer getInventory();
}
