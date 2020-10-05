/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import java.util.*;

// line 4 "../../../../../resources/visart.ump"
public class Gallery
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Gallery Attributes
  private String name;

  //Gallery Associations
  private List<User> users;
  private List<ArtListing> artListings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Gallery(String aName)
  {
    name = aName;
    users = new ArrayList<User>();
    artListings = new ArrayList<ArtListing>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  /**
   * Can there be multiple galleries? We'll need location objects then
   */
  public String getName()
  {
    return name;
  }
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public ArtListing getArtListing(int index)
  {
    ArtListing aArtListing = artListings.get(index);
    return aArtListing;
  }

  public List<ArtListing> getArtListings()
  {
    List<ArtListing> newArtListings = Collections.unmodifiableList(artListings);
    return newArtListings;
  }

  public int numberOfArtListings()
  {
    int number = artListings.size();
    return number;
  }

  public boolean hasArtListings()
  {
    boolean has = artListings.size() > 0;
    return has;
  }

  public int indexOfArtListing(ArtListing aArtListing)
  {
    int index = artListings.indexOf(aArtListing);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public User addUser(String aEmailAddress, String aUsername, String aPassword, String aIdCode, String aDisplayname)
  {
    return new User(aEmailAddress, aUsername, aPassword, aIdCode, aDisplayname, this);
  }

  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    Gallery existingGallery = aUser.getGallery();
    boolean isNewGallery = existingGallery != null && !this.equals(existingGallery);
    if (isNewGallery)
    {
      aUser.setGallery(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a gallery
    if (!this.equals(aUser.getGallery()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfArtListings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ArtListing addArtListing(ArtListing.PostVisibility aVisibility, Manager aManager, Customer aFavoritedCustomer, Artist aArtist)
  {
    return new ArtListing(aVisibility, this, aManager, aFavoritedCustomer, aArtist);
  }

  public boolean addArtListing(ArtListing aArtListing)
  {
    boolean wasAdded = false;
    if (artListings.contains(aArtListing)) { return false; }
    Gallery existingGallery = aArtListing.getGallery();
    boolean isNewGallery = existingGallery != null && !this.equals(existingGallery);
    if (isNewGallery)
    {
      aArtListing.setGallery(this);
    }
    else
    {
      artListings.add(aArtListing);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeArtListing(ArtListing aArtListing)
  {
    boolean wasRemoved = false;
    //Unable to remove aArtListing, as it must always have a gallery
    if (!this.equals(aArtListing.getGallery()))
    {
      artListings.remove(aArtListing);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addArtListingAt(ArtListing aArtListing, int index)
  {  
    boolean wasAdded = false;
    if(addArtListing(aArtListing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtListings()) { index = numberOfArtListings() - 1; }
      artListings.remove(aArtListing);
      artListings.add(index, aArtListing);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveArtListingAt(ArtListing aArtListing, int index)
  {
    boolean wasAdded = false;
    if(artListings.contains(aArtListing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfArtListings()) { index = numberOfArtListings() - 1; }
      artListings.remove(aArtListing);
      artListings.add(index, aArtListing);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addArtListingAt(aArtListing, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (artListings.size() > 0)
    {
      ArtListing aArtListing = artListings.get(artListings.size() - 1);
      aArtListing.delete();
      artListings.remove(aArtListing);
    }
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }
}