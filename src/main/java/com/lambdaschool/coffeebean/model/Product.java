package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.lambdaschool.coffeebean.controller.View;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.util.Date;
import java.util.Set;


@Entity
@Table(name = "products")
//@Check(constraints = "inventory >= 0")
public class Product
{
    @JsonView({View.Essential.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @JsonView({View.Essential.class})
    private String productName;

    @JsonView({View.Essential.class})
    private String description;

    @JsonView({View.Essential.class})
    private Double price;

    @JsonView({View.Essential.class})
    @Min(0)
    private Integer inventory;

    @JsonView({View.Essential.class})
    private String image;

    private Date createdAt = new Date();

    private Date updatedAt = new Date();

    private int totalOrdered = 0;

    // ====== Review ======
    // OneToMany with Review
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "reviewedProduct", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"reviewedProduct", "createdAt", "updatedAt"})
    private Set<Review> productReviews;

    // OneToMany with CartItem - subowner
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "product", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"product", "cart", "createdAt"})
    private Set<CartItem> cartItems;

    // ===== Other Join Tables ========

    // *** ManyToMany with supplier - subowner ***
//    @JsonIgnore
    @ManyToMany(mappedBy = "productsFromSupplier", fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"productsFromSupplier", "createdAt", "updatedAt"})
    private Set<Supplier> suppliers;


    public Product(String productName, String description, Double price, Integer inventory, String image)
    {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.inventory = inventory;
        this.image = image;
    }

    public Product()
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

    public String getImage()
    {
        return image;
    }

    public void setImage(String image)
    {
        this.image = image;
    }

    public Set<Review> getProductReviews()
    {
        return productReviews;
    }

    public void setProductReviews(Set<Review> productReviews)
    {
        this.productReviews = productReviews;
    }

    public Set<CartItem> getCartItems()
    {
        return cartItems;
    }

    public void setCartItems(Set<CartItem> cartItems)
    {
        this.cartItems = cartItems;
    }

    public Set<Supplier> getSuppliers()
    {
        return suppliers;
    }

    public void setSuppliers(Set<Supplier> suppliers)
    {
        this.suppliers = suppliers;
    }

    public Date getCreatedAt()
    {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt)
    {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt()
    {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    public int getTotalOrdered()
    {
        return totalOrdered;
    }

    public void setTotalOrdered(int totalOrdered)
    {
        this.totalOrdered = totalOrdered;
    }
}
