package com.lambdaschool.coffeebean.service;

import com.lambdaschool.coffeebean.model.Product;

import java.util.List;

public interface ProductService
{
    List<Product> findAllProducts();

    Product findProductByProductId(long productId);

    Product addNewProduct(Product newProduct);

    Product updateProductById(Product updatedP, Product foundProduct);

    Product deleteProductById(long productId);
}
