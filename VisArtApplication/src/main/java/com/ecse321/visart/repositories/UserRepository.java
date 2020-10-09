
package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

// import com.ecse321.visart.model.User;
import com.ecse321.visart.model.UserRole;
import com.ecse321.visart.model.User;

/**
 * CRUD Repository operations for a User.
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 *
 */
@Repository
public class UserRepository {

  @Autowired
  EntityManager entityManager;

  /**
   * createUser method creates a single User instance with the given parameters.
   * Does not create a UserRole, and it is not recommended to use this method to
   * create Users. ManagerRepository and CustomerRepository, create a User for
   * each new Manager or Customer instance.
   * 
   * @param  aIdCode             the primary key for the User in database
   * @param  aEmailAddress       the email address of the User
   * @param  aDisplayname        the display name for the User
   * @param  aUsername           the User's username
   * @param  aPassword           the User's password
   * @param  aProfilePicLink     a link to the User's profile picture
   * @param  aProfileDescription a description for the User's profile
   * @return                     a database persisted User instance
   */
  @Transactional
  public User createUser(String aIdCode, String aEmailAddress, String aDisplayname,
      String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription) {
    User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword, aProfilePicLink,
        aProfileDescription);
    entityManager.persist(usr);
    return usr;
  }

  /**
   * getUser method retrieves a User instance from the database given its
   * corresponding primary key.
   * 
   * @param  aIdCode the primary key of the User in database
   * @return         a persisted User instance from the database
   */
  @Transactional
  public User getUser(String aIdCode) {
    return entityManager.find(User.class, aIdCode);
  }

  /**
   * updateUser method updates a User instance's properties in the
   * database.
   * 
   * @param usr the User instance whose changes are written to database
   */
  @Transactional
  public void updateUser(User usr) {
    entityManager.merge(usr);

  }

  /**
   * Overloaded deleteUser method that deletes the given User instance from the
   * database.
   * 
   * @param  usr the persisted User to delete from the database
   * @return     true if successful delete
   */
  @Transactional
  public boolean deleteUser(User usr) {
    return deleteUser(usr.getIdCode());
  }

  /**
   * deleteUser method that deletes the User instance in the database, given its
   * corresponding primary key.
   * 
   * @param  id the primary key of the User instance
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteUser(String id) {
    User entity = entityManager.find(User.class, id);
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }
    return !entityManager.contains(entity);
  }

}
