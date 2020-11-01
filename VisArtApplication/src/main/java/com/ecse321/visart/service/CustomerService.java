package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.User;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.CustomerRepository;

@Service
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
      else if  (aEmailAddress == null) {
        throw new IllegalArgumentException("Email address cannot be empty!");
      }
      else if (aDisplayname == null) {
        throw new IllegalArgumentException("Display name cannot be empty!");
      }
      else if (aUsername == null) {
        throw new IllegalArgumentException("User name cannot be empty!");
      }
      else if (aPassword == null) {
        throw new IllegalArgumentException("Password cannot be empty!");
      }
      else if(aPassword.length()<8 || aPassword.length()>255) {
        throw new IllegalArgumentException("Password must have atleast 8 characters and less than 255 ");
      }
      else if( aProfilePicLink == null) {
        throw new IllegalArgumentException("Profile picture link cannot be empty!");
      }
      else if (aProfileDescription == null) {
        throw new IllegalArgumentException("Profile description cannot be empty!");
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



