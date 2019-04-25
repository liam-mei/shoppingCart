package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.Review;
import com.lambdaschool.coffeebean.CriteriaAPIProducts.ReviewItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>
{
    @Query(value =
            "SELECT p.productid, p.productname, p.description, p.image, p.price, " +
                    "AVG(r.stars) AS avgRating, COUNT(r.productid) AS reviewCount, COUNT(p.productid) as totalProductCount " +
                    "FROM products p " +
                    "LEFT OUTER JOIN reviews r ON p.productid=r.productid " +
                    "GROUP BY p.productid, p.productname " +
                    "ORDER BY avgRating DESC, p.productname", nativeQuery = true)
    List<ReviewItem> getAllReviewItems();

    @Query(value =
            "SELECT p.productid, p.productname, p.description, p.image, p.price, AVG(r.stars) AS avgRating, " +
                    "COUNT(r.productid) AS reviewCount, (SELECT COUNT(*) " +
                    "FROM products) AS totalProductCount " +
                    "FROM products p " +
                    "LEFT OUTER JOIN reviews r ON p.productid=r.productid " +
                    "GROUP BY p.productid, p.productname, p.description, p.image, p.price " +
                    "ORDER BY avgRating DESC, p.productname LIMIT :start, 10", nativeQuery = true)
    List<ReviewItem> get10ReviewItemsByPage(int start);
}
