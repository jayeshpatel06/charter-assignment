package com.charter.charterassignment.model;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTransactionModel {
	
    private Long id;
    private Long customerId;
    private LocalDate transactionDate;
    private double transactionAmount;
    private int rewardPoints;
}
