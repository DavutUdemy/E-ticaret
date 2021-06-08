package com.hazir.Hazirlaniyor.dataAccess.abstracts;

import com.hazir.Hazirlaniyor.entity.concretes.Product;
import com.hazir.Hazirlaniyor.entity.concretes.ProductCategory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.Option;

@Repository
public interface ProductDao extends JpaRepository<Product,Long> {
    @Query("SELECT s FROM Product s WHERE s.productCategory=?1")
    List<Product> findProductByCategory(ProductCategory productCategory);
    @Query("SELECT  s from Product s where s.unitsInStock<?1")
    Boolean findProductByUnitStock(Integer unitsInStock);
    @Transactional
    @Modifying
    @Query("UPDATE Product a " +
            "SET a.unitsInStock = a.unitsInStock-?1 WHERE a.productName = ?2")
    int unitsInStock(Integer quantity,String productName);
    //JPA
    @Query("SELECT s FROM Product s WHERE s.productName=?1")
    Optional<Product> existsProductByProductName(String productName);
   @Query("DELETE  from Product c where c.productName=?1")
	 void deleteByProductName(String productName);
}
