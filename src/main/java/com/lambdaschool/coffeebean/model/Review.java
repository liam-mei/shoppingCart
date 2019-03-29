package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.lambdaschool.coffeebean.controller.View;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name = "reviews")
public class Review
{
    @JsonView({View.ReviewOnly.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long reviewid;

    @JsonView({View.ReviewOnly.class})
    private String headline;

    @JsonView({View.ReviewOnly.class})
    private String reviewbody;

    @JsonView({View.ReviewOnly.class})
    Integer stars;

    // ManyToOne with User - Owner
    @JsonView({View.ReviewOnly.class})
    @ManyToOne
    @JoinColumn(name = "userid")
    @JsonIgnoreProperties({"reviews", "orderhistory", "productsincart"})
    private User reviewer;

    // ManyToOne with Product - Owner
    @JsonView({View.ReviewOnly.class})
    @ManyToOne
    @JoinColumn(name = "productid")
    @JsonIgnoreProperties({"productReviews", "productorders", "potentialusers", "suppliers"})
    private Product reviewedProduct;
}
