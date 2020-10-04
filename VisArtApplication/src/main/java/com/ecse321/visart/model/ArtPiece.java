/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;

// line 60 "../../../../../resources/visart.ump"
public class ArtPiece
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum PieceLocation { AtGallery, Offsite }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ArtPiece Attributes
  private PieceLocation basicLocation;
  private String addressLocation;

  //ArtPiece Associations
  private ArtListing artListing;
  private ArtOrder artOrder;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ArtPiece(PieceLocation aBasicLocation, String aAddressLocation, ArtListing aArtListing, ArtOrder aArtOrder)
  {
    basicLocation = aBasicLocation;
    addressLocation = aAddressLocation;
    boolean didAddArtListing = setArtListing(aArtListing);
    if (!didAddArtListing)
    {
      throw new RuntimeException("Unable to create piece due to artListing. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    if (aArtOrder == null || aArtOrder.getArtPiece() != null)
    {
      throw new RuntimeException("Unable to create ArtPiece due to aArtOrder. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artOrder = aArtOrder;
  }

  public ArtPiece(PieceLocation aBasicLocation, String aAddressLocation, ArtListing aArtListing, boolean aIsDeliveredForArtOrder, PieceLocation aTargetLocationForArtOrder, String aTargetAddressForArtOrder, String aDeliveryTrackerForArtOrder, Ticket aTicketForArtOrder)
  {
    basicLocation = aBasicLocation;
    addressLocation = aAddressLocation;
    boolean didAddArtListing = setArtListing(aArtListing);
    if (!didAddArtListing)
    {
      throw new RuntimeException("Unable to create piece due to artListing. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    artOrder = new ArtOrder(aIsDeliveredForArtOrder, aTargetLocationForArtOrder, aTargetAddressForArtOrder, aDeliveryTrackerForArtOrder, this, aTicketForArtOrder);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setBasicLocation(PieceLocation aBasicLocation)
  {
    boolean wasSet = false;
    basicLocation = aBasicLocation;
    wasSet = true;
    return wasSet;
  }

  public boolean setAddressLocation(String aAddressLocation)
  {
    boolean wasSet = false;
    addressLocation = aAddressLocation;
    wasSet = true;
    return wasSet;
  }

  public PieceLocation getBasicLocation()
  {
    return basicLocation;
  }

  public String getAddressLocation()
  {
    return addressLocation;
  }
  /* Code from template association_GetOne */
  public ArtListing getArtListing()
  {
    return artListing;
  }
  /* Code from template association_GetOne */
  public ArtOrder getArtOrder()
  {
    return artOrder;
  }
  /* Code from template association_SetOneToMany */
  public boolean setArtListing(ArtListing aArtListing)
  {
    boolean wasSet = false;
    if (aArtListing == null)
    {
      return wasSet;
    }

    ArtListing existingArtListing = artListing;
    artListing = aArtListing;
    if (existingArtListing != null && !existingArtListing.equals(aArtListing))
    {
      existingArtListing.removePiece(this);
    }
    artListing.addPiece(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArtListing placeholderArtListing = artListing;
    this.artListing = null;
    if(placeholderArtListing != null)
    {
      placeholderArtListing.removePiece(this);
    }
    ArtOrder existingArtOrder = artOrder;
    artOrder = null;
    if (existingArtOrder != null)
    {
      existingArtOrder.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "addressLocation" + ":" + getAddressLocation()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "basicLocation" + "=" + (getBasicLocation() != null ? !getBasicLocation().equals(this)  ? getBasicLocation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "artListing = "+(getArtListing()!=null?Integer.toHexString(System.identityHashCode(getArtListing())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "artOrder = "+(getArtOrder()!=null?Integer.toHexString(System.identityHashCode(getArtOrder())):"null");
  }
}