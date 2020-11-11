/** 

 */

package com.ecse321.visart.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.User;

/**
 * CRUD Repository operation for a Customer
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 *
 */
@Repository
public class CustomerRepository {

  @Autowired
  EntityManager entityManager;

  @Autowired
  ArtistRepository aRepository;

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
  public Customer createCustomer(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {
    User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword, aProfilePicLink,
        aProfileDescription);
    Customer customer = new Customer(aIdCode, usr);
    entityManager.persist(usr);
    entityManager.persist(customer);
    return customer;
  }

  public Customer createCustomer(String userIdCode) {
    User user = entityManager.find(User.class, userIdCode);
    Customer customer = new Customer(user.getIdCode(), user);
    entityManager.persist(customer);
    return customer;
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
    return getCustomer(aIdCode, false, false);
  }

  /**
   * getCustomer method retreives a Customer instance from the database by the
   * corresponding primary key. All Collections are lazy loaded, so to access
   * boughtTickets, and favoriteListings, those options must be set to true to
   * load them from the database as well.
   * 
   * @param  aIdCode              the primary key in the database
   * @param  alsoBoughtTickets    also fetch associated Tickets
   * @param  alsoFavoriteListings also fetch asssociated Listings
   * @return                      a Customer instance loaded from database
   */
  @Transactional
  public Customer getCustomer(String aIdCode, boolean alsoBoughtTickets,
      boolean alsoFavoriteListings) {
    Customer c = entityManager.find(Customer.class, aIdCode);
    if (alsoBoughtTickets)
      c.getBoughtTickets().size();
    if (alsoFavoriteListings)
      c.getFavoriteListings().size();
    return c;
  }

  /**
   * updateArtOrder method updates an ArtOrder instance's properties in the
   * database. Also updates underlying User instance.
   * 
   * @param customer Customer instance whose changes will be written to database
   */
  @Transactional
  public void updateCustomer(Customer customer) {
    entityManager.merge(customer.getUser());
    entityManager.merge(customer);
  }

  /**
   * Overloaded deleteCustomer method removes the given Customer instance from the
   * database.
   * 
   * @param  customer the Customer instance to remove from database
   * @return          true if successful delete
   */
  @Transactional
  public boolean deleteCustomer(Customer customer) {
    return deleteCustomer(customer.getIdCode());
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
  public boolean deleteCustomer(String id) {
    Customer entity = entityManager.find(Customer.class, id);
    if (entity == null) {
      return true;
    }
    entityManager.remove(entityManager.merge(entity));
    User usr = entityManager.find(User.class, entity.getIdCode());
    if (usr != null)
      entityManager.remove(entityManager.merge(usr));
    if (entity.getArtist() != null) {
      aRepository.deleteArtist(entity.getArtist());
    }
    return (!entityManager.contains(entity) && !entityManager.contains(entity.getArtist())
        && !entityManager.contains(usr));
  }

  /**
   * getAllKeys queries the database for all of the primary keys of the Customers
   * instances.
   * 
   * @return list of primary keys for Customers
   */
  @Transactional
  public List<String> getAllKeys() {
    return entityManager.createQuery("SELECT idCode FROM Customer", String.class).getResultList();
  }
}
