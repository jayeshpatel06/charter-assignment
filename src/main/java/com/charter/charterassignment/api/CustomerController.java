package com.charter.charterassignment.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charter.charterassignment.entity.Customer;
import com.charter.charterassignment.exception.ResourceNotFoundException;
import com.charter.charterassignment.model.CustomerModel;
import com.charter.charterassignment.service.CustomerService;

@RestController
@RequestMapping("/api/v1")
public class CustomerController {

	private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@Autowired
	private CustomerService customerService;

	@Autowired
	private ModelMapper modelMapper;

	@GetMapping("/customers")
	public List<CustomerModel> getAllCustomers() throws ResourceNotFoundException {
		List<Customer> customerList = customerService.getAllCustomer();
		List<CustomerModel> customerModelList = new ArrayList<>();
		if (!customerList.isEmpty()) {
			for (Customer customer : customerList) {
				customerModelList.add(modelMapper.map(customer, CustomerModel.class));
			}
			return customerModelList;
		}
		throw new ResourceNotFoundException("Customer not found");
	}

	@GetMapping("/customer/{customerId}")
	public ResponseEntity<CustomerModel> getCustomerById(@PathVariable(value = "customerId") Long customerId)
			throws ResourceNotFoundException {
		Customer customer = customerService.getCustomerById(customerId);
		if (customer != null) {
			return ResponseEntity.ok().body(modelMapper.map(customer, CustomerModel.class));
		}
		throw new ResourceNotFoundException("Customer not found");
	}

	@PostMapping("/customer")
	public ResponseEntity<CustomerModel> createCustomer(@Valid @RequestBody CustomerModel customerModel) {
		try {
			Customer customer = modelMapper.map(customerModel, Customer.class);
			customer = customerService.createCustomer(customer);
			customerModel= modelMapper.map(customer, CustomerModel.class);
			return new ResponseEntity<CustomerModel>(customerModel, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<CustomerModel>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/customer/{customerId}")
	public ResponseEntity<CustomerModel> updateCustomer(@PathVariable(value = "customerId") Long customerId,
			@Valid @RequestBody Customer customerDetails) throws ResourceNotFoundException {
		try {
			Customer customer = customerService.getCustomerById(customerId);
			customer.setEmailId(customerDetails.getEmailId());
			customer.setLastName(customerDetails.getLastName());
			customer.setFirstName(customerDetails.getFirstName());
			final Customer updatedCustomer = customerService.createCustomer(customer);
			return ResponseEntity.ok().body(modelMapper.map(updatedCustomer, CustomerModel.class));
		}catch(Exception e) {
			return new ResponseEntity<CustomerModel>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/customer/{customerId}")
	public Map<String, Boolean> deleteCustomer(@PathVariable(value = "customerId") Long customerId)
			throws ResourceNotFoundException {
		Map<String, Boolean> response = new HashMap<>();
		try {
			Customer customer = customerService.getCustomerById(customerId);
			if(customer!=null) {
				customerService.deleteCustomer(customerId);
				response.put("deleted", Boolean.TRUE);
				return response;
			}
			throw new ResourceNotFoundException("Customer not found");
		}catch(Exception e) {
			response.put("deleted", Boolean.FALSE);
			return response;
		}
	}

}
