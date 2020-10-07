/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

@Entity
  @Table(name="tickets")
// line 199 "../../../../../resources/visart.ump"
public class Ticket
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ticket Attributes
  private boolean isPaymentConfirmed;
  private double paymentAmount;


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ticket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode, ArtOrder aOrder, Customer aCustomer, Artist aArtist)
  {
    isPaymentConfirmed = aIsPaymentConfirmed;
    paymentAmount = aPaymentAmount;
    idCode = aIdCode;
    if (aOrder == null || aOrder.getTicket() != null)
    {
      throw new RuntimeException("Unable to create Ticket due to aOrder. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    order = aOrder;
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create boughtTicket due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddArtist = setArtist(aArtist);
    if (!didAddArtist)
    {
      throw new RuntimeException("Unable to create soldTicket due to artist. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  public Ticket(boolean aIsPaymentConfirmed, double aPaymentAmount, String aIdCode, boolean aIsDeliveredForOrder, PieceLocation aTargetLocationForOrder, String aTargetAddressForOrder, String aDeliveryTrackerForOrder, String aIdCodeForOrder, ArtPiece aArtPieceForOrder, Customer aCustomer, Artist aArtist)
  {
    isPaymentConfirmed = aIsPaymentConfirmed;
    paymentAmount = aPaymentAmount;
    idCode = aIdCode;
    order = new ArtOrder(aIsDeliveredForOrder, aTargetLocationForOrder, aTargetAddressForOrder, aDeliveryTrackerForOrder, aIdCodeForOrder, aArtPieceForOrder, this);
    boolean didAddCustomer = setCustomer(aCustomer);
    if (!didAddCustomer)
    {
      throw new RuntimeException("Unable to create boughtTicket due to customer. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddArtist = setArtist(aArtist);
    if (!didAddArtist)
    {
      throw new RuntimeException("Unable to create soldTicket due to artist. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsPaymentConfirmed(boolean aIsPaymentConfirmed)
  {
    boolean wasSet = false;
    isPaymentConfirmed = aIsPaymentConfirmed;
    wasSet = true;
    return wasSet;
  }

  public boolean setPaymentAmount(double aPaymentAmount)
  {
    boolean wasSet = false;
    paymentAmount = aPaymentAmount;
    wasSet = true;
    return wasSet;
  }

  public boolean setIdCode(String aIdCode)
  {
    boolean wasSet = false;
    idCode = aIdCode;
    wasSet = true;
    return wasSet;
  }

  /**
   * Artist confirms the payment received
   */
  public boolean getIsPaymentConfirmed()
  {
    return isPaymentConfirmed;
  }

  public double getPaymentAmount()
  {
    return paymentAmount;
  }


   @OneToOne
   private ArtOrder order;
   @ManyToOne
   private Customer customer;
   @ManyToOne
   private Artist artist;
   @Id
   private String idCode;
   
  public String getIdCode()
  {
    return idCode;
  }
  /* Code from template attribute_IsBoolean */
  @Transient
  public boolean isIsPaymentConfirmed()
  {
    return isPaymentConfirmed;
  }
  /* Code from template association_GetOne */
  public ArtOrder getOrder()
  {
    return order;
  }
  /* Code from template association_GetOne */
  public Customer getCustomer()
  {
    return customer;
  }
  /* Code from template association_GetOne */
  public Artist getArtist()
  {
    return artist;
  }
  /* Code from template association_SetOneToMany */
  public boolean setCustomer(Customer aCustomer)
  {
    boolean wasSet = false;
    if (aCustomer == null)
    {
      return wasSet;
    }

    Customer existingCustomer = customer;
    customer = aCustomer;
    if (existingCustomer != null && !existingCustomer.equals(aCustomer))
    {
      existingCustomer.removeBoughtTicket(this);
    }
    customer.addBoughtTicket(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setArtist(Artist aArtist)
  {
    boolean wasSet = false;
    if (aArtist == null)
    {
      return wasSet;
    }

    Artist existingArtist = artist;
    artist = aArtist;
    if (existingArtist != null && !existingArtist.equals(aArtist))
    {
      existingArtist.removeSoldTicket(this);
    }
    artist.addSoldTicket(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArtOrder existingOrder = order;
    order = null;
    if (existingOrder != null)
    {
      existingOrder.delete();
    }
    Customer placeholderCustomer = customer;
    this.customer = null;
    if(placeholderCustomer != null)
    {
      placeholderCustomer.removeBoughtTicket(this);
    }
    Artist placeholderArtist = artist;
    this.artist = null;
    if(placeholderArtist != null)
    {
      placeholderArtist.removeSoldTicket(this);
    }
  }

  // line 218 "../../../../../resources/visart.ump"
   public  Ticket(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "isPaymentConfirmed" + ":" + getIsPaymentConfirmed()+ "," +
            "paymentAmount" + ":" + getPaymentAmount()+ "," +
            "idCode" + ":" + getIdCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "order = "+(getOrder()!=null?Integer.toHexString(System.identityHashCode(getOrder())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "customer = "+(getCustomer()!=null?Integer.toHexString(System.identityHashCode(getCustomer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "artist = "+(getArtist()!=null?Integer.toHexString(System.identityHashCode(getArtist())):"null");
  }
}