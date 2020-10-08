/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;
import java.util.*;

@Entity
  @Table(name="artists")
// line 91 "../../../../../resources/visart.ump"
public class Artist
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------



  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Artist(String aIdCode, Customer aCustomer)
  {
    idCode = aIdCode;
    soldTickets = new ArrayList<Ticket>();
    postedListings = new ArrayList<ArtListing>();
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create artist due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  
  @OneToMany
  private List<Ticket> soldTickets;
  @OneToMany
  private List<ArtListing> postedListings;
  @OneToOne
  private Customer customer;
  @Id
  private String idCode;

  public String getIdCode()
  {
    return idCode;
  }
  /* Code from template association_GetMany */
  public Ticket getSoldTicket(int index)
  {
    Ticket aSoldTicket = soldTickets.get(index);
    return aSoldTicket;
  }

  public List<Ticket> getSoldTickets()
  {
    List<Ticket> newSoldTickets = Collections.unmodifiableList(soldTickets);
    return newSoldTickets;
  }

  public int numberOfSoldTickets()
  {
    int number = soldTickets.size();
    return number;
  }

  public boolean hasSoldTickets()
  {
    boolean has = soldTickets.size() > 0;
    return has;
  }

  public int indexOfSoldTicket(Ticket aSoldTicket)
  {
    int index = soldTickets.indexOf(aSoldTicket);
    return index;
  }
  /* Code from template association_GetMany */
  public ArtListing getPostedListing(int index)
  {
    ArtListing aPostedListing = postedListings.get(index);
    return aPostedListing;
  }

  public List<ArtListing> getPostedListings()
  {
    List<ArtListing> newPostedListings = Collections.unmodifiableList(postedListings);
    return newPostedListings;
  }

  public int numberOfPostedListings()
  {
    int number = postedListings.size();
    return number;
  }

  public boolean hasPostedListings()
  {
    boolean has = postedListings.size() > 0;
    return has;
  }

  public int indexOfPostedListing(ArtListing aPostedListing)
  {
    int index = postedListings.indexOf(aPostedListing);
    return index;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSoldTickets()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Ticket addSoldTicket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode, ArtOrder aOrder, Customer aCustomer)
  {
    return new Ticket(aIsPaymentConfirmed, aPaymentAmount, aIdCode, aOrder, aCustomer, this);
  }

  public boolean addSoldTicket(Ticket aSoldTicket)
  {
    boolean wasAdded = false;
    if (soldTickets.contains(aSoldTicket)) { return false; }
    Artist existingArtist = aSoldTicket.getArtist();
    boolean isNewArtist = existingArtist != null && !this.equals(existingArtist);
    if (isNewArtist)
    {
      aSoldTicket.setArtist(this);
    }
    else
    {
      soldTickets.add(aSoldTicket);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSoldTicket(Ticket aSoldTicket)
  {
    boolean wasRemoved = false;
    //Unable to remove aSoldTicket, as it must always have a artist
    if (!this.equals(aSoldTicket.getArtist()))
    {
      soldTickets.remove(aSoldTicket);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSoldTicketAt(Ticket aSoldTicket, int index)
  {  
    boolean wasAdded = false;
    if(addSoldTicket(aSoldTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSoldTickets()) { index = numberOfSoldTickets() - 1; }
      soldTickets.remove(aSoldTicket);
      soldTickets.add(index, aSoldTicket);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSoldTicketAt(Ticket aSoldTicket, int index)
  {
    boolean wasAdded = false;
    if(soldTickets.contains(aSoldTicket))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSoldTickets()) { index = numberOfSoldTickets() - 1; }
      soldTickets.remove(aSoldTicket);
      soldTickets.add(index, aSoldTicket);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSoldTicketAt(aSoldTicket, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPostedListings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ArtListing addPostedListing(ArtListing.PostVisibility aVisibility, String aIdCode)
  {
    return new ArtListing(aVisibility, aIdCode, this);
  }

  public boolean addPostedListing(ArtListing aPostedListing)
  {
    boolean wasAdded = false;
    if (postedListings.contains(aPostedListing)) { return false; }
    Artist existingArtist = aPostedListing.getArtist();
    boolean isNewArtist = existingArtist != null && !this.equals(existingArtist);
    if (isNewArtist)
    {
      aPostedListing.setArtist(this);
    }
    else
    {
      postedListings.add(aPostedListing);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePostedListing(ArtListing aPostedListing)
  {
    boolean wasRemoved = false;
    //Unable to remove aPostedListing, as it must always have a artist
    if (!this.equals(aPostedListing.getArtist()))
    {
      postedListings.remove(aPostedListing);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPostedListingAt(ArtListing aPostedListing, int index)
  {  
    boolean wasAdded = false;
    if(addPostedListing(aPostedListing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPostedListings()) { index = numberOfPostedListings() - 1; }
      postedListings.remove(aPostedListing);
      postedListings.add(index, aPostedListing);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePostedListingAt(ArtListing aPostedListing, int index)
  {
    boolean wasAdded = false;
    if(postedListings.contains(aPostedListing))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPostedListings()) { index = numberOfPostedListings() - 1; }
      postedListings.remove(aPostedListing);
      postedListings.add(index, aPostedListing);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPostedListingAt(aPostedListing, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setCustomer(Customer aNewCustomer)
  {
    boolean wasSet = false;
    if (aNewCustomer == null)
    {
      //Unable to setCustomer to null, as artist must always be associated to a customer
      return wasSet;
    }
    
    Artist existingArtist = aNewCustomer.getArtist();
    if (existingArtist != null && !equals(existingArtist))
    {
      //Unable to setCustomer, the current customer already has a artist, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Customer anOldCustomer = customer;
    customer = aNewCustomer;
    customer.setArtist(this);

    if (anOldCustomer != null)
    {
      anOldCustomer.setArtist(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=soldTickets.size(); i > 0; i--)
    {
      Ticket aSoldTicket = soldTickets.get(i - 1);
      aSoldTicket.delete();
    }
    for(int i=postedListings.size(); i > 0; i--)
    {
      ArtListing aPostedListing = postedListings.get(i - 1);
      aPostedListing.delete();
    }
    Customer existingCustomer = customer;
    customer = null;
    if (existingCustomer != null)
    {
      existingCustomer.setArtist(null);
    }
  }

  // line 109 "../../../../../resources/visart.ump"
   public  Artist(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "idCode" + ":" + getIdCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null");
  }
}