/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import java.util.*;

// line 21 "../../../../../resources/visart.ump"
public class UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserRole Associations
  private User user;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(User aUser)
  {
    if (aUser == null || aUser.getRole() != null)
    {
      throw new RuntimeException("Unable to create UserRole due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    user = aUser;
  }

  public UserRole(String aEmailAddressForUser, String aUsernameForUser, String aPasswordForUser, String aIdCodeForUser, String aDisplaynameForUser, Gallery aGalleryForUser)
  {
    user = new User(aEmailAddressForUser, aUsernameForUser, aPasswordForUser, aIdCodeForUser, aDisplaynameForUser, this, aGalleryForUser);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public User getUser()
  {
    return user;
  }

  public void delete()
  {
    User existingUser = user;
    user = null;
    if (existingUser != null)
    {
      existingUser.delete();
    }
  }

}