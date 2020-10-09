/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;
import java.util.*;

@Entity
  @Table(name="customers")
  @DiscriminatorValue("1")
// line 70 "../../../../../resources/visart.ump"
public class Customer extends UserRole
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Customer Associations
  private Artist artist;
  private List<Ticket> boughtTickets;
  private List<ArtListing> favoriteListings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Customer(String aIdCode, User aUser)
  {
    super(aIdCode, aUser);
    boughtTickets = new ArrayList<Ticket>();
    favoriteListings = new ArrayList<ArtListing>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Artist getArtist()
  {
    return artist;
  }

  public boolean hasArtist()
  {
    boolean has = artist != null;
    return has;
  }
  /* Code from template association_GetMany */
  public Ticket getBoughtTicket(int index)
  {
    Ticket aBoughtTicket = boughtTickets.get(index);
    return aBoughtTicket;
  }

  public List<Ticket> getBoughtTickets()
  {
    List<Ticket> newBoughtTickets = Collections.unmodifiableList(boughtTickets);
    return newBoughtTickets;
  }

  public int numberOfBoughtTickets()
  {
    int number = boughtTickets.size();
    return number;
  }

  public boolean hasBoughtTickets()
  {
    boolean has = boughtTickets.size() > 0;
    return has;
  }

  public int indexOfBoughtTicket(Ticket aBoughtTicket)
  {
    int index = boughtTickets.indexOf(aBoughtTicket);
    return index;
  }
  /* Code from template association_GetMany */
  public ArtListing getFavoriteListing(int index)
  {
    ArtListing aFavoriteListing = favoriteListings.get(index);
    return aFavoriteListing;
  }

  public List<ArtListing> getFavoriteListings()
  {
    List<ArtListing> newFavoriteListings = Collections.unmodifiableList(favoriteListings);
    return newFavoriteListings;
  }

  public int numberOfFavoriteListings()
  {
    int number = favoriteListings.size();
    return number;
  }

  public boolean hasFavoriteListings()
  {
    boolean has = favoriteListings.size() > 0;
    return has;
  }

  public int indexOfFavoriteListing(ArtListing aFavoriteListing)
  {
    int index = favoriteListings.indexOf(aFavoriteListing);
    return index;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setArtist(Artist aNewArtist)
  {
    boolean wasSet = false;
    if (artist != null && !artist.equals(aNewArtist) && equals(artist.getCustomer()))
    {
      //Unable to setArtist, as existing artist would become an orphan
      return wasSet;
    }

    artist = aNewArtist;
    Customer anOldCustomer = aNewArtist != null ? aNewArtist.getCustomer() : null;

    if (!this.equals(anOldCustomer))
    {
      if (anOldCustomer != null)
      {
        anOldCustomer.artist = null;
      }
      if (artist != null)
      {
        artist.setCustomer(this);
      }
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBoughtTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Ticket addBoughtTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode, ArtOrder aOrder, Artist aArtist)
  {
    return new Ticket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aOrder, this, aArtist);
  }

  public boolean addBoughtTicket(Ticket aBoughtTicket)
  {
    boolean wasAdded = false;
    if (boughtTickets.contains(aBoughtTicket)) { return false; }
    Customer existingCustomer = aBoughtTicket.getCustomer();
    boolean isNewCustomer = existingCustomer != null && !this.equals(existingCustomer);
    if (isNewCustomer)
    {
      aBoughtTicket.setCustomer(this);
    }
    else
    {
      boughtTickets.add(aBoughtTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBoughtTicket(Ticket aBoughtTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aBoughtTicket, as it must always have a customer
    if (!this.equals(aBoughtTicket.getCustomer()))
    {
      boughtTickets.remove(aBoughtTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBoughtTicketAt(Ticket aBoughtTicket, int index)
  {  
    boolean wasAdded = false;
    if(addBoughtTicket(aBoughtTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoughtTickets()) { index = numberOfBoughtTickets() - 1; }
      boughtTickets.remove(aBoughtTicket);
      boughtTickets.add(index, aBoughtTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBoughtTicketAt(Ticket aBoughtTicket, int index)
  {
    boolean wasAdded = false;
    if(boughtTickets.contains(aBoughtTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBoughtTickets()) { index = numberOfBoughtTickets() - 1; }
      boughtTickets.remove(aBoughtTicket);
      boughtTickets.add(index, aBoughtTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBoughtTicketAt(aBoughtTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfFavoriteListings()
  {
    return 0;
  }
  /* Code from template association_AddManyToManyMethod */
  public boolean addFavoriteListing(ArtListing aFavoriteListing)
  {
    boolean wasAdded = false;
    if (favoriteListings.contains(aFavoriteListing)) { return false; }
    favoriteListings.add(aFavoriteListing);
    if (aFavoriteListing.indexOfFavoritedCustomer(this) != -1)
    {
      wasAdded = true;
    }
    else
    {
      wasAdded = aFavoriteListing.addFavoritedCustomer(this);
      if (!wasAdded)
      {
        favoriteListings.remove(aFavoriteListing);
      }
    }
    return wasAdded;
  }
  /* Code from template association_RemoveMany */
  public boolean removeFavoriteListing(ArtListing aFavoriteListing)
  {
    boolean wasRemoved = false;
    if (!favoriteListings.contains(aFavoriteListing))
    {
      return wasRemoved;
    }

    int oldIndex = favoriteListings.indexOf(aFavoriteListing);
    favoriteListings.remove(oldIndex);
    if (aFavoriteListing.indexOfFavoritedCustomer(this) == -1)
    {
      wasRemoved = true;
    }
    else
    {
      wasRemoved = aFavoriteListing.removeFavoritedCustomer(this);
      if (!wasRemoved)
      {
        favoriteListings.add(oldIndex,aFavoriteListing);
      }
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addFavoriteListingAt(ArtListing aFavoriteListing, int index)
  {  
    boolean wasAdded = false;
    if(addFavoriteListing(aFavoriteListing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFavoriteListings()) { index = numberOfFavoriteListings() - 1; }
      favoriteListings.remove(aFavoriteListing);
      favoriteListings.add(index, aFavoriteListing);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveFavoriteListingAt(ArtListing aFavoriteListing, int index)
  {
    boolean wasAdded = false;
    if(favoriteListings.contains(aFavoriteListing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfFavoriteListings()) { index = numberOfFavoriteListings() - 1; }
      favoriteListings.remove(aFavoriteListing);
      favoriteListings.add(index, aFavoriteListing);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addFavoriteListingAt(aFavoriteListing, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    Artist existingArtist = artist;
    artist = null;
    if (existingArtist != null)
    {
      existingArtist.delete();
      existingArtist.setCustomer(null);
    }
    for(int i=boughtTickets.size(); i > 0; i--)
    {
      Ticket aBoughtTicket = boughtTickets.get(i - 1);
      aBoughtTicket.delete();
    }
    ArrayList<ArtListing> copyOfFavoriteListings = new ArrayList<ArtListing>(favoriteListings);
    favoriteListings.clear();
    for(ArtListing aFavoriteListing : copyOfFavoriteListings)
    {
      aFavoriteListing.removeFavoritedCustomer(this);
    }
    super.delete();
  }

  // line 87 "../../../../../resources/visart.ump"
   public  Customer(){
    
  }

}