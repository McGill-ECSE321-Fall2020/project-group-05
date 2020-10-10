/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;
import java.util.*;

@Entity
  @Table(name="managers")
  @DiscriminatorValue("0")
// line 54 "../../../../../resources/visart.ump"
public class Manager extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Manager Associations

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Manager(String aIdCode, User aUser)
  {
    super(aIdCode, aUser);
    promotedListings = new ArrayList<ArtListing>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public ArtListing getPromotedListing(int index)
  {
    ArtListing aPromotedListing = promotedListings.get(index);
    return aPromotedListing;
  }

  
  @OneToMany(mappedBy="manager")
  private List<ArtListing> promotedListings;
   
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
  /* Code from template association_AddManyToOptionalOne */
  public boolean addPromotedListing(ArtListing aPromotedListing)
  {
    boolean wasAdded = false;
    if (promotedListings.contains(aPromotedListing)) { return false; }
    Manager existingManager = aPromotedListing.getManager();
    if (existingManager == null)
    {
      aPromotedListing.setManager(this);
    }
    else if (!this.equals(existingManager))
    {
      existingManager.removePromotedListing(aPromotedListing);
      addPromotedListing(aPromotedListing);
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
    if (promotedListings.contains(aPromotedListing))
    {
      promotedListings.remove(aPromotedListing);
      aPromotedListing.setManager(null);
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
    while( !promotedListings.isEmpty() )
    {
      promotedListings.get(0).setManager(null);
    }
    super.delete();
  }

  // line 64 "../../../../../resources/visart.ump"
   public  Manager(){
    
  }

}