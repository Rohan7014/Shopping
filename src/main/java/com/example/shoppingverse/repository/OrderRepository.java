package com.example.shoppingverse.repository;

import com.example.shoppingverse.model.Order_Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order_Entity,Integer> {
}
