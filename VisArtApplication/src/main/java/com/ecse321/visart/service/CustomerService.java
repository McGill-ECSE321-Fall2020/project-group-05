package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.User;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.CustomerRepository;

public class CustomerService {

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    EntityRepository entityRepo;

    @Transactional
    public Customer createCustomer(String aIdCode, String aEmailAddress, String aDisplayname,
        String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {

      if (aIdCode == null || aIdCode == "") {
        throw new IllegalArgumentException("Customer id code cannot be empty!");
      }

      return customerRepo.createCustomer(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword,
          aProfilePicLink, aProfileDescription);

    }

    @Transactional
    public Customer getCustomer(String aIdCode) {
      return customerRepo.getCustomer(aIdCode);
    }

    @Transactional
    public List<Customer> getAllUsers() {
      return entityRepo.getAllEntities(Customer.class);
    }

  }



