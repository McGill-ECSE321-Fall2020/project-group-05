package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;

@Repository
public class CustomerRepository {
	
	@Autowired
	EntityManager entityManager;
	
	@Transactional
	public Customer createCustomer(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername, String aPassword) {
		User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword);
		Customer customer = new Customer(aIdCode, usr);
		entityManager.persist(customer);
		return customer;
	}
	
	@Transactional
	public Customer getCustomer(String aIdCode) {
		return entityManager.find(Customer.class, aIdCode);
	}

}
