package com.lambdaschool.coffeebean.CriteriaAPIProducts;

import com.lambdaschool.coffeebean.model.Product;

import java.util.List;
import java.util.Set;

public interface ProductrepositoryCustom
{
    List<Product> dynamicQueryWithStringsLike(Set<String> searchArray, int start);

    List<ProductWithReview> searchFor10ProductsWithReviewsDataBySearchString(Set<String> searchSet, int start, String ascending, String orderBy);

    List<ProductWithReview> get10ReviewItemsByPage(int start, String ascending, String orderBy);

}
