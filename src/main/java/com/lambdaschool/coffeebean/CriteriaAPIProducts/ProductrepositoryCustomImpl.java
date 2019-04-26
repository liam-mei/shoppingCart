package com.lambdaschool.coffeebean.CriteriaAPIProducts;

import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.model.Review;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ProductrepositoryCustomImpl implements ProductrepositoryCustom
{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> dynamicQueryWithStringsLike(Set<String> searchSet, int start)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> productRoot = query.from(Product.class);

        Path<String> productPath = productRoot.get("productName");

        List<Predicate> predicates = new ArrayList<>();
        for (String word : searchSet)
        {
            word = "%" + word + "%";
            predicates.add(cb.like(productPath, word));
        }

        query.select(productRoot)
                .where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        return entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(10)
                .getResultList();
    }

    @Override
    public List<ProductWithReview> get10ProductsWithReviewsData(Set<String> searchSet, int start)
    {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<ProductWithReview> query = cb.createQuery(ProductWithReview.class);
        Root<Product> productRoot = query.from(Product.class);
        Path<String> productPath = productRoot.get("productName");

        Join<Product, Review> reviewJoin = productRoot.join("productReviews", JoinType.LEFT);

        query
                .select(cb.construct(ProductWithReview.class,
                      productRoot.get("productId"),
                      productRoot.get("productName"),
                      productRoot.get("description"),
                      productRoot.get("image"),
                      productRoot.get("price"),
                      productRoot.get("inventory"),
                      cb.avg(reviewJoin.get("stars")),
                      cb.countDistinct(reviewJoin)))
                .where(cb.and(searchSet.stream()
                      .map(word -> cb.like(productPath, "%" + word + "%"))
                      .toArray(Predicate[]::new)))
                .groupBy(
                      productRoot.get("productId"), productRoot.get("productName"),
                      productRoot.get("description"), productRoot.get("image"),
                      productRoot.get("price"))
                .orderBy(cb.desc(cb.avg(reviewJoin.get("stars"))));

        List<ProductWithReview> resultList = entityManager.createQuery(query)
                .setFirstResult(start)
                .setMaxResults(10)
                .getResultList();

        return resultList;
    }
}
