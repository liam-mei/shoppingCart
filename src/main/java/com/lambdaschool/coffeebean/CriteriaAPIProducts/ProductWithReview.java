package com.lambdaschool.coffeebean.CriteriaAPIProducts;

import lombok.Data;

@Data
public class ProductWithReview
{
    Long productid;
    String productname;
    String description;
    String image;
    Double price;

    Double AvgRating;
    Long ReviewCount;

    public ProductWithReview(Long productid, String productname, String description, String image, Double price, Double AvgRating, Long ReviewCount)
    {
        this.productid = productid;
        this.productname = productname;
        this.description = description;
        this.image = image;
        this.price = price;
        this.AvgRating = AvgRating;
        this.ReviewCount = ReviewCount;
    }
}
