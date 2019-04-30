package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.Review;
import com.lambdaschool.coffeebean.CriteriaAPIProducts.ReviewItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>
{
    @Query(value =
            "SELECT p.product_id AS productId, p.product_name as productName, p.description, p.image, p.price, " +
                    "AVG(r.stars) AS avgRating, COUNT(r.product_id) AS reviewCount, p.inventory " +
                    "FROM products p " +
                    "LEFT OUTER JOIN reviews r ON p.product_id=r.product_id " +
                    "GROUP BY p.product_id, p.product_name " +
                    "ORDER BY avgRating DESC, p.product_name", nativeQuery = true)
    List<ReviewItem> getAllReviewItems();

    @Query(value =
            "SELECT p.product_id AS productId, p.product_name as productName, p.description, p.image, p.price, AVG(r.stars) AS avgRating, " +
                    "COUNT(r.product_id) AS reviewCount, p.inventory " +
                    "FROM products p " +
                    "LEFT OUTER JOIN reviews r ON p.product_id=r.product_id " +
                    "GROUP BY p.product_id, p.product_name, p.description, p.image, p.price " +
                    "ORDER BY avgRating DESC, p.product_name LIMIT :start, 10", nativeQuery = true)
    List<ReviewItem> get10ReviewItemsByPage(int start);

    @Query(value = "SELECT * FROM reviews WHERE user_id = :userId AND review_id = :reviewId", nativeQuery = true)
    Review getReviewByUserIdAndReviewId(long userId, long reviewId);
}
