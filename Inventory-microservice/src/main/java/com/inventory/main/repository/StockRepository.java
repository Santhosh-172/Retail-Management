package com.inventory.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.main.model.Product;
import com.inventory.main.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {

	Optional<Stock> findByProductId(int productId);

	Stock findByProduct(Product product);
}
