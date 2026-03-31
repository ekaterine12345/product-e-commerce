package com.example.product_api.repository;

import com.example.product_api.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(String userId);
}
