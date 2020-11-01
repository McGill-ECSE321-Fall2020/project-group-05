package com.ecse321.visart.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.CustomerRepository;

@Service
public class CustomerService {

  @Autowired
  CustomerRepository customerRepo;

  @Autowired
  EntityRepository entityRepo;

  @Autowired
  ArtListingService artListingService;

  @Autowired
  TicketService ticketService;

  /**
   * createCustomer method creates an instance of Customer that is persisted in
   * the database.
   * 
   * @param  aIdCode             database primary key for the new customer
   * @param  aEmailAddress       email address of the customer
   * @param  aDisplayname        Full name of the customer
   * @param  aUsername           User name for the profile to be created
   * @param  aPassword           Password for the profile to be created
   * @param  aProfilePicLink     Profile picture link of the customer
   * @param  aProfileDescription Short description about the customer
   * @return                     persisted Customer instance
   */

  @Transactional
  public Customer createCustomer(String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {

    String aIdCode = EntityRepository.getUniqueKey();

  /*  if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("Customer id code cannot be empty!");
<<<<<<< HEAD
    } */
    if (aEmailAddress == null|| isValidEmail(aEmailAddress)==false) {
=======
    }
    if (aEmailAddress == null) { // TODO: check string length is not 0
>>>>>>> 3524b8e857d873cb196949e103cccd08c18f41fa
      throw new IllegalArgumentException("Email address cannot be empty!");
    }
    if (aDisplayname == null) { // TODO: check string length is not 0
      throw new IllegalArgumentException("Display name cannot be empty!");
    }
    if (aDisplayname.length() < 5||aDisplayname.length() > 25) {
      throw new IllegalArgumentException(
          "This Display Name is invalid, must be less than 25 characters!");
    }
    List<User> l1 = entityRepo.findEntityByAttribute("displayname", User.class, aDisplayname);
    if (l1 != null && l1.size() > 0) {
      throw new IllegalArgumentException("This Display Name is already taken!");
    }

    if (aUsername == null) {
      throw new IllegalArgumentException("User name cannot be empty!");
    }
    if (aUsername.length() < 6 || aUsername.length() > 30) {
      throw new IllegalArgumentException(
          "This User Name is invalid, must be between 6 and 30 characters!");
    }

    List<User> l2 = entityRepo.findEntityByAttribute("username", User.class, aUsername);
    if (l2 != null && l2.size() > 0) {
      throw new IllegalArgumentException("This Username is already taken!");
    }

    if (aPassword == null) {
      throw new IllegalArgumentException("Password cannot be empty!");
    }
    if (aPassword.length() < 8 || aPassword.length() > 40) {
      throw new IllegalArgumentException(
          "Password must have atleast 8 characters and less than 40 ");
    }
    if (aProfilePicLink == null) {
      throw new IllegalArgumentException("Profile picture link cannot be empty!"); // TODO: check
                                                                                   // length of
                                                                                   // picLink string
                                                                                   // as well
    }
    if (aProfileDescription == null) { // TODO: check string length is not 0
      throw new IllegalArgumentException("Profile description cannot be empty!");
    }
    if (aProfileDescription.length() > 255) {
      throw new IllegalArgumentException("Profile description cannot exceed 255 characters!"); // TODO:
                                                                                               // change
                                                                                               // it
                                                                                               // to
                                                                                               // 1000
                                                                                               // maximum
    }

    return customerRepo.createCustomer(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword,
        aProfilePicLink, aProfileDescription);
  }

  @Transactional
  public Customer getCustomer(String aIdCode, boolean alsoBoughtTickets,
      boolean alsoFavoriteListings) {
    return customerRepo.getCustomer(aIdCode, alsoBoughtTickets, alsoFavoriteListings);
  }

  /**
   * addfavoriteList method adds
   * 
   * @param aIdCode
   * @param aListingIdCode
   */
  @Transactional
  public void addfavoriteList(String aIdCode, String aListingIdCode) {
    Customer customer = getCustomer(aIdCode, false, true);
    // TODO: check that artListing retrieved is not null before setting it
    customer.addFavoriteListing(artListingService.getArtListing(aListingIdCode));
    customerRepo.updateCustomer(customer);
  }

  @Transactional
  public void removefavoriteList(String aIdCode, String aListingIdCode) {
    Customer customer = getCustomer(aIdCode, false, true);
    // TODO: check that artListing retrieved is not null before removing it
    // TODO: check that artListing is a member of artListing before removing it
    customer.removeFavoriteListing(artListingService.getArtListing(aListingIdCode));
    customerRepo.updateCustomer(customer);
  }

  public void addtickets(String aIdCode, String aListingIdCode) { // TODO: make this ticketIdCode
    Customer customer = getCustomer(aIdCode, true, true); // TODO: make this true,false

    // TODO: check that the retrieved ticke is not null first
    customer.addBoughtTicket(ticketService.getTicket(aListingIdCode));
    customerRepo.updateCustomer(customer);
  }

  public void removetickets(String aIdCode, String aListingIdCode) { // TODO: make this ticketIdCode
    Customer customer = getCustomer(aIdCode, true, true); // TODO: make this true,false

    // TODO: check that the retrieved ticke is not null first
    // TODO: check that the retrieved ticket is connected to the Customer object
    customer.removeBoughtTicket(ticketService.getTicket(aListingIdCode));
    customerRepo.updateCustomer(customer);
  }

  /**
   * Overloaded getCustomer method retrieves a Customer instance from the database
   * by primary key. Lazy loads all collections by default, and so boughtTickets
   * and favoriteListings are unaccessible unless those options are set to true.
   * 
   * @param  aIdCode database primary key for the customer
   * @return         a persisted Customer instance from the database
   */
  @Transactional
  public Customer getCustomer(String aIdCode) {
    return customerRepo.getCustomer(aIdCode);
  }

  /**
   * deleteCustomer removes the Customer instance from the database, given its
   * primary key. Also removes the underlying User instance, and Artist instance,
   * if present.
   * 
   * @param  id primary key of Customer instance in database
   * @return    true if successful delete
   */
  @Transactional
  public Boolean deleteCustomer(String aIdCode) {
    if (aIdCode != null && !aIdCode.contentEquals("")) {
      return customerRepo.deleteCustomer(aIdCode);
    } else {
      return false;
    }
  }

  /**
   * getAllCustomers retrieves all customers from the database
   * 
   * @return list of all Customers in database
   */
  @Transactional
  public List<Customer> getAllCustomers() {
    return entityRepo.getAllEntities(Customer.class);
  }

  public static boolean isValidEmail(String email) { // TODO: use the isValidEmail from UserService class
    String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
        "[a-zA-Z0-9_+&*-]+)*@" +
        "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
        "A-Z]{2,7}$";

    Pattern pat = Pattern.compile(emailRegex);
    if (email == null)
      return false;
    return pat.matcher(email).matches();
  }

}
