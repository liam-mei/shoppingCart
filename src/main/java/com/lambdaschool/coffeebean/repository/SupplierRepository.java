package com.lambdaschool.coffeebean.repository;

import com.lambdaschool.coffeebean.model.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface SupplierRepository extends JpaRepository<Supplier, Long>
{
    @Transactional
    @Modifying
    @Query(value = "INSERT INTO supplier_product (supplier_id, product_id) VALUES (:supplierId, :productId)", nativeQuery = true)
    void addSupplierToProduct(long supplierId, long productId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM supplier_product WHERE supplier_id=:supplierId AND product_id=:productId", nativeQuery = true)
    void deleteSupplierFromProduct(long supplierId, long productId);
}
