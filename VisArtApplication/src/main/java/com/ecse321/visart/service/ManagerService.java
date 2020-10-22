package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.ManagerRepository;

@Service
public class ManagerService {

  @Autowired
  ManagerRepository managerRepo;
  
  @Autowired
  EntityRepository entityRepo;
  
  @Transactional
  public Manager createManager(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {
    
    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("Manager id code cannot be empty!");
    }
    
    return managerRepo.createManager(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword,
        aProfilePicLink, aProfileDescription);
    
  }
  
  @Transactional
  public Manager getManager(String aIdCode) {
    return managerRepo.getManager(aIdCode);
  }

  @Transactional
  public List<Manager> getAllUsers() {
    return entityRepo.getAllEntities(Manager.class);
  }
  
  
}
