/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling language!*/

package com.ecse321.visart.model;
import javax.persistence.*;

@Entity
  @Table(name="tags")
// line 155 "../../../../../resources/visart.ump"
public class Tag
{

  //------------------------
  // ENUMERATIONS
  //------------------------

  public enum TagType { Topic, Category, Genre, Material, Other }

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Tag Attributes
  private TagType type;
  private String keyword;

  //Tag Associations
  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Tag(TagType aType, String aKeyword, String aIdCode, ArtListing aListing)
  {
    type = aType;
    keyword = aKeyword;
    idCode = aIdCode;
    boolean didAddListing = setListing(aListing);
    if (!didAddListing)
    {
      throw new RuntimeException("Unable to create tag due to listing. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setType(TagType aType)
  {
    boolean wasSet = false;
    type = aType;
    wasSet = true;
    return wasSet;
  }

  public boolean setKeyword(String aKeyword)
  {
    boolean wasSet = false;
    keyword = aKeyword;
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
  public TagType getType()
  {
    return type;
  }

  public String getKeyword()
  {
    return keyword;
  }

  
  @ManyToOne
  private ArtListing listing;
  @Id
  private String idCode;
   
  public String getIdCode()
  {
    return idCode;
  }
  /* Code from template association_GetOne */
  public ArtListing getListing()
  {
    return listing;
  }
  /* Code from template association_SetOneToMany */
  public boolean setListing(ArtListing aListing)
  {
    boolean wasSet = false;
    if (aListing == null)
    {
      return wasSet;
    }

    ArtListing existingListing = listing;
    listing = aListing;
    if (existingListing != null && !existingListing.equals(aListing))
    {
      existingListing.removeTag(this);
    }
    listing.addTag(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ArtListing placeholderListing = listing;
    this.listing = null;
    if(placeholderListing != null)
    {
      placeholderListing.removeTag(this);
    }
  }

  // line 173 "../../../../../resources/visart.ump"
   public  Tag(){
    
  }


  public String toString()
  {
    return super.toString() + "["+
            "keyword" + ":" + getKeyword()+ "," +
            "idCode" + ":" + getIdCode()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "type" + "=" + (getType() != null ? !getType().equals(this)  ? getType().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "listing = "+(getListing()!=null?Integer.toHexString(System.identityHashCode(getListing())):"null");
  }
}