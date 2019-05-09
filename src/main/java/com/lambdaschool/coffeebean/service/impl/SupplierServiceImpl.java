package com.lambdaschool.coffeebean.service.impl;

import com.lambdaschool.coffeebean.model.Supplier;
import com.lambdaschool.coffeebean.repository.SupplierRepository;
import com.lambdaschool.coffeebean.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Transactional
@Service
public class SupplierServiceImpl implements SupplierService
{
    @Autowired
    SupplierRepository supplierrepos;


    @Override
    public List<Supplier> findAllSuppliers()
    {
        return supplierrepos.findAll();
    }

    @Override
    public Supplier findSupplierById(long supplierId)
    {
        return supplierrepos.findById(supplierId)
                .orElseThrow(()-> new EntityNotFoundException("SupplierId: " + Long.toString(supplierId) + " not found."));
    }

    @Transactional
    @Override
    public Supplier addSupplier(Supplier newSupplier)
    {
        newSupplier.setSupplierId(null);
        return supplierrepos.save(newSupplier);
    }

    @Transactional
    @Override
    public Supplier updateSupplierBySupplierID(Supplier updatedSupplier)
    {
        Supplier foundSupplier = supplierrepos.findById(updatedSupplier.getSupplierId())
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(updatedSupplier.getSupplierId())));

        if (updatedSupplier.getSupplierName() == null) updatedSupplier.setSupplierName(foundSupplier.getSupplierName());
        if (updatedSupplier.getSupplierPhone() == null) updatedSupplier.setSupplierPhone(foundSupplier.getSupplierPhone());
        if (updatedSupplier.getProductsFromSupplier().isEmpty()) updatedSupplier.setProductsFromSupplier(foundSupplier.getProductsFromSupplier());

        updatedSupplier.setCreatedAt(foundSupplier.getCreatedAt());
        updatedSupplier.setUpdatedAt(new Date());
        return supplierrepos.save(updatedSupplier);
    }

    @Transactional
    @Override
    public Supplier deleteSupplierById(long supplierId)
    {
        Supplier foundSupplier = supplierrepos.findById(supplierId)
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(supplierId)));

        supplierrepos.deleteById(supplierId);

        return foundSupplier;
    }

    @Transactional
    @Override
    public Supplier addSupplierToProduct(long supplierId, long productId)
    {
        supplierrepos.addSupplierToProduct(supplierId, productId);
        return supplierrepos.findById(supplierId)
                .orElseThrow(()-> new EntityNotFoundException(Long.toString(supplierId)));
    }

    @Transactional
    @Override
    public Supplier deleteSupplierFromProduct(long supplierId, long productId)
    {
        Supplier foundSupplier = supplierrepos.findById(supplierId)
                .orElseThrow(()-> new EntityNotFoundException("SupplerId: " + Long.toString(supplierId)));

        supplierrepos.deleteSupplierFromProduct(supplierId, productId);
        return foundSupplier;
    }
}
