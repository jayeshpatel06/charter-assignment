package com.charter.charterassignment.service;

import java.time.LocalDate;
import java.util.List;

import com.charter.charterassignment.entity.CustomerTransaction;

public interface CustomerTransactionService {
	CustomerTransaction getCustomerTransactionById(long customerTransactionId);
	CustomerTransaction createCustomerTransaction(CustomerTransaction customerTransaction);
	List<CustomerTransaction> getAllCustomerTransaction();
	List<CustomerTransaction> getRewardSummaryByCustomerId(long customerId,LocalDate startDate, LocalDate endDate);
}
