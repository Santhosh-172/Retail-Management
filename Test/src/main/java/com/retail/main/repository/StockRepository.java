package com.retail.main.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.retail.main.model.Stock;

public interface StockRepository extends JpaRepository<Stock, Integer> {

	Optional<Stock> findByProductId(int productId);
}
