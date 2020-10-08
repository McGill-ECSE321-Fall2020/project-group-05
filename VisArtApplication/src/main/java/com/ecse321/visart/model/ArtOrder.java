/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

@Entity
  @Table(name="orders")
// line 225 "../../../../../resources/visart.ump"
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
 
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker, String aIdCode, ArtPiece aArtPiece)
  {
    isDelivered = aIsDelivered;
    targetLocation = aTargetLocation;
    targetAddress = aTargetAddress;
    deliveryTracker = aDeliveryTracker;
    idCode = aIdCode;
    boolean didAddArtPiece = setArtPiece(aArtPiece);
    if (!didAddArtPiece)
    {
      throw new RuntimeException("Unable to create artOrder due to artPiece. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean hasTicket()
  {
    boolean has = ticket != null;
    return has;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setArtPiece(ArtPiece aNewArtPiece)
  {
    boolean wasSet = false;
    if (aNewArtPiece == null)
    {
      //Unable to setArtPiece to null, as artOrder must always be associated to a artPiece
      return wasSet;
    }
    
    ArtOrder existingArtOrder = aNewArtPiece.getArtOrder();
    if (existingArtOrder != null && !equals(existingArtOrder))
    {
      //Unable to setArtPiece, the current artPiece already has a artOrder, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    ArtPiece anOldArtPiece = artPiece;
    artPiece = aNewArtPiece;
    artPiece.setArtOrder(this);

    if (anOldArtPiece != null)
    {
      anOldArtPiece.setArtOrder(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setTicket(Ticket aNewTicket)
  {
    boolean wasSet = false;
    if (ticket != null && !ticket.equals(aNewTicket) && equals(ticket.getOrder()))
    {
      //Unable to setTicket, as existing ticket would become an orphan
      return wasSet;
    }

    ticket = aNewTicket;
    ArtOrder anOldOrder = aNewTicket != null ? aNewTicket.getOrder() : null;

    if (!this.equals(anOldOrder))
    {
      if (anOldOrder != null)
      {
        anOldOrder.ticket = null;
      }
      if (ticket != null)
      {
        ticket.setOrder(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArtPiece existingArtPiece = artPiece;
    artPiece = null;
    if (existingArtPiece != null)
    {
      existingArtPiece.setArtOrder(null);
    }
    Ticket existingTicket = ticket;
    ticket = null;
    if (existingTicket != null)
    {
      existingTicket.delete();
    }
  }

  // line 246 "../../../../../resources/visart.ump"
   public  ArtOrder(){
    
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