package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.EntityRepository;

@Service
public class ArtListingService {

  @Autowired
  ArtListingRepository ArtListingRepo;

  @Autowired
  EntityRepository entityRepo;
  
  /**
   * 
   * This method creates an ArtListing instance that is persisted in the database.
   * 
   * @param  aVisibility       Visibility of post
   * @param  aDescription      Description of ArtListing
   * @param  aTitle            Title of ArtListing
   * @param  aIdCode           the database primary key for the ArtListing
   * @param  aArtist           Artist who is posting the ArtListing
   * @return                   persisted ArtListing instance
   */
  @Transactional
  public ArtListing createArtListing(PostVisibility aVisibility, String aDescription, String aTitle,
      String aIdCode, Artist aArtist) {

    if (aVisibility == null) {
      throw new IllegalArgumentException("Post Visibility cannot be empty!");
    }

    if (aDescription == null || aDescription == "") {
      throw new IllegalArgumentException("Description cannot be empty!");
    }

    if (aTitle == null || aTitle == "") {
      throw new IllegalArgumentException("Title cannot be empty!");
    }

    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("Id code cannot be empty!");
    }

    if (aArtist == null) {
      throw new IllegalArgumentException("Artist cannot be empty!");
    }


    return ArtListingRepo.createArtListing(aVisibility, aDescription, aTitle, aIdCode, aArtist);
  }

  /**
   * updateArtListing method updates the given ArtListing instance's properties in the
   * database.
   * 
   * @param ArtListing the ArtListing whose properties will be updated in the database
   */
  @Transactional
  public void updateArtListing(ArtListing aArtListing) {
    ArtListingRepo.updateArtListing(aArtListing);
  }
  
  
  /**
   * getArtListing method retrieves a persisted ArtListing instance from the database.
   * 
   * @param  aIdCode database Id for a specific order
   * @return
   */
  @Transactional
  public ArtListing getArtListing(String aIdCode) {
    return ArtListingRepo.getArtListing(aIdCode);
  }
  
  /**
   * Overloaded deleteArtListing method removes the specified ArtListing instance from
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
   * deleteArtListing method deletes the ArtListing instance from the database, given
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
