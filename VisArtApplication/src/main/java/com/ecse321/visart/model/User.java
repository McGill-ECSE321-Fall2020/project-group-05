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


  private String emailAddress;
  private String displayname;
  private String username;
  private String password;



  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername, String aPassword, UserRole aRole, Gallery aGallery)
  {
    idCode = aIdCode;
    emailAddress = aEmailAddress;
    displayname = aDisplayname;
    username = aUsername;
    password = aPassword;
    if (aRole == null || aRole.getUser() != null)
    {
      throw new RuntimeException("Unable to create User due to aRole. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    role = aRole;
    boolean didAddGallery = setGallery(aGallery);
    if (!didAddGallery)
    {
      throw new RuntimeException("Unable to create user due to gallery. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public User(String aIdCode, String aEmailAddress, String aDisplayname, String aUsername, String aPassword, String aIdCodeForRole, Gallery aGallery)
  {
    idCode = aIdCode;
    emailAddress = aEmailAddress;
    displayname = aDisplayname;
    username = aUsername;
    password = aPassword;
    role = new UserRole(aIdCodeForRole, this);
    boolean didAddGallery = setGallery(aGallery);
    if (!didAddGallery)
    {
      throw new RuntimeException("Unable to create user due to gallery. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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
  /* Code from template association_GetOne */
  public Gallery getGallery()
  {
    return gallery;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGallery(Gallery aGallery)
  {
    boolean wasSet = false;
    if (aGallery == null)
    {
      return wasSet;
    }

    Gallery existingGallery = gallery;
    gallery = aGallery;
    if (existingGallery != null && !existingGallery.equals(aGallery))
    {
      existingGallery.removeUser(this);
    }
    gallery.addUser(this);
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
    Gallery placeholderGallery = gallery;
    this.gallery = null;
    if(placeholderGallery != null)
    {
      placeholderGallery.removeUser(this);
    }
  }

  // line 30 "../../../../../resources/visart.ump"
   public  User(){
    
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