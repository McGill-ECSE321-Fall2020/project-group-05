package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.Gallery;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.User;
import com.ecse321.visart.model.UserRole;

/**
 * 
 * @author anwar
 *
 */
@Repository
public class ManagerRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * This method creates a Manager in the database with the given parameters
   * 
   * @param  aIdCode databse
   * @param  aEmailAddress
   * @param  aDisplayname
   * @param  aUsername
   * @param  aPassword
   * @param  aProfilePicLink
   * @param  aProfileDescription
   * @return a newly created Manager
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

  /**
   * Retrieves Manager information from the database
   * 
   * @param  aIdCode
   * @return the manager information
   */
  @Transactional
  public Manager getManager(String aIdCode) {
    return entityManager.find(Manager.class, aIdCode);
  }

  /**
   * updates manager information if there are changes
   * 
   * @param manager
   */
  @Transactional
  public void updateManager(Manager manager) {
    entityManager.merge(manager);
    entityManager.merge(manager.getUser());
  }

  /**
   * deletes manager profile from the database
   * 
   * @param  manager
   * @return a boolean
   */
  @Transactional
  public boolean deleteManager(Manager manager) {
    Manager entity = entityManager.find(Manager.class, manager.getIdCode());
    User usr = entityManager.find(User.class, manager.getIdCode());
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
      entityManager.remove(entityManager.merge(usr));
    } else {
      entityManager.remove(entity);
      entityManager.remove(usr);
    }

    return !entityManager.contains(entity) && !entityManager.contains(usr);
  }

}
