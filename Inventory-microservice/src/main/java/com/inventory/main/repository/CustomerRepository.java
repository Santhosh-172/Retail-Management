package com.inventory.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.main.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer>{

	Customer findByphoneNumber(String phoneNumber);

}
