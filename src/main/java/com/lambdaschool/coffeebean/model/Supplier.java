package com.lambdaschool.coffeebean.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "suppliers")
public class Supplier
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long supplierId;

    @Column(length = 250, unique = true)
    private String supplierName;

    @Column(length = 250, unique = true)
    private String supplierPhone;

    Date createdAt = new Date();

    Date updatedAt = new Date();

    // *** ManyToMany with product - supplierproduct - owner ***
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "supplierProduct",
            joinColumns = {@JoinColumn(name = "supplierId")},
            inverseJoinColumns = {@JoinColumn(name = "productId")})
    @JsonIgnoreProperties({"productReviews", "cartItems", "suppliers", "createdAt", "updatedAt"})
    private Set<Product> productsFromSupplier;

    public Supplier()
    {
    }

    public long getSupplierId()
    {
        return supplierId;
    }

    public void setSupplierId(long supplierId)
    {
        this.supplierId = supplierId;
    }

    public String getSupplierName()
    {
        return supplierName;
    }

    public void setSupplierName(String supplierName)
    {
        this.supplierName = supplierName;
    }

    public String getSupplierPhone()
    {
        return supplierPhone;
    }

    public void setSupplierPhone(String supplierPhone)
    {
        this.supplierPhone = supplierPhone;
    }

    public Set<Product> getProductsFromSupplier()
    {
        return productsFromSupplier;
    }

    public void setProductsFromSupplier(Set<Product> productsFromSupplier)
    {
        this.productsFromSupplier = productsFromSupplier;
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
}
