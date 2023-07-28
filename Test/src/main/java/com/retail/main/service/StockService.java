package com.retail.main.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.retail.main.model.Product;
import com.retail.main.model.Stock;
import com.retail.main.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	StockRepository stockRepository;

	public Stock insertOrUpdate(Stock stock) {
		int productId = stock.getProduct().getId();
		System.out.println(productId);
		Optional<Stock> existingStock = stockRepository.findByProductId(productId);

		if (existingStock.isPresent()) {
			// If stock entry already exists, update the quantity by adding the new quantity
			int newQuantity = existingStock.get().getQuantity() + stock.getQuantity();
			
			if (newQuantity < 0) {
				throw new IllegalArgumentException("Insufficient Stock for product:" + stock.getProduct().getTitle());
			}
			existingStock.get().setQuantity(newQuantity);
			return stockRepository.save(existingStock.get());
		} else {
			// If stock entry does not exist, create a new stock entry
			stock.setDateOfSupply(LocalDate.now());
			return stockRepository.save(stock);
		}
	}
	
	public Stock update(Stock stock) {
		return stockRepository.save(stock);
	}

	public List<Stock> getAll() {

		return stockRepository.findAll();
	}

	public Stock getById(int id) {
		// TODO Auto-generated method stub
		Optional<Stock> optional = stockRepository.findById(id);
		if (!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	public void updateStockQuantity(Product product, int quantityChange) {
		List<Stock> stocks = getAll();
		if (stocks != null && !stocks.isEmpty()) {
			Stock stock = stocks.get(0); // Assuming there's only one stock entry per product for simplicity
			int currentQuantity = stock.getQuantity();
			int newQuantity = currentQuantity + quantityChange;
			stock.setQuantity(newQuantity);
			stockRepository.save(stock);
		} else {

		}
	}

}
