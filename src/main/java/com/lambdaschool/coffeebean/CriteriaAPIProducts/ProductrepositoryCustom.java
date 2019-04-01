package com.lambdaschool.coffeebean.CriteriaAPIProducts;

import com.lambdaschool.coffeebean.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductrepositoryCustom
{
    List<Product> dynamicQueryWithStringsLike(Set<String> searchArray, int start);

    List<ProductWithReview> get10ProductsWithReviewsData(Set<String> searchSet, int start);

}
