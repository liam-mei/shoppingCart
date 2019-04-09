package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.repository.ProductRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{productid}")
    public Product findProductByProductId(@PathVariable long productid)
    {
        return productrepos.findById(productid).get();
    }

    @PostMapping("")
    public Product addToProducts(@RequestBody Product newProduct)
    {
        return productrepos.save(newProduct);
    }

    @PutMapping("/{productid}")
    public Object modifyProductById(@RequestBody Product updatedP, @PathVariable long productid)
    {
        Optional<Product> foundProduct = productrepos.findById(productid);
        if (foundProduct.isPresent())
        {
//            if (updatedP.getProductorders() == null) updatedP.setProductorders(foundProduct.get().getProductorders());
//            if (updatedP.getProductusers() == null) updatedP.setProductusers(foundProduct.get().getProductusers());
            if (updatedP.getProductName() == null) updatedP.setProductName(foundProduct.get().getProductName());
            if (updatedP.getDescription() == null) updatedP.setDescription(foundProduct.get().getDescription());
//            if (updatedP.getExpiration() == null) updatedP.setExpiration(foundProduct.get().getExpiration());
            if (updatedP.getSuppliers() == null) updatedP.setSuppliers(foundProduct.get().getSuppliers());
            if (updatedP.getInventory() == null) updatedP.setInventory(foundProduct.get().getInventory());
            if (updatedP.getImage() == null) updatedP.setImage(foundProduct.get().getImage());
            if (updatedP.getPrice() == null) updatedP.setPrice(foundProduct.get().getPrice());

            updatedP.setProductId(productid);
            return productrepos.save(updatedP);
        } else
        {
            return "Product with id: " + productid + " could not be found.";
        }
    }

    @DeleteMapping("/{productid}")
    public Object deleteProductById(@PathVariable long productid)
    {
        Optional<Product> foundProduct = productrepos.findById(productid);
        if (foundProduct.isPresent())
        {
            productrepos.deleteById(productid);
            return foundProduct.get();
        } else
        {
            return "Product with id: " + productid + " could not be found.";
        }
    }

}