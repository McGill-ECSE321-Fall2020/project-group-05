/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

// line 75 "../../../../../resources/visart.ump"
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
  private ArtPiece artPiece;
  private Ticket ticket;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, ArtPiece aArtPiece, Ticket aTicket)
  {
    isDelivered = aIsDelivered;
    targetLocation = aTargetLocation;
    targetAddress = aTargetAddress;
    deliveryTracker = aDeliveryTracker;
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

  public ArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, PieceLocation aBasicLocationForArtPiece, String aAddressLocationForArtPiece, ArtListing aArtListingForArtPiece, boolean aIsPaymentConfirmedForTicket, double aPaymentAmountForTicket, Customer aCustomerForTicket, Artist aArtistForTicket)
  {
    isDelivered = aIsDelivered;
    targetLocation = aTargetLocation;
    targetAddress = aTargetAddress;
    deliveryTracker = aDeliveryTracker;
    artPiece = new ArtPiece(aBasicLocationForArtPiece, aAddressLocationForArtPiece, aArtListingForArtPiece, this);
    ticket = new Ticket(aIsPaymentConfirmedForTicket, aPaymentAmountForTicket, this, aCustomerForTicket, aArtistForTicket);
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

  public boolean getIsDelivered()
  {
    return isDelivered;
  }

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
  /* Code from template attribute_IsBoolean */
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
            "deliveryTracker" + ":" + getDeliveryTracker()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "targetLocation" + "=" + (getTargetLocation() != null ? !getTargetLocation().equals(this)  ? getTargetLocation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "artPiece = "+(getArtPiece()!=null?Integer.toHexString(System.identityHashCode(getArtPiece())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ticket = "+(getTicket()!=null?Integer.toHexString(System.identityHashCode(getTicket())):"null");
  }
}