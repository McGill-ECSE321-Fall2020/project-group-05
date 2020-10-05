/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.*;

// line 25 "../../../../../resources/visart.ump"
public class Manager extends UserRole
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, Manager> managersByDbId = new HashMap<String, Manager>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Attributes
  private String dbId;

  //Manager Associations
  private List<ArtListing> promotedListings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(User aUser, String aDbId)
  {
    super(aUser);
    if (!setDbId(aDbId))
    {
      throw new RuntimeException("Cannot create due to duplicate dbId. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    promotedListings = new ArrayList<ArtListing>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setDbId(String aDbId)
  {
    boolean wasSet = false;
    String anOldDbId = getDbId();
    if (anOldDbId != null && anOldDbId.equals(aDbId)) {
      return true;
    }
    if (hasWithDbId(aDbId)) {
      return wasSet;
    }
    dbId = aDbId;
    wasSet = true;
    if (anOldDbId != null) {
      managersByDbId.remove(anOldDbId);
    }
    managersByDbId.put(aDbId, this);
    return wasSet;
  }

  public String getDbId()
  {
    return dbId;
  }
  /* Code from template attribute_GetUnique */
  public static Manager getWithDbId(String aDbId)
  {
    return managersByDbId.get(aDbId);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithDbId(String aDbId)
  {
    return getWithDbId(aDbId) != null;
  }
  /* Code from template association_GetMany */
  public ArtListing getPromotedListing(int index)
  {
    ArtListing aPromotedListing = promotedListings.get(index);
    return aPromotedListing;
  }

  public List<ArtListing> getPromotedListings()
  {
    List<ArtListing> newPromotedListings = Collections.unmodifiableList(promotedListings);
    return newPromotedListings;
  }

  public int numberOfPromotedListings()
  {
    int number = promotedListings.size();
    return number;
  }

  public boolean hasPromotedListings()
  {
    boolean has = promotedListings.size() > 0;
    return has;
  }

  public int indexOfPromotedListing(ArtListing aPromotedListing)
  {
    int index = promotedListings.indexOf(aPromotedListing);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPromotedListings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ArtListing addPromotedListing(ArtListing.PostVisibility aVisibility, Gallery aGallery, Customer aFavoritedCustomer, Artist aArtist)
  {
    return new ArtListing(aVisibility, aGallery, this, aFavoritedCustomer, aArtist);
  }

  public boolean addPromotedListing(ArtListing aPromotedListing)
  {
    boolean wasAdded = false;
    if (promotedListings.contains(aPromotedListing)) { return false; }
    Manager existingManager = aPromotedListing.getManager();
    boolean isNewManager = existingManager != null && !this.equals(existingManager);
    if (isNewManager)
    {
      aPromotedListing.setManager(this);
    }
    else
    {
      promotedListings.add(aPromotedListing);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePromotedListing(ArtListing aPromotedListing)
  {
    boolean wasRemoved = false;
    //Unable to remove aPromotedListing, as it must always have a manager
    if (!this.equals(aPromotedListing.getManager()))
    {
      promotedListings.remove(aPromotedListing);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPromotedListingAt(ArtListing aPromotedListing, int index)
  {  
    boolean wasAdded = false;
    if(addPromotedListing(aPromotedListing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPromotedListings()) { index = numberOfPromotedListings() - 1; }
      promotedListings.remove(aPromotedListing);
      promotedListings.add(index, aPromotedListing);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePromotedListingAt(ArtListing aPromotedListing, int index)
  {
    boolean wasAdded = false;
    if(promotedListings.contains(aPromotedListing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPromotedListings()) { index = numberOfPromotedListings() - 1; }
      promotedListings.remove(aPromotedListing);
      promotedListings.add(index, aPromotedListing);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPromotedListingAt(aPromotedListing, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    managersByDbId.remove(getDbId());
    for(int i=promotedListings.size(); i > 0; i--)
    {
      ArtListing aPromotedListing = promotedListings.get(i - 1);
      aPromotedListing.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "dbId" + ":" + getDbId()+ "]";
  }
}