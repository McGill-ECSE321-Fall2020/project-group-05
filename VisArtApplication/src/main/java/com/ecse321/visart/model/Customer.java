/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import java.util.*;

// line 33 "../../../../../resources/visart.ump"
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

  public Customer(User aUser)
  {
    super(aUser);
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
  public Ticket addBoughtTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, ArtOrder aOrder, Artist aArtist)
  {
    return new Ticket(aIsPaymentConfirmed, aPaymentAmount, aOrder, this, aArtist);
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
  /* Code from template association_AddManyToOne */
  public ArtListing addFavoriteListing(ArtListing.PostVisibility aVisibility, Gallery aGallery, Manager aManager, Artist aArtist)
  {
    return new ArtListing(aVisibility, aGallery, aManager, this, aArtist);
  }

  public boolean addFavoriteListing(ArtListing aFavoriteListing)
  {
    boolean wasAdded = false;
    if (favoriteListings.contains(aFavoriteListing)) { return false; }
    Customer existingFavoritedCustomer = aFavoriteListing.getFavoritedCustomer();
    boolean isNewFavoritedCustomer = existingFavoritedCustomer != null && !this.equals(existingFavoritedCustomer);
    if (isNewFavoritedCustomer)
    {
      aFavoriteListing.setFavoritedCustomer(this);
    }
    else
    {
      favoriteListings.add(aFavoriteListing);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeFavoriteListing(ArtListing aFavoriteListing)
  {
    boolean wasRemoved = false;
    //Unable to remove aFavoriteListing, as it must always have a favoritedCustomer
    if (!this.equals(aFavoriteListing.getFavoritedCustomer()))
    {
      favoriteListings.remove(aFavoriteListing);
      wasRemoved = true;
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
    for(int i=favoriteListings.size(); i > 0; i--)
    {
      ArtListing aFavoriteListing = favoriteListings.get(i - 1);
      aFavoriteListing.delete();
    }
    super.delete();
  }

}