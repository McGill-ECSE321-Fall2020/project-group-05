/** 
 *@author Nikola Milekic 
 *@author Daniel Bucci
 */

package com.ecse321.visart.repositories;
import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


//import com.ecse321.visart.model.User;
import com.ecse321.visart.model.UserRole;
import com.ecse321.visart.model.User;

@Repository
public class UserRepository {
    
  @Autowired
  EntityManager entityManager;
  
  @Transactional
  public User createUser(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername, String aPassword, String aProfilePicLink, String aProfileDescription ) {
    User usr = new User(aIdCode, aEmailAddress, aDisplayname, aUsername, aPassword, aProfilePicLink, aProfileDescription );
    entityManager.persist(usr);
    return usr;
  }
  
  @Transactional 
  public User getUser(String aIdCode) {
    return entityManager.find(User.class, aIdCode);
  }
  
  @Transactional
  public void updateUser(User usr) {
    entityManager.merge(usr);
    
  }
  
  @Transactional 
  public boolean deleteUser(User usr) {
    User entity = entityManager.find(User.class, usr.getIdCode());
    if(entityManager.contains(entity)){
      entityManager.remove(entityManager.merge(entity));
      
    } else {
      entityManager.remove(entity);
      
    }
    
    return !entityManager.contains(entity); 
  }

}
