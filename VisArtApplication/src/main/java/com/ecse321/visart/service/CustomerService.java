package com.ecse321.visart.service;

import java.util.List;
import java.util.regex.Pattern;

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
      if  (aEmailAddress == null) {
        throw new IllegalArgumentException("Email address cannot be empty!");
      }
      if (aDisplayname == null) {
        throw new IllegalArgumentException("Display name cannot be empty!");
      }
      if(aDisplayname.length()<5 || aDisplayname.length()>25) {
        throw new IllegalArgumentException("This Display Name is invalid, must be between 5 and 25 characters!");
      }
      List<User> l1 = entityRepo.findEntityByAttribute("displayname",User.class,aDisplayname);
      if (l1 != null && l1.size() > 0) {
          throw new IllegalArgumentException("This Display Name is already taken!");
      }
      
      if (aUsername == null) {
        throw new IllegalArgumentException("User name cannot be empty!");
      }
      if(aUsername.length()<5 || aUsername.length()>25 ) {
        throw new IllegalArgumentException("This User Name is invalid, must be between 5 and 25 characters!");
      }
      
      List<User> l2 = entityRepo.findEntityByAttribute("username",User.class,aUsername);
      if (l2 != null && l2.size() > 0) {
          throw new IllegalArgumentException("This Username is already taken!");
      }
      
      if (aPassword == null) {
        throw new IllegalArgumentException("Password cannot be empty!");
      }
      if(aPassword.length()<8 || aPassword.length()>40) {
        throw new IllegalArgumentException("Password must have atleast 8 characters and less than 40 ");
      }
      if( aProfilePicLink == null) {
        throw new IllegalArgumentException("Profile picture link cannot be empty!");
      }
      if (aProfileDescription == null) {
        throw new IllegalArgumentException("Profile description cannot be empty!");
      }
      if (aProfileDescription.length()>255) {
        throw new IllegalArgumentException("Profile description cannot exceed 255 characters!");
      }
      

      return customerRepo.createCustomer(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword,
          aProfilePicLink, aProfileDescription);

    }

    @Transactional
    public Customer getCustomer(String aIdCode) {
      return customerRepo.getCustomer(aIdCode);
    }
    
    @Transactional
    public Boolean deleteCustomer(String aIdCode) {
      if(aIdCode != null && !aIdCode.contentEquals("")) {
        return customerRepo.deleteCustomer(aIdCode);
      } else {
        return false;
      }
    }

    @Transactional
    public List<Customer> getAllUsers() {
      return entityRepo.getAllEntities(Customer.class);
    }
    
    public static boolean isValidEmail(String email) {
      String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                          "[a-zA-Z0-9_+&*-]+)*@" +
                          "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                          "A-Z]{2,7}$";

      Pattern pat = Pattern.compile(emailRegex);
      if (email == null)
          return false;
      return pat.matcher(email).matches();
  }

  }



