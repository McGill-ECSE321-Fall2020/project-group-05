package com.ecse321.visart.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;

/**
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 *
 */
@Repository
public class ManagerRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * createManager method creates a Manager in the database with the given
   * parameters
   * 
   * @param  aIdCode             primary key of Manager in database
   * @param  aEmailAddress       email address for manager
   * @param  aDisplayname        display name of manager
   * @param  aUsername           username of manager
   * @param  aPassword           password of manager
   * @param  aProfilePicLink     profile picture link
   * @param  aProfileDescription profile description for manager
   * @return                     a new persisted Manager instance
   */
  @Transactional
  public Manager createManager(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {
    User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword, aProfilePicLink,
        aProfileDescription);
    Manager manager = new Manager(aIdCode, usr);
    entityManager.persist(usr);
    entityManager.persist(manager);
    return manager;
  }
  
  public Manager createManager(String userIdCode) {
    User user = entityManager.find(User.class, userIdCode);
    Manager manager = new Manager(userIdCode, user);
    entityManager.persist(manager);
    return manager;
  }

  /**
   * Overloaded getManager method retrieves Manager instance from the database.
   * Its collection of promotedListings will be inaccessible unless the option
   * alsoPromotedListings is set to true.
   * 
   * @param  aIdCode the primary key of the Manager instance
   * @return         the Manager instance from the database
   */
  @Transactional
  public Manager getManager(String aIdCode) {
    return getManager(aIdCode, false);
  }

  /**
   * getManager method retrieves a Manager instance from the database. Its
   * promotedListings collection is lazy loaded, so alsoPromotedListings must be
   * set to true, to load it.
   * 
   * @param  aIdCode              the primary key of the Manager instance
   * @param  alsoPromotedListings also fetch the promotedListings from database
   * @return                      the Manager instance from the database
   */
  @Transactional
  public Manager getManager(String aIdCode, boolean alsoPromotedListings) {
    Manager m = entityManager.find(Manager.class, aIdCode);
    if (alsoPromotedListings)
      m.getPromotedListings().size();
    return m;
  }

  /**
   * updateManager method updates an Manager instance's properties in the
   * database. Also updates the underlying User's properties.
   * 
   * @param manager the changed Manager which will be written to database
   */
  @Transactional
  public void updateManager(Manager manager) {
    entityManager.merge(manager);
    entityManager.merge(manager.getUser());
  }

  /**
   * Overloaded deleteManager method that deletes the given Manager instance from
   * the database. Also deletes the underlying User instance.
   * 
   * @param  manager the Manager instance to delete
   * @return         true if successful delete
   */
  @Transactional
  public boolean deleteManager(Manager manager) {
    return deleteManager(manager.getIdCode());
  }

  /**
   * deleteManager method removes the Manager instance in the database, given the
   * corresponding primary key. Also removes the underlying User instance.
   * 
   * @param  id primary key of the Manager instance to remove
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteManager(String id) {
    Manager entity = entityManager.find(Manager.class, id);
    if (entity == null) {
      return true;
    }
    User usr = entityManager.find(User.class, entity.getUser().getIdCode());
    entityManager.remove(entityManager.merge(entity));
    entityManager.remove(entityManager.merge(usr));
    return !entityManager.contains(entity) && !entityManager.contains(usr);
  }

  /**
   * getAllKeys queries the database for all of the primary keys of the Managers
   * instances.
   * 
   * @return list of primary keys for Managers
   */
  @Transactional
  public List<String> getAllKeys() {
    return entityManager.createQuery("SELECT idCode FROM Manager", String.class).getResultList();
  }
}
