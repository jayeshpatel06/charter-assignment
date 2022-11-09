package com.charter.charterassignment.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charter.charterassignment.entity.CustomerTransaction;
import com.charter.charterassignment.repository.CustomerTransactionRepository;
import com.charter.charterassignment.service.CustomerTransactionService;
import com.charter.charterassignment.util.RewardUtil;

@Service
public class CustomerTransactionServiceImpl implements CustomerTransactionService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerTransactionService.class);

	@Autowired
	private CustomerTransactionRepository customerTransactionRepository;
	
	@Autowired
	private RewardUtil rewardUtil;

	@Override
	public CustomerTransaction getCustomerTransactionById(long customerTransactionId) {
		CustomerTransaction customerTransaction = customerTransactionRepository.findById(customerTransactionId).orElse(null);
		return customerTransaction;
	}
	
	@Override
	public List<CustomerTransaction> getCustomerTransactionByCustomerId(long customerId) {
		return customerTransactionRepository.findBycustomerId(customerId);
	}

	@Override
	public CustomerTransaction createCustomerTransaction(CustomerTransaction customerTransaction) {
		logger.info("Transaction Date: {} ",new java.util.Date());
		customerTransaction.setTransactionDate(LocalDate.now());
		if(customerTransaction.getTransactionAmount()!=null) {
			customerTransaction.setRewardPoints(rewardUtil.calulateReward(customerTransaction.getTransactionAmount()));
		}
		return customerTransactionRepository.save(customerTransaction);
	}

	@Override
	public List<CustomerTransaction> getAllCustomerTransaction() {
		return customerTransactionRepository.findAll();
	}
	
	@Override
	public List<CustomerTransaction> getRewardSummaryByCustomerId(long customerId,LocalDate startDate, LocalDate endDate) {
		return customerTransactionRepository.findByCustomerIdAndTransactionDateBetween(customerId,startDate,endDate);
	}

}
