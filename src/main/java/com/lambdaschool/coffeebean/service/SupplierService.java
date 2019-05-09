package com.lambdaschool.coffeebean.service;

import com.lambdaschool.coffeebean.model.Supplier;

import java.util.List;

public interface SupplierService
{
    List<Supplier> findAllSuppliers();

    Supplier findSupplierById(long supplierId);

    Supplier addSupplier( Supplier newSupplier);

    Supplier updateSupplierBySupplierID(Supplier updatedSupplier);

    Supplier deleteSupplierById(long supplierId);

    Supplier addSupplierToProduct(long supplierId, long productId);

    Supplier deleteSupplierFromProduct(long supplierId, long productId);
}
