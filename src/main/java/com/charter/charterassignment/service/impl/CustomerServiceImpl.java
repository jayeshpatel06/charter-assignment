package com.charter.charterassignment.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.charter.charterassignment.entity.Customer;
import com.charter.charterassignment.repository.CustomerRepository;
import com.charter.charterassignment.service.CustomerService;

@Service
public class CustomerServiceImpl implements CustomerService {

	private static final Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

	@Autowired
	private CustomerRepository customerRepository;

	@Override
	public Customer getCustomerById(long customerId) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		return customer;
	}

	@Override
	public Customer createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomer(long customerId, Customer customerDetails) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		customer.setEmailId(customerDetails.getEmailId());
		customer.setLastName(customerDetails.getLastName());
		customer.setFirstName(customerDetails.getFirstName());
		final Customer updatedCustomer = customerRepository.save(customer);
		return updatedCustomer;
	}

	@Override
	public boolean deleteCustomer(long customerId) {
		Customer customer = customerRepository.findById(customerId).orElse(null);
		customerRepository.delete(customer);
		return true;
	}

	@Override
	public List<Customer> getAllCustomer() {
		return customerRepository.findAll();
	}

}
