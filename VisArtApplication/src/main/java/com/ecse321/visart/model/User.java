/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;

@Entity
  @Table(name="users")
// line 10 "../../../../../resources/visart.ump"
public class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String emailAddress;
  private String displayname;
  private String username;
  private String password;

  //User Associations

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername, String aPassword)
  {
    idCode = aIdCode;
    emailAddress = aEmailAddress;
    displayname = aDisplayname;
    username = aUsername;
    password = aPassword;
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

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setDisplayname(String aDisplayname)
  {
    boolean wasSet = false;
    displayname = aDisplayname;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  
   @OneToOne
   private UserRole role;
   @Transient
   private Gallery gallery;
   @Id
   private String idCode;
  public String getIdCode()
  {
    return idCode;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }

  public String getDisplayname()
  {
    return displayname;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public UserRole getRole()
  {
    return role;
  }

  public boolean hasRole()
  {
    boolean has = role != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Gallery getGallery()
  {
    return gallery;
  }

  public boolean hasGallery()
  {
    boolean has = gallery != null;
    return has;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setRole(UserRole aNewRole)
  {
    boolean wasSet = false;
    if (role != null && !role.equals(aNewRole) && equals(role.getUser()))
    {
      //Unable to setRole, as existing role would become an orphan
      return wasSet;
    }

    role = aNewRole;
    User anOldUser = aNewRole != null ? aNewRole.getUser() : null;

    if (!this.equals(anOldUser))
    {
      if (anOldUser != null)
      {
        anOldUser.role = null;
      }
      if (role != null)
      {
        role.setUser(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setGallery(Gallery aGallery)
  {
    boolean wasSet = false;
    Gallery existingGallery = gallery;
    gallery = aGallery;
    if (existingGallery != null && !existingGallery.equals(aGallery))
    {
      existingGallery.removeUser(this);
    }
    if (aGallery != null)
    {
      aGallery.addUser(this);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    UserRole existingRole = role;
    role = null;
    if (existingRole != null)
    {
      existingRole.delete();
    }
    if (gallery != null)
    {
      Gallery placeholderGallery = gallery;
      this.gallery = null;
      placeholderGallery.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "idCode" + ":" + getIdCode()+ "," +
            "emailAddress" + ":" + getEmailAddress()+ "," +
            "displayname" + ":" + getDisplayname()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "role = "+(getRole()!=null?Integer.toHexString(System.identityHashCode(getRole())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "gallery = "+(getGallery()!=null?Integer.toHexString(System.identityHashCode(getGallery())):"null");
  }
}