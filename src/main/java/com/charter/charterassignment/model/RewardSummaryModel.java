package com.charter.charterassignment.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RewardSummaryModel {
	
    private Long customerId;
    @JsonProperty("customerTransactions")	
    private List<CustomerTransactionModel> customerTransactionModelList;
    private double totalTransactionAmount;
    private double totalRewardPoints;
}
