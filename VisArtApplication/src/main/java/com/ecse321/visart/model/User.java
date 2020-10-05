/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import java.util.*;

// line 11 "../../../../../resources/visart.ump"
public class User
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, User> usersByUsername = new HashMap<String, User>();
  private static Map<String, User> usersByIdCode = new HashMap<String, User>();
  private static Map<String, User> usersByDisplayname = new HashMap<String, User>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String emailAddress;
  private String username;
  private String password;
  private String idCode;
  private String displayname;

  //User Associations
  private UserRole role;
  private Gallery gallery;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aEmailAddress, String aUsername, String aPassword, String aIdCode, String aDisplayname, Gallery aGallery)
  {
    emailAddress = aEmailAddress;
    password = aPassword;
    if (!setUsername(aUsername))
    {
      throw new RuntimeException("Cannot create due to duplicate username. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setIdCode(aIdCode))
    {
      throw new RuntimeException("Cannot create due to duplicate idCode. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    if (!setDisplayname(aDisplayname))
    {
      throw new RuntimeException("Cannot create due to duplicate displayname. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    boolean didAddGallery = setGallery(aGallery);
    if (!didAddGallery)
    {
      throw new RuntimeException("Unable to create user due to gallery. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setEmailAddress(String aEmailAddress)
  {
    boolean wasSet = false;
    emailAddress = aEmailAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    String anOldUsername = getUsername();
    if (anOldUsername != null && anOldUsername.equals(aUsername)) {
      return true;
    }
    if (hasWithUsername(aUsername)) {
      return wasSet;
    }
    username = aUsername;
    wasSet = true;
    if (anOldUsername != null) {
      usersByUsername.remove(anOldUsername);
    }
    usersByUsername.put(aUsername, this);
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setIdCode(String aIdCode)
  {
    boolean wasSet = false;
    String anOldIdCode = getIdCode();
    if (anOldIdCode != null && anOldIdCode.equals(aIdCode)) {
      return true;
    }
    if (hasWithIdCode(aIdCode)) {
      return wasSet;
    }
    idCode = aIdCode;
    wasSet = true;
    if (anOldIdCode != null) {
      usersByIdCode.remove(anOldIdCode);
    }
    usersByIdCode.put(aIdCode, this);
    return wasSet;
  }

  public boolean setDisplayname(String aDisplayname)
  {
    boolean wasSet = false;
    String anOldDisplayname = getDisplayname();
    if (anOldDisplayname != null && anOldDisplayname.equals(aDisplayname)) {
      return true;
    }
    if (hasWithDisplayname(aDisplayname)) {
      return wasSet;
    }
    displayname = aDisplayname;
    wasSet = true;
    if (anOldDisplayname != null) {
      usersByDisplayname.remove(anOldDisplayname);
    }
    usersByDisplayname.put(aDisplayname, this);
    return wasSet;
  }

  public String getEmailAddress()
  {
    return emailAddress;
  }

  public String getUsername()
  {
    return username;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithUsername(String aUsername)
  {
    return usersByUsername.get(aUsername);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithUsername(String aUsername)
  {
    return getWithUsername(aUsername) != null;
  }

  public String getPassword()
  {
    return password;
  }

  public String getIdCode()
  {
    return idCode;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithIdCode(String aIdCode)
  {
    return usersByIdCode.get(aIdCode);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithIdCode(String aIdCode)
  {
    return getWithIdCode(aIdCode) != null;
  }

  public String getDisplayname()
  {
    return displayname;
  }
  /* Code from template attribute_GetUnique */
  public static User getWithDisplayname(String aDisplayname)
  {
    return usersByDisplayname.get(aDisplayname);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithDisplayname(String aDisplayname)
  {
    return getWithDisplayname(aDisplayname) != null;
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
    usersByUsername.remove(getUsername());
    usersByIdCode.remove(getIdCode());
    usersByDisplayname.remove(getDisplayname());
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


  public String toString()
  {
    return super.toString() + "["+
            "emailAddress" + ":" + getEmailAddress()+ "," +
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "," +
            "idCode" + ":" + getIdCode()+ "," +
            "displayname" + ":" + getDisplayname()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "role = "+(getRole()!=null?Integer.toHexString(System.identityHashCode(getRole())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "gallery = "+(getGallery()!=null?Integer.toHexString(System.identityHashCode(getGallery())):"null");
  }
}