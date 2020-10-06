/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

@Entity
  @Table(name="orders")
// line 210 "../../../../../resources/visart.ump"
public class ArtOrder
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ArtOrder Attributes
  private boolean isDelivered;
  private PieceLocation targetLocation;
  private String targetAddress;
  private String deliveryTracker;

  //ArtOrder Associations

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, String aIdCode, ArtPiece aArtPiece, Ticket aTicket)
  {
    isDelivered = aIsDelivered;
    targetLocation = aTargetLocation;
    targetAddress = aTargetAddress;
    deliveryTracker = aDeliveryTracker;
    idCode = aIdCode;
    if (aArtPiece == null || aArtPiece.getArtOrder() != null)
    {
      throw new RuntimeException("Unable to create ArtOrder due to aArtPiece. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artPiece = aArtPiece;
    if (aTicket == null || aTicket.getOrder() != null)
    {
      throw new RuntimeException("Unable to create ArtOrder due to aTicket. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    ticket = aTicket;
  }

  public ArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, String aIdCode, PieceLocation aBasicLocationForArtPiece, String aAddressLocationForArtPiece, String aIdCodeForArtPiece, ArtListing aArtListingForArtPiece, boolean aIsPaymentConfirmedForTicket, double aPaymentAmountForTicket, String aIdCodeForTicket, Customer aCustomerForTicket, Artist aArtistForTicket)
  {
    isDelivered = aIsDelivered;
    targetLocation = aTargetLocation;
    targetAddress = aTargetAddress;
    deliveryTracker = aDeliveryTracker;
    idCode = aIdCode;
    artPiece = new ArtPiece(aBasicLocationForArtPiece, aAddressLocationForArtPiece, aIdCodeForArtPiece, aArtListingForArtPiece, this);
    ticket = new Ticket(aIsPaymentConfirmedForTicket, aPaymentAmountForTicket, aIdCodeForTicket, this, aCustomerForTicket, aArtistForTicket);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setIsDelivered(boolean aIsDelivered)
  {
    boolean wasSet = false;
    isDelivered = aIsDelivered;
    wasSet = true;
    return wasSet;
  }

  public boolean setTargetLocation(PieceLocation aTargetLocation)
  {
    boolean wasSet = false;
    targetLocation = aTargetLocation;
    wasSet = true;
    return wasSet;
  }

  public boolean setTargetAddress(String aTargetAddress)
  {
    boolean wasSet = false;
    targetAddress = aTargetAddress;
    wasSet = true;
    return wasSet;
  }

  public boolean setDeliveryTracker(String aDeliveryTracker)
  {
    boolean wasSet = false;
    deliveryTracker = aDeliveryTracker;
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

  public boolean getIsDelivered()
  {
    return isDelivered;
  }

  @Enumerated(EnumType.ORDINAL)
  public PieceLocation getTargetLocation()
  {
    return targetLocation;
  }

  public String getTargetAddress()
  {
    return targetAddress;
  }

  public String getDeliveryTracker()
  {
    return deliveryTracker;
  }

  
   @OneToOne
   private ArtPiece artPiece;
   @OneToOne
   private Ticket ticket;
   @Id
   private String idCode;
  public String getIdCode()
  {
    return idCode;
  }
  /* Code from template attribute_IsBoolean */
  @Transient
  public boolean isIsDelivered()
  {
    return isDelivered;
  }
  /* Code from template association_GetOne */
  public ArtPiece getArtPiece()
  {
    return artPiece;
  }
  /* Code from template association_GetOne */
  public Ticket getTicket()
  {
    return ticket;
  }

  public void delete()
  {
    ArtPiece existingArtPiece = artPiece;
    artPiece = null;
    if (existingArtPiece != null)
    {
      existingArtPiece.delete();
    }
    Ticket existingTicket = ticket;
    ticket = null;
    if (existingTicket != null)
    {
      existingTicket.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "isDelivered" + ":" + getIsDelivered()+ "," +
            "targetAddress" + ":" + getTargetAddress()+ "," +
            "deliveryTracker" + ":" + getDeliveryTracker()+ "," +
            "idCode" + ":" + getIdCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "targetLocation" + "=" + (getTargetLocation() != null ? !getTargetLocation().equals(this)  ? getTargetLocation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "artPiece = "+(getArtPiece()!=null?Integer.toHexString(System.identityHashCode(getArtPiece())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null");
  }
}