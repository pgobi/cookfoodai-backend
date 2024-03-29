package com.pgobi.cookfood.ai.repository;

import com.pgobi.cookfood.ai.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAll();
    List<Order> findOrderByEmail( String email);

}
