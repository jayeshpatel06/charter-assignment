package com.charter.charterassignment.api;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.charter.charterassignment.entity.CustomerTransaction;
import com.charter.charterassignment.exception.ResourceNotFoundException;
import com.charter.charterassignment.model.CustomerTransactionModel;
import com.charter.charterassignment.model.RewardSummaryModel;
import com.charter.charterassignment.service.CustomerTransactionService;

@RestController
@RequestMapping("/api/v1")
public class CustomerTransactionController {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomerTransactionController.class);
	
    @Autowired
    private CustomerTransactionService customerTransactionService;
    
	@Value("${defaultNumberofMonths}")
	private Long defaultNumberofMonths;

    @Autowired
    private ModelMapper modelMapper;
    
    @GetMapping("/customer/transaction")
    public List<CustomerTransactionModel> getAllCustomerTransactions() throws ResourceNotFoundException{
    	logger.info("get All CustomerTransactions");
	    List <CustomerTransaction> customerTransasctionList= customerTransactionService.getAllCustomerTransaction();
	    List < CustomerTransactionModel > customerTransactionModelList = new ArrayList<>();
	    if (!customerTransasctionList.isEmpty()) {
	    	for (CustomerTransaction customerTransaction: customerTransasctionList ) {
	          customerTransactionModelList.add(modelMapper.map(customerTransaction, CustomerTransactionModel.class));
	        }
	    	return customerTransactionModelList;
	    }
	    throw new ResourceNotFoundException("Customer Transaction not found");
    }

    @GetMapping("/customer/{customerId}/transaction")
    public List<CustomerTransactionModel> getCustomerTransactionByCustomerId(@PathVariable(value = "customerId") Long customerId)
    throws ResourceNotFoundException {
    	logger.info("get CustomerTransactions by CustomerId : {} ",customerId);
        List<CustomerTransaction> customerTransasctionList = customerTransactionService.getCustomerTransactionByCustomerId(customerId);
        List < CustomerTransactionModel > customerTransactionModelList = new ArrayList<>();
        if (!customerTransasctionList.isEmpty()) {
	    	for (CustomerTransaction customerTransaction: customerTransasctionList ) {
	          customerTransactionModelList.add(modelMapper.map(customerTransaction, CustomerTransactionModel.class));
	        }
	    	return customerTransactionModelList;
	    }
	    throw new ResourceNotFoundException("Customer Transaction not found");
    }
    
    @GetMapping("/customer/transaction/{customerTransactionId}")
    public ResponseEntity<CustomerTransactionModel> getCustomerTransactionById(@PathVariable(value = "customerTransactionId") Long customerTransactionId)
    throws ResourceNotFoundException {
    	logger.info("get CustomerTransactions by CustomerId : {} ",customerTransactionId);
        CustomerTransaction customerTransaction = customerTransactionService.getCustomerTransactionById(customerTransactionId);
        if(customerTransaction!=null) {
        	return ResponseEntity.ok().body(modelMapper.map(customerTransaction, CustomerTransactionModel.class));
        }
        throw new ResourceNotFoundException("Customer Transaction not found");
    }

    @PostMapping("/customer/transaction")
    public ResponseEntity<CustomerTransactionModel> createCustomerTransaction(@Valid @RequestBody CustomerTransactionModel customerTransactionModel) {
    	try {
	    	CustomerTransaction customerTransaction = modelMapper.map(customerTransactionModel, CustomerTransaction.class);
	        customerTransaction = customerTransactionService.createCustomerTransaction(customerTransaction);
	        customerTransactionModel =  modelMapper.map(customerTransaction, CustomerTransactionModel.class);
	        return new ResponseEntity<CustomerTransactionModel>(customerTransactionModel, HttpStatus.OK);
	    }catch(Exception e) {
	    	logger.error("Create Customer Transaction Exception : {} ",e.getMessage());
			return new ResponseEntity<CustomerTransactionModel>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
    @GetMapping("/customer/{customerId}/reward")
    public ResponseEntity<CustomerTransactionModel> getCustomerRewardById(@PathVariable(value = "customerId") Long customerTransactionId)
    throws ResourceNotFoundException {
	     CustomerTransaction customerTransaction = customerTransactionService.getCustomerTransactionById(customerTransactionId);
	        if(customerTransaction!=null) {
	        	return ResponseEntity.ok().body(modelMapper.map(customerTransaction, CustomerTransactionModel.class));
	        }else {
	        	throw new ResourceNotFoundException("Customer Transaction not found");
	        }
    }
    
    @GetMapping("/customer/{customerId}/rewardsummary/{months}")
    public ResponseEntity<RewardSummaryModel> getRewardsSummaryByCustomerId(@PathVariable(value = "customerId") Long customerTransactionId,@PathVariable(value = "months") Long numberOfMonths)
    throws ResourceNotFoundException {
    		if(numberOfMonths==null) {
    			numberOfMonths=defaultNumberofMonths;
    		}
	        List<CustomerTransaction> customerTransactionList = customerTransactionService.getRewardSummaryByCustomerId(customerTransactionId, LocalDate.now().minusMonths(numberOfMonths),LocalDate.now());
	        List < CustomerTransactionModel > customerTransactionModelList = new ArrayList<>();
	        Double totalRewardPoints=0d, totalTransactionAmount=0d;
	        if (!customerTransactionList.isEmpty()) {
	            for (CustomerTransaction customerTransaction: customerTransactionList ) {
	            	customerTransactionModelList.add(modelMapper.map(customerTransaction, CustomerTransactionModel.class));
	            	totalRewardPoints=totalRewardPoints+customerTransaction.getRewardPoints();
	            	totalTransactionAmount=totalTransactionAmount+customerTransaction.getTransactionAmount();
	            }
	         }else {
	        	 throw new ResourceNotFoundException("Customer Transaction Reward not found");
	         }
	        RewardSummaryModel rewardSummaryModel = new RewardSummaryModel();
	        rewardSummaryModel.setCustomerId(customerTransactionId);
	        rewardSummaryModel.setTotalRewardPoints(totalRewardPoints);
	        rewardSummaryModel.setTotalTransactionAmount(totalTransactionAmount);
	        rewardSummaryModel.setCustomerTransactionModelList(customerTransactionModelList);
	        return new ResponseEntity<RewardSummaryModel>(rewardSummaryModel, HttpStatus.OK);    	
    }

}
