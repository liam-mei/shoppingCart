package com.lambdaschool.coffeebean.CriteriaAPIProducts;

import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.model.ReviewItem;

import java.util.List;
import java.util.Set;

public interface ProductrepositoryCustom
{
    List<Product> dynamicQueryWithStringsLike(Set<String> searchArray, int start);

    List<ReviewItem> dynamicQueryForReviewItem(Set<String> searchArray, int start);

}
