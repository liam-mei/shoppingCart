package com.lambdaschool.coffeebean.service.impl;

import com.lambdaschool.coffeebean.model.Product;
import com.lambdaschool.coffeebean.repository.ProductRepository;
import com.lambdaschool.coffeebean.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class ProductServiceImpl implements ProductService
{
    @Autowired
    ProductRepository productRepos;

    @Override
    public List<Product> findAllProducts()
    {
        return productRepos.findAll();
    }

    @Override
    public Product findProductByProductId(long productId)
    {
        return productRepos.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(productId)));
    }

    @Transactional
    @Override
    public Product addNewProduct(Product newProduct)
    {
        newProduct.setProductId(null);
        return productRepos.save(newProduct);
    }

    @Transactional
    @Override
    public Product updateProductById(Product updatedP, Product foundProduct)
    {
        if (updatedP.getProductName() == null) updatedP.setProductName(foundProduct.getProductName());
        if (updatedP.getDescription() == null) updatedP.setDescription(foundProduct.getDescription());
        if (updatedP.getSuppliers() == null) updatedP.setSuppliers(foundProduct.getSuppliers());
        if (updatedP.getInventory() == null) updatedP.setInventory(foundProduct.getInventory());
        if (updatedP.getImage() == null) updatedP.setImage(foundProduct.getImage());
        if (updatedP.getPrice() == null) updatedP.setPrice(foundProduct.getPrice());

        updatedP.setUpdatedAt(new Date());
        return productRepos.save(updatedP);
    }

    @Transactional
    @Override
    public Product deleteProductById(long productId)
    {
        Product foundProduct = productRepos.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(productId)));
        productRepos.deleteById(productId);
        return foundProduct;
    }
}
