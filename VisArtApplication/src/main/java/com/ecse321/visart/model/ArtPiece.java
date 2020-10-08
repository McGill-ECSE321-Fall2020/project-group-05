/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;

@Entity
  @Table(name="artpieces")
// line 174 "../../../../../resources/visart.ump"
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


  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ArtPiece(PieceLocation aBasicLocation, String aAddressLocation, String aIdCode, ArtListing aArtListing)
  {
    basicLocation = aBasicLocation;
    addressLocation = aAddressLocation;
    idCode = aIdCode;
    boolean didAddArtListing = setArtListing(aArtListing);
    if (!didAddArtListing)
    {
      throw new RuntimeException("Unable to create piece due to artListing. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setIdCode(String aIdCode)
  {
    boolean wasSet = false;
    idCode = aIdCode;
    wasSet = true;
    return wasSet;
  }

  @Enumerated(EnumType.ORDINAL)
  public PieceLocation getBasicLocation()
  {
    return basicLocation;
  }

  public String getAddressLocation()
  {
    return addressLocation;
  }

  
  @ManyToOne
  private ArtListing artListing;
  @OneToOne
  private ArtOrder artOrder;
  @Id
  private String idCode;

  public String getIdCode()
  {
    return idCode;
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

  public boolean hasArtOrder()
  {
    boolean has = artOrder != null;
    return has;
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
  /* Code from template association_SetOptionalOneToOne */
  public boolean setArtOrder(ArtOrder aNewArtOrder)
  {
    boolean wasSet = false;
    if (artOrder != null && !artOrder.equals(aNewArtOrder) && equals(artOrder.getArtPiece()))
    {
      //Unable to setArtOrder, as existing artOrder would become an orphan
      return wasSet;
    }

    artOrder = aNewArtOrder;
    ArtPiece anOldArtPiece = aNewArtOrder != null ? aNewArtOrder.getArtPiece() : null;

    if (!this.equals(anOldArtPiece))
    {
      if (anOldArtPiece != null)
      {
        anOldArtPiece.artOrder = null;
      }
      if (artOrder != null)
      {
        artOrder.setArtPiece(this);
      }
    }
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
      existingArtOrder.setArtPiece(null);
    }
  }

  // line 195 "../../../../../resources/visart.ump"
   public  ArtPiece(){
    String note;
  }


  public String toString()
  {
    return super.toString() + "["+
            "addressLocation" + ":" + getAddressLocation()+ "," +
            "idCode" + ":" + getIdCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "basicLocation" + "=" + (getBasicLocation() != null ? !getBasicLocation().equals(this)  ? getBasicLocation().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "artListing = "+(getArtListing()!=null?Integer.toHexString(System.identityHashCode(getArtListing())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "artOrder = "+(getArtOrder()!=null?Integer.toHexString(System.identityHashCode(getArtOrder())):"null");
  }
}