package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "Some value... by DKM", description = "Product Controller by DKM")
@RestController
@RequestMapping(path = "/products", produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController
{
    @Autowired
    ProductService productService;

    @ApiOperation(value = "find all orders - DKM", response = Product.class)
    @GetMapping("")
    public ResponseEntity<?> findAllProducts()
    {
        List<Product> allProducts = productService.findAllProducts();
        return new ResponseEntity<>(allProducts, HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> findProductByProductId(@PathVariable long productId)
    {
        Product foundProduct = productService.findProductByProductId(productId);
        return new ResponseEntity<>(foundProduct, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addNewProduct(@RequestBody Product newProduct)
    {
        Product savedProduct = productService.addNewProduct(newProduct);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateProductById(@RequestBody Product updatedP)
    {
        Product foundProduct = productService.findProductByProductId(updatedP.getProductId());
        Product savedProduct = productService.updateProductById(updatedP, foundProduct);
        return new ResponseEntity<>(savedProduct, HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProductById(@PathVariable long productId)
    {
        Product deletedProduct = productService.deleteProductById(productId);
        return new ResponseEntity<>(deletedProduct, HttpStatus.OK);
    }

}