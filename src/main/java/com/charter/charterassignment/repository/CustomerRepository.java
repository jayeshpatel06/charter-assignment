package com.charter.charterassignment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.charter.charterassignment.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>{

}
