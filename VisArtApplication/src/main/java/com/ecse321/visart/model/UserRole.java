/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;

@Entity
  @Inheritance(strategy=InheritanceType.SINGLE_TABLE)
  @DiscriminatorColumn(name="user_type",discriminatorType=DiscriminatorType.INTEGER)
// line 37 "../../../../../resources/visart.ump"
public class UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //UserRole Attributes

  //UserRole Associations

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public UserRole(String aIdCode, User aUser)
  {
    idCode = aIdCode;
    boolean didAddUser = setUser(aUser);
    if (!didAddUser)
    {
      throw new RuntimeException("Unable to create role due to user. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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
  /* Code from template association_SetOneToOptionalOne */
  public boolean setUser(User aNewUser)
  {
    boolean wasSet = false;
    if (aNewUser == null)
    {
      //Unable to setUser to null, as role must always be associated to a user
      return wasSet;
    }
    
    UserRole existingRole = aNewUser.getRole();
    if (existingRole != null && !equals(existingRole))
    {
      //Unable to setUser, the current user already has a role, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    User anOldUser = user;
    user = aNewUser;
    user.setRole(this);

    if (anOldUser != null)
    {
      anOldUser.setRole(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    User existingUser = user;
    user = null;
    if (existingUser != null)
    {
      existingUser.setRole(null);
    }
  }

  // line 48 "../../../../../resources/visart.ump"
   public  UserRole(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "idCode" + ":" + getIdCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "user = "+(getUser()!=null?Integer.toHexString(System.identityHashCode(getUser())):"null");
  }
}