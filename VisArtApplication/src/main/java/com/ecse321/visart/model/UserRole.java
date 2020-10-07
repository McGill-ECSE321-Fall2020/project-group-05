/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;

@Entity
  @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
  @DiscriminatorColumn(name="user_type",discriminatorType=DiscriminatorType.INTEGER)
// line 36 "../../../../../resources/visart.ump"
public class UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(String aIdCode, User aUser)
  {
    idCode = aIdCode;
    if (aUser == null || aUser.getRole() != null)
    {
      throw new RuntimeException("Unable to create UserRole due to aUser. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    user = aUser;
  }

  public UserRole(String aIdCode, String aIdCodeForUser, String aEmailAddressForUser, String aDisplaynameForUser, String aUsernameForUser, String aPasswordForUser, Gallery aGalleryForUser)
  {
    idCode = aIdCode;
    user = new User(aIdCodeForUser, aEmailAddressForUser, aDisplaynameForUser, aUsernameForUser, aPasswordForUser, this, aGalleryForUser);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIdCode(String aIdCode)
  {
    boolean wasSet = false;
    idCode = aIdCode;
    wasSet = true;
    return wasSet;
  }


   @OneToOne
   private User user;
   @Id
   private String idCode;
   
  public String getIdCode()
  {
    return idCode;
  }
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

  // line 47 "../../../../../resources/visart.ump"
   public  UserRole(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "idCode" + ":" + getIdCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}