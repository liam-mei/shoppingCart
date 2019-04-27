package com.lambdaschool.coffeebean.CriteriaAPIProducts;

public class ProductWithReview
{
    Long productId;
    String productName;
    String description;
    String image;
    Double price;
    Integer inventory;

    Double avgRating;
    Long reviewCount;


    public ProductWithReview(Long productId, String productName, String description, String image, Double price, Integer inventory, Double avgRating, Long reviewCount)
    {
        this.productId = productId;
        this.productName = productName;
        this.description = description;
        this.image = image;
        this.price = price;
        this.inventory = inventory;
        this.avgRating = avgRating;
        this.reviewCount = reviewCount;
    }

    public ProductWithReview()
    {
    }

    public Long getProductId()
    {
        return productId;
    }

    public void setProductId(Long productId)
    {
        this.productId = productId;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public Integer getInventory()
    {
        return inventory;
    }

    public void setInventory(Integer inventory)
    {
        this.inventory = inventory;
    }

    public Double getAvgRating()
    {
        return avgRating;
    }

    public void setAvgRating(Double avgRating)
    {
        this.avgRating = avgRating;
    }

    public Long getReviewCount()
    {
        return reviewCount;
    }

    public void setReviewCount(Long reviewCount)
    {
        this.reviewCount = reviewCount;
    }
}
