package com.charter.charterassignment.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charter.charterassignment.entity.CustomerTransaction;

@Repository
public interface CustomerTransactionRepository extends JpaRepository<CustomerTransaction, Long>{
	
	List<CustomerTransaction> findByCustomerIdAndTransactionDateBetween(Long customerId,LocalDate startDate, LocalDate endDate);
	List<CustomerTransaction> findBycustomerId(Long customerId);
}
