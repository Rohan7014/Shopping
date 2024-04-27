package com.example.shoppingverse.repository;

import com.example.shoppingverse.Enum.ProductCategory;
import com.example.shoppingverse.dto.response.ProductResponseDto;
import com.example.shoppingverse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("select p from Product p where p.price >= :price and p.category = :category")
    public List<Product> getProductByCategoryAndPriceGreaterThan(int price,ProductCategory category);
}
