package com.ecse321.visart.service;

import java.util.List;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecse321.visart.dto.CredentialsDto;
import com.ecse321.visart.model.User;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.UserRepository;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;

@Service
public class UserService {
  @Autowired
  UserRepository userRepo;

  @Autowired
  EntityRepository entityRepo;

  /**
   *
   * @param  aEmailAddress
   * @param  aDisplayname
   * @param  aUsername
   * @param  aPassword
   * @param  aProfilePicLink
   * @param  aProfileDescription
   * @return
   */
  @Transactional
  public User createUser(String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {

    String aIdCode = EntityRepository.getUniqueKey();

    if (aEmailAddress == null || isValidEmail(aEmailAddress) == false) {
      throw new IllegalArgumentException("This Email address is invalid");
    }

    if (aDisplayname == null || aDisplayname.length() < 5 || aDisplayname.length() > 25) {
      throw new IllegalArgumentException(
          "This Display Name is invalid, must be between 5 and 25 characters!");
    }

    List<User> l1 = entityRepo.findEntityByAttribute("displayname", User.class, aDisplayname);
    if (l1 != null && l1.size() > 0) {
      throw new IllegalArgumentException("This Display Name is already taken!");
    }

    if (aUsername == null || aUsername.length() < 5 || aUsername.length() > 25) {
      throw new IllegalArgumentException(
          "This User Name is invalid, must be between 5 and 25 characters!");
    }

    List<User> l2 = entityRepo.findEntityByAttribute("username", User.class, aUsername);
    if (l2 != null && l2.size() > 0) {
      throw new IllegalArgumentException("This Username is already taken!");
    }

    if (aPassword == null || aPassword.length() < 8 || aPassword.length() > 40) {
      throw new IllegalArgumentException("Password must be between 8 and 40 characters");
    }

    if (aProfileDescription == null || aProfileDescription.length() > 1000) {
      throw new IllegalArgumentException("Description must be less than 255 characters");
    }

    List<User> l0 = entityRepo.findEntityByAttribute("emailAddress", User.class, aEmailAddress);
    if (l0 != null && l0.size() > 0) {
      throw new IllegalArgumentException("This Email Address is already taken!");
    }

    return userRepo.createUser(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword,
        aProfilePicLink, aProfileDescription);

  }

  /**
   *
   * @param  aIdCode
   * @param  aEmailAddress
   * @param  aDisplayname
   * @param  aUsername
   * @param  aPassword
   * @param  aProfilePicLink
   * @param  aProfileDescription
   * @return
   */
  @Transactional
  public User updateUser(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {
    // TODO: For ALL Attributes, if parameter is null, then leave attribute
    // unchanged, if not-null, validate (with exceptions) then update value

    if (aIdCode == null || aIdCode == "" || aIdCode == null) {
      throw new IllegalArgumentException("User cannot be found");
    }

    User user = userRepo.getUser(aIdCode); // TODO: must check that user is not null

    if (aEmailAddress != null && isValidEmail(aEmailAddress) == false) {
      throw new IllegalArgumentException("This Email address is invalid");
    }

    if (aDisplayname != null && (aDisplayname.length() < 5 || aDisplayname.length() > 25)) {
      throw new IllegalArgumentException(
          "This Display Name is invalid, must be between 5 and 25 characters!");
    }

    List<User> l1 = entityRepo.findEntityByAttribute("displayname", User.class, aDisplayname);
    if (l1 != null && l1.size() > 0) {
      throw new IllegalArgumentException("This Display Name is already taken!");
    }

    if (aUsername != null && (aUsername.length() < 5 || aUsername.length() > 25)) {
      throw new IllegalArgumentException(
          "This User Name is invalid, must be between 5 and 25 characters!");
    }

    List<User> l2 = entityRepo.findEntityByAttribute("username", User.class, aUsername);
    if (l2 != null && l2.size() > 0) {
      throw new IllegalArgumentException("This Username is already taken!");
    }

    if (aPassword != null && (aPassword.length() < 8 || aPassword.length() > 40)) {
      throw new IllegalArgumentException("Password must be between 8 and 40 characters");
    }

    if (aProfileDescription != null && aProfileDescription.length() > 1000) {
      throw new IllegalArgumentException("Description must be less than 255 characters");
    }

    List<User> l0 = entityRepo.findEntityByAttribute("emailAddress", User.class, aEmailAddress);
    if (l0 != null && l0.size() > 0) {
      throw new IllegalArgumentException("This Email Address is already taken!");
    }

    if (aDisplayname != null)
      user.setDisplayname(aDisplayname);
    if (aUsername != null)
      user.setUsername(aUsername);
    if (aEmailAddress != null)
      user.setEmailAddress(aEmailAddress);
    if (aPassword != null)
      user.setPassword(aPassword);
    if (aProfilePicLink != null)
      user.setProfilePicLink(aProfilePicLink);
    if (aProfileDescription != null)
      user.setProfileDescription(aProfileDescription);

    userRepo.updateUser(user);

    return user;

  }

  /**
   * logs in the user
   * 
   * @param  aEmailAddress
   * @param  aPassword
   * @return               isLoggedIn
   */
  @Transactional
  public boolean loginUser(String username, String aPassword) {
    List<User> users = entityRepo.findEntityByAttribute("username", User.class, username);
    User user = users.size() > 0 ? users.get(0) : null; 
    if (user == null) {
      throw new IllegalArgumentException("Username cannot be found");
    }
    if (!user.getPassword().equals(aPassword)) { // TODO: return false instead of exception, true for
                                           // success, false for wrong password
      throw new IllegalArgumentException("Password does not match");
    }
    return true;
  }

  /**
   * gets a specific user from the db
   * 
   * @param  aIdCode
   * @param  user
   * @return         isLoggedIn
   */
  @Transactional
  public User getUser(String aIdCode) {
    return userRepo.getUser(aIdCode);
  }

  /**
   * delete a specific user from the db
   * 
   * @param  aIdCode
   * @param  user
   * @return         isDeleted
   */
  @Transactional
  public Boolean deleteUser(String aIdCode) {
    if (aIdCode != null && !aIdCode.contentEquals("")) {
      return userRepo.deleteUser(aIdCode);
    } else {
      return false;
    }
  }

  /**
   * get all users from the db
   * 
   * @return allUsers
   */
  @Transactional
  public List<User> getAllUsers() {
    return entityRepo.getAllEntities(User.class);
  }

  // https://www.geeksforgeeks.org/check-email-address-valid-not-java/
  public static boolean isValidEmail(String email) {
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null)
      return false;
    return pat.matcher(email).matches();
  }

  @Transactional
  public CredentialsDto generateCredentials(String id) {
    CredentialsDto creds = new CredentialsDto();
    
    try {
      creds.setFirebaseJWT(FirebaseAuth.getInstance().createCustomToken(id));
    } catch (FirebaseAuthException e) {
      e.printStackTrace();
    }
    
    return creds;
  }

}
