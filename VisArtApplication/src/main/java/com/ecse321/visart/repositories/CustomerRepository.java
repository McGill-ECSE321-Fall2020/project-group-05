package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Gallery;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.UserRole;

@Repository
public class CustomerRepository {

  @Autowired
  EntityManager entityManager;
  ArtistRepository aRepository;

  /**
   * createCustomer
   * 
   * This method creates an instance of createCustomer that is persisted in the
   * database
   * 
   * @param  aIdCode             database ID for the new customer
   * @param  aEmailAddress       email address of the customer
   * @param  aDisplayname        Full name of the customer
   * @param  aUsername           User name for the profile to be created
   * @param  aPassword           Password for the profile to be created
   * @param  aProfilePicLink     Profile picture link of the customer
   * @param  aProfileDescription Short description about the customer
   * @return                     persisted createCustomer instance
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

  /**
   * getCustomer
   * 
   * This method retrieves a customer from the database
   * 
   * @param  aIdCode database ID for the customer
   * @return         a customer with a specific Id
   */
  @Transactional
  public Customer getCustomer(String aIdCode) {
    return entityManager.find(Customer.class, aIdCode);
  }

  /**
   * updateCustomer
   * 
   * This method updates any details about the customer
   * 
   * @param customer takes in information of a customer
   */
  @Transactional
  public void updateCustomer(Customer customer) {
    entityManager.merge(customer.getUser());
    entityManager.merge(customer);
  }

  /**
   * deleteCustomer
   * 
   * This method removes a customer instance from the database
   * 
   * @param  customer
   * @return
   */
  @Transactional
  public boolean deleteCustomer(Customer customer) {
    Customer entity = entityManager.find(Customer.class, customer.getIdCode());
    User usr = entityManager.find(User.class, customer.getIdCode());
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
      entityManager.remove(entityManager.merge(usr));
    } else {
      entityManager.remove(entity);
      entityManager.remove(usr);
    }
    if (customer.getArtist() != null) {
      aRepository.deleteArtist(customer.getArtist());
    }
    return (!entityManager.contains(entity) && !entityManager.contains(customer.getArtist())
        && !entityManager.contains(usr));

  }

}
