package com.billing.main.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.billing.main.model.Billing;

public interface BillingRepository extends JpaRepository<Billing, Long>{

}
