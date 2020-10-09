/** 
 *@author Nikola Milekic 
 *@author Daniel Bucci
 */

package com.ecse321.visart.repositories;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtListing.PostVisibility;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Tag;
import com.ecse321.visart.model.ArtPiece;

/**
 * Repository class for an Art Listing
 * 
 * @author anwar
 *
 */
@Repository
public class ArtListingRepository {

  @Autowired
  EntityManager entityManager;
  ArtPieceRepository apRepository;
  TagRepository tRepository;

  /**
   * createArtListing
   * 
   * This method creates an ArtListing instance that is persisted in the database
   * 
   * @param  aVisibility  If the art listing is visible in the system
   * @param  aDescription short description of the art listing
   * @param  aTitle       Title of the art listing
   * @param  aIdCode      database Id for this listing
   * @param  aArtist      Artist information
   * @return              a persisted instance of ArtListing
   */
  @Transactional
  public ArtListing createArtListing(PostVisibility aVisibility, String aDescription, String aTitle,
      String aIdCode, Artist aArtist) {

    ArtListing al = new ArtListing(aVisibility, aDescription, aTitle, aIdCode, aArtist);
    entityManager.persist(al);
    return al;
  }

  /**
   * getArtListing
   * 
   * This method retrieves an instance of ArtListing from the database
   * 
   * @param  aIdCode database ID for this art listing
   * @return         an instance specific art listing
   */
  @Transactional
  public ArtListing getArtListing(String aIdCode) {
    return entityManager.find(ArtListing.class, aIdCode);
  }

  /**
   * updateArtListing
   * 
   * This method updates an art listing if there's any changes to make
   * 
   * @param al specific art listing
   */
  @Transactional
  public void updateArtListing(ArtListing al) {
    entityManager.merge(al);
  }

  /**
   * deleteArtListing
   * 
   * This method removes a specific art listing instance from the database
   * 
   * @param  al specific art listing
   * @return
   */
  @Transactional
  public boolean deleteArtListing(ArtListing al) {
    ArtListing entity = entityManager.find(ArtListing.class, al.getIdCode());
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }
    for (Tag t : entity.getTags()) {
      tRepository.deleteTag(t);
    }
    for (ArtPiece t : entity.getPieces()) {
      apRepository.deleteArtPiece(t);
    }
    return !entityManager.contains(entity);
  }
}
