package com.lambdaschool.coffeebean.controller;

import com.lambdaschool.coffeebean.model.Supplier;
import com.lambdaschool.coffeebean.service.SupplierService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Api(value = "Some value... by DKM", description = "Supplier Controller by DKM")
@RestController
@RequestMapping(path = "/suppliers", produces = MediaType.APPLICATION_JSON_VALUE)
public class SupplierController
{
    @Autowired
    SupplierService supplierService;

    @ApiOperation(value = "find all orders - DKM", response = Supplier.class)
    @GetMapping
    public ResponseEntity<?> findAllSuppliers()
    {
        List<Supplier> allSuppliers = supplierService.findAllSuppliers();
        return new ResponseEntity<>(allSuppliers, HttpStatus.OK);
    }

    @GetMapping("/{supplierId}")
    public ResponseEntity<?> getSupplierById(@PathVariable long supplierId)
    {
        Supplier foundSupplier = supplierService.findSupplierById(supplierId);
        return new ResponseEntity<>(foundSupplier, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addSupplier(@RequestBody Supplier newSupplier) throws URISyntaxException
    {
        newSupplier = supplierService.addSupplier(newSupplier);

        HttpHeaders responseHeaders = new HttpHeaders();

        URI newSupplierURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{restaurantid}")
                .buildAndExpand(newSupplier.getSupplierId()).toUri();

        responseHeaders.setLocation(newSupplierURI);

        return new ResponseEntity<>(newSupplier, responseHeaders, HttpStatus.CREATED);
    }

    @PostMapping("/supplierid/{supplierId}/productid/{productId}")
    public ResponseEntity<?> addSupplierToProduct(@PathVariable long supplierId, @PathVariable long productId)
    {
        Supplier updatedSupplier = supplierService.addSupplierToProduct(supplierId, productId);
        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<?> updateSupplierBySupplierID(@RequestBody Supplier updatedSupplier)
    {
        updatedSupplier = supplierService.updateSupplierBySupplierID(updatedSupplier);

        return new ResponseEntity<>(updatedSupplier, HttpStatus.OK);
    }

    @DeleteMapping("/supplierid/{supplierId}/productid/{productId}")
    public ResponseEntity<?> deleteSupplierFromProduct(@PathVariable long supplierId, @PathVariable long productId)
    {
        Supplier foundSupplier = supplierService.deleteSupplierFromProduct(supplierId, productId);

        return new ResponseEntity<>(foundSupplier, HttpStatus.OK);
    }

    @DeleteMapping("/{supplierId}")
    public ResponseEntity<?> deleteSupplierById(@PathVariable long supplierId)
    {
        Supplier deletedSupplier = supplierService.deleteSupplierById(supplierId);

        return new ResponseEntity<>(deletedSupplier, HttpStatus.OK);
    }

}
