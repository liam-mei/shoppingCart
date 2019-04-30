package com.lambdaschool.coffeebean.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.lambdaschool.coffeebean.CriteriaAPIProducts.ProductWithReview;
import com.lambdaschool.coffeebean.CriteriaAPIProducts.ReviewItem;
import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.model.Review;
import com.lambdaschool.coffeebean.repository.ProductRepository;
import com.lambdaschool.coffeebean.repository.ReviewRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Api(value = "Some value... by DKM", description = "Shop Controller by DKM")
@RestController
@RequestMapping(path = "/shop", produces = MediaType.APPLICATION_JSON_VALUE)
public class ShopController
{
    @Autowired
    ProductRepository productrepos;

    @Autowired
    ReviewRepository reviewrepos;

    @JsonView(View.Essential.class)
    @ApiOperation(value = "find all products - DKM", response = Product.class)
    @GetMapping("")
    public List<Product> getAllProducts()
    {
        return productrepos.findAll();
    }

    @JsonView(View.Essential.class)
    @GetMapping("/{page}")
    public List<Product> get10Products(@PathVariable int page)
    {
        int start = (page - 1) * 10;
        return productrepos.get10Products(start);
    }

    @JsonView(View.Essential.class)
    @GetMapping("/naturalsearch/{searchString}/page/{page}")
    public List<Product> naturalSearchForProductByName(@PathVariable String searchString, @PathVariable int page)
    {
        int start = (page - 1) * 10;
        return productrepos.naturalSearchForProductByName(searchString, start);
    }


    @JsonView(View.Essential.class)
    @GetMapping("/criteria/{searchString}/page/{page}")
    public List<Product> dynamicQueryWithStringsLike(@PathVariable String searchString, @PathVariable int page)
    {
        String[] searchArray = searchString.split(" ");
        Set<String> searchSet = new HashSet<>(Arrays.asList(searchArray));

        int start = (page - 1) * 10;

        return productrepos.dynamicQueryWithStringsLike(searchSet, start);
    }

    // ====== REVIEWS =========

    @JsonView(View.Essential.class)
    @GetMapping("/reviews")
    public List<Review> getAllReviews()
    {
        return reviewrepos.findAll();
    }

    @GetMapping("/reviewitems")
    public List<ReviewItem> getAllReviewItems()
    {
        return reviewrepos.getAllReviewItems();
    }

    // ============= Use the Routes Below Only =================

    @GetMapping("/reviewitems/page/{page}/orderby/{orderBy}/ascdesc/{ascending}")
    public List<ProductWithReview> get10ReviewItemsByPage(@PathVariable int page,
                                                   @PathVariable String orderBy,
                                                   @PathVariable String ascending)
    {
        int start = (page - 1) * 10;

        return productrepos.get10ReviewItemsByPage(start, ascending, orderBy);
    }

    @GetMapping("/search/productwithreview/{searchString}/page/{page}/orderby/{orderBy}/ascdesc/{ascending}")
    public List<ProductWithReview> searchFor10ProductsWithReviewsDataBySearchString(
            @PathVariable String searchString,
            @PathVariable int page,
            @PathVariable String orderBy,
            @PathVariable String ascending)
    {
        String[] searchArray = searchString.split(" ");
        Set<String> searchSet = new HashSet<>(Arrays.asList(searchArray));

        int start = (page - 1) * 10;

        return productrepos.searchFor10ProductsWithReviewsDataBySearchString(searchSet, start, ascending, orderBy);
    }
}
