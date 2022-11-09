package com.charter.charterassignment.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "customer_transaction")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTransaction {
	
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="customer_id")
    private Long customerId;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "transaction_amount")
    private Double transactionAmount;
    
    @Column(name = "reward_points")
    private Double rewardPoints;
}
