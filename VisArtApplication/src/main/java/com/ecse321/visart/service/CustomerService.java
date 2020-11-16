package com.ecse321.visart.service;

import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.User;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.repositories.EntityRepository;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.CustomerRepository;

@Service
public class CustomerService {

  @Autowired
  CustomerRepository customerRepo;

  @Autowired
  EntityRepository entityRepo;

  @Autowired
  ArtPieceRepository artPieceRepo;

  @Autowired
  ArtOrderService artOrderService;

  @Autowired
  ArtListingService artListingService;

  @Autowired
  TicketService ticketService;

  @Autowired
  UserService userService;

  @Transactional
  public Customer createCustomerUnified(String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {

    User user = userService.createUser(aEmailAddress, aDisplayname, aUsername, aPassword,
        aProfilePicLink, aProfileDescription);
    return customerRepo.createCustomer(user.getIdCode());
  }

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

    if (aIdCode == null || aIdCode.length() < 1) {
      throw new IllegalArgumentException("Tag id code cannot be empty!");
    }

    if (aEmailAddress == null || aEmailAddress.length() < 0 || !isValidEmail(aEmailAddress)) {
      throw new IllegalArgumentException("Email address is invalid");
    }
    if (aDisplayname.length() < 0 || aDisplayname == null || aDisplayname.length() < 5
        || aDisplayname.length() > 25) {
      throw new IllegalArgumentException(
          "This Display Name is invalid, must be greater than 5 and less than 25 characters!");
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

    if (aPassword == null) {
      throw new IllegalArgumentException("Password cannot be empty!");
    }
    if (aPassword.length() < 8 || aPassword.length() > 40) {
      throw new IllegalArgumentException(
          "Password must have atleast 8 characters and less than 40 ");
    }
    if (aProfilePicLink == null || aProfilePicLink.length() < 0) {
      throw new IllegalArgumentException("Profile picture link cannot be empty!");
    }
    if (aProfileDescription == null || aProfileDescription.length() > 255
        || aProfileDescription.length() < 0) {
      throw new IllegalArgumentException("Description must be less than 255 characters");
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
    if (aListingIdCode != null) {
      customer.addFavoriteListing(artListingService.getArtListing(aListingIdCode));
      customerRepo.updateCustomer(customer);
    }
  }

  @Transactional
  public void removefavoriteList(String aIdCode, String aListingIdCode) {
    Customer customer = getCustomer(aIdCode, false, true);
    if (aListingIdCode != null) {
      customer.removeFavoriteListing(artListingService.getArtListing(aListingIdCode));
      customerRepo.updateCustomer(customer);
    }
  }

  @Transactional
  public void addtickets(String aIdCode, String ticketIdCode) { // TODO: make this ticketIdCode
    Customer customer = getCustomer(aIdCode, true, false);
    if (ticketIdCode != null) {
      customer.addBoughtTicket(ticketService.getTicket(ticketIdCode));
      customerRepo.updateCustomer(customer);
    }
  }

  @Transactional
  public void removetickets(String aIdCode, String ticketIdCode) {
    Customer customer = getCustomer(aIdCode, true, false);
    if (ticketIdCode != null) {
      customer.removeBoughtTicket(ticketService.getTicket(ticketIdCode));
      customerRepo.updateCustomer(customer);
    }
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

}
