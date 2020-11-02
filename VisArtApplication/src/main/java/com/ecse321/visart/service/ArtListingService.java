package com.ecse321.visart.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtistRepository;
import com.ecse321.visart.repositories.EntityRepository;

@Service
public class ArtListingService {

  @Autowired
  ArtListingRepository ArtListingRepo;

  @Autowired
  ArtistRepository artistRepo;

  @Autowired
  EntityRepository entityRepo;

  @Autowired
  ArtPieceService apService;

  @Autowired
  TagService tService;

  /**
   * 
   * This method creates an ArtListing instance that is persisted in the database.
   * 
   * @param  aVisibility  Visibility of post
   * @param  aDescription Description of ArtListing
   * @param  aTitle       Title of ArtListing
   * @param  aArtist      Artist who is posting the ArtListing
   * @return              persisted ArtListing instance
   */
  @Transactional
  public ArtListing createArtListing(String aVisibilityString, String aDescription, String aTitle,
      String aArtistId) {
    String aIdCode = EntityRepository.getUniqueKey();

    PostVisibility aVisibility = PostVisibility.fromString(aVisibilityString);
    if (aVisibility == null) {
      throw new IllegalArgumentException("Post Visibility cannot be empty!");
    }

    if (aDescription == null || aDescription == "") {
      throw new IllegalArgumentException("Description cannot be empty!");
    }
    if (aDescription.length() > 1000) {
      throw new IllegalArgumentException("Description must be no longer than 1000 characters!");
    }

    if (aTitle == null || aTitle == "") {
      throw new IllegalArgumentException("Title cannot be empty!");
    }

    if (aTitle.length() > 1000) {
      throw new IllegalArgumentException("Title must be no longer than 1000 characters!");
    }

    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("Id code cannot be empty!");
    }

    Artist aArtist = artistRepo.getArtist(aArtistId);
    if (aArtist == null) {
      throw new IllegalArgumentException("Artist cannot be empty!");
    }

    return ArtListingRepo.createArtListing(aVisibility, aDescription, aTitle, aIdCode, aArtist);
  }

  /**
   * updateArtListing method updates the given ArtListing instance's properties in
   * the
   * database.
   * 
   * @param aIdCode      Id of post in db
   * @param aVisibility  Visibility of post
   * @param aDescription Description of ArtListing
   * @param aTitle       Title of ArtListing
   * 
   */
  @Transactional
  public ArtListing updateArtListing(String aIdCode, String aVisibilityString, String aDescription,
      String aTitle) {
    ArtListing al = ArtListingRepo.getArtListing(aIdCode);
    if (al == null) {
      throw new IllegalArgumentException("ArtListing cannot be resolved with given id!");
    }

    PostVisibility aVisibility = PostVisibility.fromString(aVisibilityString);
    if (aVisibility != null) { // TODO: check string values, and length
      al.setVisibility(aVisibility);
    }
    if (aDescription != null) {
      if (aDescription.length() > 1000) {
        throw new IllegalArgumentException("Description must be no longer than 1000 characters!");
      }
      al.setDescription(aDescription);
    }
    if (aTitle != null) {
      if (aTitle.length() > 1000) {
        throw new IllegalArgumentException("Title must be no longer than 1000 characters!");
      }
      al.setTitle(aTitle);
    }
    ArtListingRepo.updateArtListing(al);
    return al;
  }

  @Transactional
  public ArtListing updateDimensions(String aIdCode, Float[] aDimensions) {
    ArtListing al = ArtListingRepo.getArtListing(aIdCode);
    if (al == null) {
      throw new IllegalArgumentException("ArtListing cannot be resolved with given id!");
    }
    for (float d : al.getDimensions()) {
      al.removeDimension(d);
    }
    for (float d : aDimensions) {
      al.addDimension(d);
    }

    ArtListingRepo.updateArtListing(al);
    return al;
  }

  @Transactional
  public ArtListing updatePostImages(String aIdCode, String[] aPostImages) {
    ArtListing al = ArtListingRepo.getArtListing(aIdCode);
    if (al == null) {
      throw new IllegalArgumentException("ArtListing cannot be resolved with given id!");
    }
    for (String s : al.getPostingPicLink()) {
      al.removePostingPicLink(s);
    }
    for (String s : aPostImages) {
      al.addPostingPicLink(s);
    }

    ArtListingRepo.updateArtListing(al);
    return al;
  }

  @Transactional
  public ArtListing addArtPiece(String aIdCode, String apCode) {
    ArtListing al = ArtListingRepo.getArtListing(aIdCode);
    if (al == null) {
      throw new IllegalArgumentException("ArtListing cannot be resolved with given id!");
    }
    ArtPiece ap = apService.getArtPiece(apCode);
    if (ap == null) {
      throw new IllegalArgumentException("ArtPiece cannot be resolved");
    }
    al.addPiece(ap);
    return al;
  }

  @Transactional
  public ArtListing removeArtPiece(String aIdCode, String apCode) {
    ArtListing al = ArtListingRepo.getArtListing(aIdCode);
    if (al == null) {
      throw new IllegalArgumentException("ArtListing cannot be resolved with given id!");
    }
    ArtPiece ap = apService.getArtPiece(apCode);
    if (ap == null) {
      throw new IllegalArgumentException("ArtPiece cannot be resolved");
    }
    if (!al.getPieces().contains(ap)) {
      throw new IllegalArgumentException("ArtPiece is not stored in the ArtListing");
    }
    al.removePiece(ap);
    return al;
  }

  @Transactional
  public List<Customer> getFavoritedCustomers(String aIdCode) {
    ArtListing al = ArtListingRepo.getArtListing(aIdCode);
    if (al == null) {
      throw new IllegalArgumentException("ArtListing cannot be resolved with given id!");
    }
    return al.getFavoritedCustomer();
  }

  @Transactional
  public ArtListing addTag(String aIdCode, String tCode) {
    ArtListing al = ArtListingRepo.getArtListing(aIdCode);
    if (al == null) {
      throw new IllegalArgumentException("ArtListing cannot be resolved with given id!");
    }
    Tag t = tService.getTag(tCode);
    if (t == null) {
      throw new IllegalArgumentException("Tag cannot be resolved with given id!");
    }
    al.addTag(t);
    return al;
  }

  @Transactional
  public ArtListing removeTag(String aIdCode, String tCode) {
    ArtListing al = ArtListingRepo.getArtListing(aIdCode);
    if (al == null) {
      throw new IllegalArgumentException("ArtListing cannot be resolved with given id!");
    }
    Tag t = tService.getTag(tCode);
    if (t == null) {
      throw new IllegalArgumentException("Tag cannot be resolved with given id!");
    }
    if (!al.getTags().contains(t)) {
      throw new IllegalArgumentException("Tag is not contained in this Art Listing");
    }
    al.removeTag(t);
    return al;
  }

  @Transactional
  public List<ArtListing> getUnsoldArtworks() {
    List<ArtListing> allListings = entityRepo.getAllEntities(ArtListing.class);
    List<ArtListing> unsoldListings = new ArrayList<ArtListing>();
    for (ArtListing al : allListings) {
      if (al.hasPieces()) {
        unsoldListings.add(al);
      }
    }
    return unsoldListings;
  }

  @Transactional
  public List<String> filterArtworkByTag(String[] keywords) {
    List<Tag> listTagByKeywords = entityRepo.findEntityByAttribute("type", Tag.class, keywords);
    return new ArrayList<String>(listTagByKeywords.stream()
        .map((tag) -> tag.getListing().getIdCode()).collect(Collectors.toSet()));
  }

  @Transactional
  public List<ArtListing> filterArtworkByTagAsListings(String[] keywords) {
    return entityRepo.findEntityByAttribute("idCode", ArtListing.class,
        filterArtworkByTag(keywords));
  }

  /**
   * getArtListing method retrieves a persisted ArtListing instance from the
   * database.
   * 
   * @param  aIdCode database Id for a specific order
   * @return
   */
  @Transactional
  public ArtListing getArtListing(String aIdCode) {
    return ArtListingRepo.getArtListing(aIdCode);
  }

  /**
   * Overloaded deleteArtListing method removes the specified ArtListing instance
   * from
   * the database.
   * 
   * @param  al the ArtListing instance to remove
   * @return    true if successful removal
   */
  @Transactional
  public boolean deleteArtListing(ArtListing al) {

    return ArtListingRepo.deleteArtListing(al.getIdCode());
  }

  /**
   * deleteArtListing method deletes the ArtListing instance from the database,
   * given
   * its primary key.
   * 
   * @param  id the primary key of the ArtListing to remove
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteArtListing(String Id) {

    return ArtListingRepo.deleteArtListing(Id);
  }

  @Transactional
  public List<ArtListing> getAllArtListings() {
    return entityRepo.getAllEntities(ArtListing.class);
  }

}
