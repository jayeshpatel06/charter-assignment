package com.charter.charterassignment.service;

import java.util.List;

import com.charter.charterassignment.entity.Customer;

public interface CustomerService {
	Customer getCustomerById(long customerId);
	Customer createCustomer(Customer customer);
	Customer updateCustomer(long customerId,Customer customerDetails);
	boolean deleteCustomer(long customerId);
	List<Customer> getAllCustomer();
}
