package com.charter.charterassignment.api;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.charter.charterassignment.CharterAssignmentApplication;
import com.charter.charterassignment.entity.CustomerTransaction;



@SpringBootTest(classes = CharterAssignmentApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerTransactionControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	private String getRootUrl() {
		return "http://localhost:" + port;
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void testGetAllCustomerTransactions() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/customer/transaction",
				HttpMethod.GET, entity, String.class);
		assertNotNull(response.getBody());
	}

	@Test
	public void testGetCustomerTransactionById() {
		CustomerTransaction customerTransaction = restTemplate.getForObject(getRootUrl() + "/customer/transaction/1", CustomerTransaction.class);
		assertNotNull(customerTransaction);
	}

	@Test
	public void testCreateCustomerTransaction() {
		CustomerTransaction customerTransaction = new CustomerTransaction();
		customerTransaction.setCustomerId(1l);
		customerTransaction.setTransactionAmount(120d);
		customerTransaction.setRewardPoints(90d);

		ResponseEntity<CustomerTransaction> postResponse = restTemplate.postForEntity(getRootUrl() + "/customer/transaction", customerTransaction, CustomerTransaction.class);
		assertNotNull(postResponse);
		assertNotNull(postResponse.getBody());
	}

}
