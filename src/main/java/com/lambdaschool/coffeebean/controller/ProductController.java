package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Api(value = "Some value... by DKM", description = "Product Controller by DKM")
@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController
{
    @Autowired
    ProductRepository productrepos;

    @ApiOperation(value = "find all orders - DKM", response = Product.class)
    @GetMapping("")
    public List<Product> findAllOrders()
    {
        return productrepos.findAll();
    }

    @GetMapping("/{productId}")
    public Product findProductByProductId(@PathVariable long productId)
    {
        return productrepos.findById(productId).get();
    }

    @PostMapping("")
    public Product addToProducts(@RequestBody Product newProduct)
    {
        newProduct.setProductId(null);
        return productrepos.save(newProduct);
    }

    @PutMapping("/{productId}")
    public Object modifyProductById(@RequestBody Product updatedP, @PathVariable long productId)
    {
        Optional<Product> foundProduct = productrepos.findById(productId);
        if (foundProduct.isPresent())
        {
            if (updatedP.getProductName() == null) updatedP.setProductName(foundProduct.get().getProductName());
            if (updatedP.getDescription() == null) updatedP.setDescription(foundProduct.get().getDescription());
//            if (updatedP.getExpiration() == null) updatedP.setExpiration(foundProduct.get().getExpiration());
            if (updatedP.getSuppliers() == null) updatedP.setSuppliers(foundProduct.get().getSuppliers());
            if (updatedP.getInventory() == null) updatedP.setInventory(foundProduct.get().getInventory());
            if (updatedP.getImage() == null) updatedP.setImage(foundProduct.get().getImage());
            if (updatedP.getPrice() == null) updatedP.setPrice(foundProduct.get().getPrice());

            updatedP.setUpdatedAt(new Date());
            updatedP.setProductId(productId);
            return productrepos.save(updatedP);
        } else
        {
            return "Product with id: " + productId + " could not be found.";
        }
    }

    @DeleteMapping("/{productId}")
    public Object deleteProductById(@PathVariable long productId)
    {
        Optional<Product> foundProduct = productrepos.findById(productId);
        if (foundProduct.isPresent())
        {
            productrepos.deleteById(productId);
            return foundProduct.get();
        } else
        {
            return "Product with id: " + productId + " could not be found.";
        }
    }

}