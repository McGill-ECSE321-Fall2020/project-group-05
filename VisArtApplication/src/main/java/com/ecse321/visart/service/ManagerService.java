package com.ecse321.visart.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
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

  @Autowired
  ArtListingService artListingService;

  @Transactional
  public Manager createManager(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {

    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("Manager id code cannot be empty!");
    }

    if(isValidEmail(aEmailAddress)==false) {
      throw new IllegalArgumentException("Email address is invalid");
    }

    if(aDisplayname.length()<5 || aDisplayname.length()>25) {
      throw new IllegalArgumentException("This Display Name is invalid, must be between 5 and 25 characters!");
    }

    List<Manager> l1 = entityRepo.findEntityByAttribute("displayname",Manager.class,aDisplayname);
    if (l1 != null && l1.size() > 0) {
        throw new IllegalArgumentException("This Display Name is already taken!");
    }

    if(aUsername.length()<5 || aUsername.length()>25 ) {
      throw new IllegalArgumentException("This User Name is invalid, must be between 5 and 25 characters!");
    }

    List<Manager> l2 = entityRepo.findEntityByAttribute("username",Manager.class,aUsername);
    if (l2 != null && l2.size() > 0) {
        throw new IllegalArgumentException("This Username is already taken!");
    }

    if(aPassword.length()<8 || aPassword.length()>40) {
      throw new IllegalArgumentException("Password must be between 8 and 40 characters");
    }

    if(aProfileDescription.length()>255) {
      throw new IllegalArgumentException("Description must be less than 255 characters");
    }

    String hashedPassword;

    return managerRepo.createManager(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword,
        aProfilePicLink, aProfileDescription);

  }

//  @Transactional
//  public Manager addManagerListing(String aIdCode, ArtListing artListing) {
//    Manager manager = managerRepo.getManager(aIdCode, true);
//    if(artListing != null) {
//      manager.addPromotedListing(artListing);
//    } else {
//      throw new IllegalArgumentException("Cannot add a null art listing");
//    }
//    return manager;
//  }

  @Transactional
  public Manager getManager(String aIdCode) {
    return managerRepo.getManager(aIdCode);
  }

  @Transactional
  public Manager getManager(String aIdCode, Boolean promotedListings) {
    return managerRepo.getManager(aIdCode, promotedListings);
  }


  @Transactional
  public void addListing(String aIdCode ,String aListingIdCode) {
    Manager manager = getManager(aIdCode, true);
    manager.addPromotedListing(artListingService.getArtListing(aListingIdCode));
    managerRepo.updateManager(manager);
  }

  @Transactional
  public void removeListing(String aIdCode, String aListingIdCode) {
    Manager manager = getManager(aIdCode, true);
    manager.removePromotedListing(artListingService.getArtListing(aListingIdCode));
    managerRepo.updateManager(manager);
  }

  @Transactional
  public Boolean deleteManager(String aIdCode) {
    if(aIdCode != null && !aIdCode.contentEquals("")) {
      return managerRepo.deleteManager(aIdCode);
    } else {
      return false;
    }
  }
  @Transactional
  public List<Manager> getAllUsers() {
    return entityRepo.getAllEntities(Manager.class);
  }

  //https://www.geeksforgeeks.org/check-email-address-valid-not-java/
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
