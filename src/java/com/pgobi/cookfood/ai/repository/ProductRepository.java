package com.pgobi.cookfood.ai.repository;

import com.pgobi.cookfood.ai.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAll();

    @Modifying
    @Transactional
    @Query("UPDATE Product p SET p.status = :status WHERE p.id = :id")
    Integer updateProductStatus(@Param("status") String status, @Param("id") int id);

    List<Product> findProductsByCategoryId(Integer id);

    Product getProductById( Integer id);

}
