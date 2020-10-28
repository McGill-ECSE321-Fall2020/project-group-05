/* PLEASE DO NOT EDIT THIS CODE */
/*
 * This code was generated using the UMPLE 1.30.1.5099.60569f335 modeling
 * language!
 */

package com.ecse321.visart.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "artlistings")
// line 115 "../../../../../resources/visart.ump"
public class ArtListing {

  // ------------------------
  // ENUMERATIONS
  // ------------------------

  public enum PostVisibility {
    Public, Private, Unlisted, Draft
  }

  // ------------------------
  // MEMBER VARIABLES
  // ------------------------

  // ArtListing Attributes
  private PostVisibility visibility;
  private String description;
  private String title;

  @ElementCollection
  private List<String> postingPicLink;

  // ArtListing Associations

  // ------------------------
  // CONSTRUCTOR
  // ------------------------

  public ArtListing(PostVisibility aVisibility, String aDescription, String aTitle, String aIdCode,
      Artist aArtist) {
    visibility = aVisibility;
    dimensions = new ArrayList<Float>();
    description = aDescription;
    title = aTitle;
    postingPicLink = new ArrayList<String>();
    idCode = aIdCode;
    pieces = new ArrayList<ArtPiece>();
    tags = new ArrayList<Tag>();
    favoritedCustomer = new ArrayList<Customer>();
    boolean didAddArtist = setArtist(aArtist);
    if (!didAddArtist) {
      throw new RuntimeException(
          "Unable to create postedListing due to artist. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  // ------------------------
  // INTERFACE
  // ------------------------

  public boolean setVisibility(PostVisibility aVisibility) {
    boolean wasSet = false;
    visibility = aVisibility;
    wasSet = true;
    return wasSet;
  }

  /* Code from template attribute_SetMany */
  public boolean addDimension(Float aDimension) {
    boolean wasAdded = false;
    wasAdded = dimensions.add(aDimension);
    return wasAdded;
  }

  public boolean removeDimension(Float aDimension) {
    boolean wasRemoved = false;
    wasRemoved = dimensions.remove(aDimension);
    return wasRemoved;
  }

  public boolean setDescription(String aDescription) {
    boolean wasSet = false;
    description = aDescription;
    wasSet = true;
    return wasSet;
  }

  public boolean setTitle(String aTitle) {
    boolean wasSet = false;
    title = aTitle;
    wasSet = true;
    return wasSet;
  }

  /* Code from template attribute_SetMany */
  public boolean addPostingPicLink(String aPostingPicLink) {
    boolean wasAdded = false;
    wasAdded = postingPicLink.add(aPostingPicLink);
    return wasAdded;
  }

  public boolean removePostingPicLink(String aPostingPicLink) {
    boolean wasRemoved = false;
    wasRemoved = postingPicLink.remove(aPostingPicLink);
    return wasRemoved;
  }

  public boolean setIdCode(String aIdCode) {
    boolean wasSet = false;
    idCode = aIdCode;
    wasSet = true;
    return wasSet;
  }

  @Enumerated(EnumType.ORDINAL)
  public PostVisibility getVisibility() {
    return visibility;
  }

  /* Code from template attribute_GetMany */
  public Float getDimension(int index) {
    Float aDimension = dimensions.get(index);
    return aDimension;
  }

  public Float[] getDimensions() {
    Float[] newDimensions = dimensions.toArray(new Float[dimensions.size()]);
    return newDimensions;
  }

  public int numberOfDimensions() {
    int number = dimensions.size();
    return number;
  }

  public boolean hasDimensions() {
    boolean has = dimensions.size() > 0;
    return has;
  }

  public int indexOfDimension(Float aDimension) {
    int index = dimensions.indexOf(aDimension);
    return index;
  }

  public String getDescription() {
    return description;
  }

  public String getTitle() {
    return title;
  }

  /* Code from template attribute_GetMany */
  public String getPostingPicLink(int index) {
    String aPostingPicLink = postingPicLink.get(index);
    return aPostingPicLink;
  }

  public String[] getPostingPicLink() {
    String[] newPostingPicLink = postingPicLink.toArray(new String[postingPicLink.size()]);
    return newPostingPicLink;
  }

  public int numberOfPostingPicLink() {
    int number = postingPicLink.size();
    return number;
  }

  public boolean hasPostingPicLink() {
    boolean has = postingPicLink.size() > 0;
    return has;
  }

  public int indexOfPostingPicLink(String aPostingPicLink) {
    int index = postingPicLink.indexOf(aPostingPicLink);
    return index;
  }

  @OneToMany(mappedBy = "artListing")
  private List<ArtPiece> pieces;
  @OneToMany(mappedBy = "listing")
  private List<Tag> tags;
  @ManyToOne
  private Manager manager;
  @ManyToMany
  private List<Customer> favoritedCustomer;
  @ManyToOne
  private Artist artist;
  @ElementCollection
  private List<Float> dimensions;
  @Id
  private String idCode;

  public String getIdCode() {
    return idCode;
  }

  /* Code from template association_GetMany */
  public ArtPiece getPiece(int index) {
    ArtPiece aPiece = pieces.get(index);
    return aPiece;
  }

  public List<ArtPiece> getPieces() {
    List<ArtPiece> newPieces = Collections.unmodifiableList(pieces);
    return newPieces;
  }

  public int numberOfPieces() {
    int number = pieces.size();
    return number;
  }

  public boolean hasPieces() {
    boolean has = pieces.size() > 0;
    return has;
  }

  public int indexOfPiece(ArtPiece aPiece) {
    int index = pieces.indexOf(aPiece);
    return index;
  }

  /* Code from template association_GetMany */
  public Tag getTag(int index) {
    Tag aTag = tags.get(index);
    return aTag;
  }

  public List<Tag> getTags() {
    List<Tag> newTags = Collections.unmodifiableList(tags);
    return newTags;
  }

  public int numberOfTags() {
    int number = tags.size();
    return number;
  }

  public boolean hasTags() {
    boolean has = tags.size() > 0;
    return has;
  }

  public int indexOfTag(Tag aTag) {
    int index = tags.indexOf(aTag);
    return index;
  }

  /* Code from template association_GetOne */
  public Manager getManager() {
    return manager;
  }

  public boolean hasManager() {
    boolean has = manager != null;
    return has;
  }

  /* Code from template association_GetMany */
  public Customer getFavoritedCustomer(int index) {
    Customer aFavoritedCustomer = favoritedCustomer.get(index);
    return aFavoritedCustomer;
  }

  public List<Customer> getFavoritedCustomer() {
    List<Customer> newFavoritedCustomer = Collections.unmodifiableList(favoritedCustomer);
    return newFavoritedCustomer;
  }

  public int numberOfFavoritedCustomer() {
    int number = favoritedCustomer.size();
    return number;
  }

  public boolean hasFavoritedCustomer() {
    boolean has = favoritedCustomer.size() > 0;
    return has;
  }

  public int indexOfFavoritedCustomer(Customer aFavoritedCustomer) {
    int index = favoritedCustomer.indexOf(aFavoritedCustomer);
    return index;
  }

  /* Code from template association_GetOne */
  public Artist getArtist() {
    return artist;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPieces() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public ArtPiece addPiece(ArtPiece.PieceLocation aBasicLocation, String aAddressLocation,
      String aIdCode) {
    return new ArtPiece(aBasicLocation, aAddressLocation, aIdCode, this);
  }

  public boolean addPiece(ArtPiece aPiece) {
    boolean wasAdded = false;
    if (pieces.contains(aPiece)) {
      return false;
    }
    ArtListing existingArtListing = aPiece.getArtListing();
    boolean isNewArtListing = existingArtListing != null && !this.equals(existingArtListing);
    if (isNewArtListing) {
      aPiece.setArtListing(this);
    } else {
      pieces.add(aPiece);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePiece(ArtPiece aPiece) {
    boolean wasRemoved = false;
    // Unable to remove aPiece, as it must always have a artListing
    if (!this.equals(aPiece.getArtListing())) {
      pieces.remove(aPiece);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addPieceAt(ArtPiece aPiece, int index) {
    boolean wasAdded = false;
    if (addPiece(aPiece)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPieces()) {
        index = numberOfPieces() - 1;
      }
      pieces.remove(aPiece);
      pieces.add(index, aPiece);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePieceAt(ArtPiece aPiece, int index) {
    boolean wasAdded = false;
    if (pieces.contains(aPiece)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfPieces()) {
        index = numberOfPieces() - 1;
      }
      pieces.remove(aPiece);
      pieces.add(index, aPiece);
      wasAdded = true;
    } else {
      wasAdded = addPieceAt(aPiece, index);
    }
    return wasAdded;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfTags() {
    return 0;
  }

  /* Code from template association_AddManyToOne */
  public Tag addTag(Tag.TagType aType, String aKeyword, String aIdCode) {
    return new Tag(aType, aKeyword, aIdCode, this);
  }

  public boolean addTag(Tag aTag) {
    boolean wasAdded = false;
    if (tags.contains(aTag)) {
      return false;
    }
    ArtListing existingListing = aTag.getListing();
    boolean isNewListing = existingListing != null && !this.equals(existingListing);
    if (isNewListing) {
      aTag.setListing(this);
    } else {
      tags.add(aTag);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeTag(Tag aTag) {
    boolean wasRemoved = false;
    // Unable to remove aTag, as it must always have a listing
    if (!this.equals(aTag.getListing())) {
      tags.remove(aTag);
      wasRemoved = true;
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addTagAt(Tag aTag, int index) {
    boolean wasAdded = false;
    if (addTag(aTag)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfTags()) {
        index = numberOfTags() - 1;
      }
      tags.remove(aTag);
      tags.add(index, aTag);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveTagAt(Tag aTag, int index) {
    boolean wasAdded = false;
    if (tags.contains(aTag)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfTags()) {
        index = numberOfTags() - 1;
      }
      tags.remove(aTag);
      tags.add(index, aTag);
      wasAdded = true;
    } else {
      wasAdded = addTagAt(aTag, index);
    }
    return wasAdded;
  }

  /* Code from template association_SetOptionalOneToMany */
  public boolean setManager(Manager aManager) {
    boolean wasSet = false;
    Manager existingManager = manager;
    manager = aManager;
    if (existingManager != null && !existingManager.equals(aManager)) {
      existingManager.removePromotedListing(this);
    }
    if (aManager != null) {
      aManager.addPromotedListing(this);
    }
    wasSet = true;
    return wasSet;
  }

  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfFavoritedCustomer() {
    return 0;
  }

  /* Code from template association_AddManyToManyMethod */
  public boolean addFavoritedCustomer(Customer aFavoritedCustomer) {
    boolean wasAdded = false;
    if (favoritedCustomer.contains(aFavoritedCustomer)) {
      return false;
    }
    favoritedCustomer.add(aFavoritedCustomer);
    if (aFavoritedCustomer.indexOfFavoriteListing(this) != -1) {
      wasAdded = true;
    } else {
      wasAdded = aFavoritedCustomer.addFavoriteListing(this);
      if (!wasAdded) {
        favoritedCustomer.remove(aFavoritedCustomer);
      }
    }
    return wasAdded;
  }

  /* Code from template association_RemoveMany */
  public boolean removeFavoritedCustomer(Customer aFavoritedCustomer) {
    boolean wasRemoved = false;
    if (!favoritedCustomer.contains(aFavoritedCustomer)) {
      return wasRemoved;
    }

    int oldIndex = favoritedCustomer.indexOf(aFavoritedCustomer);
    favoritedCustomer.remove(oldIndex);
    if (aFavoritedCustomer.indexOfFavoriteListing(this) == -1) {
      wasRemoved = true;
    } else {
      wasRemoved = aFavoritedCustomer.removeFavoriteListing(this);
      if (!wasRemoved) {
        favoritedCustomer.add(oldIndex, aFavoritedCustomer);
      }
    }
    return wasRemoved;
  }

  /* Code from template association_AddIndexControlFunctions */
  public boolean addFavoritedCustomerAt(Customer aFavoritedCustomer, int index) {
    boolean wasAdded = false;
    if (addFavoritedCustomer(aFavoritedCustomer)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfFavoritedCustomer()) {
        index = numberOfFavoritedCustomer() - 1;
      }
      favoritedCustomer.remove(aFavoritedCustomer);
      favoritedCustomer.add(index, aFavoritedCustomer);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveFavoritedCustomerAt(Customer aFavoritedCustomer, int index) {
    boolean wasAdded = false;
    if (favoritedCustomer.contains(aFavoritedCustomer)) {
      if (index < 0) {
        index = 0;
      }
      if (index > numberOfFavoritedCustomer()) {
        index = numberOfFavoritedCustomer() - 1;
      }
      favoritedCustomer.remove(aFavoritedCustomer);
      favoritedCustomer.add(index, aFavoritedCustomer);
      wasAdded = true;
    } else {
      wasAdded = addFavoritedCustomerAt(aFavoritedCustomer, index);
    }
    return wasAdded;
  }

  /* Code from template association_SetOneToMany */
  public boolean setArtist(Artist aArtist) {
    boolean wasSet = false;
    if (aArtist == null) {
      return wasSet;
    }

    Artist existingArtist = artist;
    artist = aArtist;
    if (existingArtist != null && !existingArtist.equals(aArtist)) {
      existingArtist.removePostedListing(this);
    }
    artist.addPostedListing(this);
    wasSet = true;
    return wasSet;
  }

  public void delete() {
    while (pieces.size() > 0) {
      ArtPiece aPiece = pieces.get(pieces.size() - 1);
      aPiece.delete();
      pieces.remove(aPiece);
    }

    while (tags.size() > 0) {
      Tag aTag = tags.get(tags.size() - 1);
      aTag.delete();
      tags.remove(aTag);
    }

    if (manager != null) {
      Manager placeholderManager = manager;
      this.manager = null;
      placeholderManager.removePromotedListing(this);
    }
    ArrayList<Customer> copyOfFavoritedCustomer = new ArrayList<Customer>(favoritedCustomer);
    favoritedCustomer.clear();
    for (Customer aFavoritedCustomer : copyOfFavoritedCustomer) {
      aFavoritedCustomer.removeFavoriteListing(this);
    }
    Artist placeholderArtist = artist;
    this.artist = null;
    if (placeholderArtist != null) {
      placeholderArtist.removePostedListing(this);
    }
  }

  // line 150 "../../../../../resources/visart.ump"
  public ArtListing() {

  }

  public String toString() {
    return super.toString() + "[" +
        "description" + ":" + getDescription() + "," +
        "title" + ":" + getTitle() + "," +
        "idCode" + ":" + getIdCode() + "]" + System.getProperties().getProperty("line.separator") +
        "  " + "visibility" + "="
        + (getVisibility() != null
            ? !getVisibility().equals(this) ? getVisibility().toString().replaceAll("  ", "    ")
                : "this"
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "manager = "
        + (getManager() != null ? Integer.toHexString(System.identityHashCode(getManager()))
            : "null")
        + System.getProperties().getProperty("line.separator") +
        "  " + "artist = "
        + (getArtist() != null ? Integer.toHexString(System.identityHashCode(getArtist()))
            : "null");
  }
}