package com.ecse321.visart.service;

import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.UserRepository;

@Service
public class UserService {
  @Autowired
  UserRepository userRepo;
  
  @Autowired
  EntityRepository entityRepo;

  @Transactional
  public User createUser(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {
    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("User id code cannot be empty!");
    }
    
    return userRepo.createUser(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword,
        aProfilePicLink, aProfileDescription);
  }

  @Transactional
  public User getUser(String aIdCode) {
    return userRepo.getUser(aIdCode);
  }

  @Transactional
  public List<User> getAllUsers() {
    return entityRepo.getAllEntities(User.class);
  }
  
  
}
