package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Supplier;
import com.lambdaschool.coffeebean.repository.SupplierRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Api(value = "Some value... by DKM", description = "Supplier Controller by DKM")
@RestController
@RequestMapping(path = "/suppliers", produces = MediaType.APPLICATION_JSON_VALUE)
public class SupplierController
{
    @Autowired
    SupplierRepository suppplierrepos;

    @ApiOperation(value = "find all orders - DKM", response = Supplier.class)
    @GetMapping("")
    public List<Supplier> findAllOrders()
    {
        return suppplierrepos.findAll();
    }

    @GetMapping("/{supplierId}")
    public Supplier getSupplierById(@PathVariable long supplierId)
    {
        return suppplierrepos.findById(supplierId).get();
    }

    @PostMapping("")
    public Supplier addSupplier(@RequestBody Supplier newSupplier)
    {
        return suppplierrepos.save(newSupplier);
    }

    @PostMapping("/supplierid/{supplierId}/productid/{productId}")
    public Supplier addSupplierToProduct(@PathVariable long supplierId, @PathVariable long productId)
    {
        suppplierrepos.addSupplierToProduct(supplierId, productId);
        return suppplierrepos.findById(supplierId).get();
    }

    @PutMapping("/{supplierId}")
    public Object updateSupplierBySupplierID(@PathVariable long supplierId, @RequestBody Supplier updatedSupplier)
    {
        Optional<Supplier> foundSupplier = suppplierrepos.findById(supplierId);

        if (foundSupplier.isPresent())
        {
            updatedSupplier.setSupplierId(supplierId);
            if (updatedSupplier.getSupplierName() == null)
                updatedSupplier.setSupplierName(foundSupplier.get().getSupplierName());
            if (updatedSupplier.getSupplierPhone() == null)
                updatedSupplier.setSupplierPhone(foundSupplier.get().getSupplierPhone());
            if (updatedSupplier.getProductsFromSupplier().isEmpty())
                updatedSupplier.setProductsFromSupplier(foundSupplier.get().getProductsFromSupplier());
            return suppplierrepos.save(updatedSupplier);
        } else
        {
            return "Supplier with id: " + supplierId + " is not found.";
        }
    }

    @DeleteMapping("/supplierid/{supplierId}/productid/{productId}")
    public Supplier deleteSupplierFromProduct(@PathVariable long supplierId, @PathVariable long productId)
    {
        Supplier foundSupplier = suppplierrepos.findById(supplierId).get();
        suppplierrepos.deleteSupplierFromProduct(supplierId, productId);
        return foundSupplier;
    }

    @DeleteMapping("/{supplierId}")
    public Object deleteSupplierById(@PathVariable long supplierId)
    {
        Optional<Supplier> foundSupplier = suppplierrepos.findById(supplierId);
        if (foundSupplier.isPresent())
        {
            suppplierrepos.deleteById(supplierId);
            return foundSupplier.get();
        } else
        {
            return "Supplier with id: " + supplierId + " is not found.";
        }
    }

}
