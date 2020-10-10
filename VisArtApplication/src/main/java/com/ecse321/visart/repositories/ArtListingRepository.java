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
 * CRUD Repository operations for an ArtListing
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 *
 */
@Repository
public class ArtListingRepository {

  @Autowired
  EntityManager entityManager;
  ArtPieceRepository apRepository;
  TagRepository tRepository;

  /**
   * createArtListing method creates an ArtListing instance that is persisted in
   * the database
   * 
   * @param  aVisibility  If the art listing is visible in the system
   * @param  aDescription short description of the art listing
   * @param  aTitle       title of the art listing
   * @param  aIdCode      database Id for this listing
   * @param  aArtist      Artist instance this belongs to
   * @return              a persisted instance of ArtListing
   */
  @Transactional
  public ArtListing createArtListing(PostVisibility aVisibility, String aDescription, String aTitle,
      String aIdCode, Artist aArtist) {
    ArtListing al = new ArtListing(aVisibility, aDescription, aTitle, aIdCode, aArtist);
    entityManager.persist(al);
    entityManager.merge(aArtist);
    return al;
  }

  /**
   * Overloaded getArtListing method retrieves an instance of ArtListing from the
   * database, based on a primary key. All collections are lazy-loaded, and
   * therefore inaccessible, unless set to true.
   * 
   * @param  aIdCode the database primary key for the ArtListing
   * @return         a persisted instance of ArtListing from the database
   */
  @Transactional
  public ArtListing getArtListing(String aIdCode) {
    return getArtListing(aIdCode, false, false, false);
  }

  /**
   * getArtListing method retrieves an instance of ArtListing from the database,
   * based on a corresponding primary key. The collections of this instance are
   * lazy loaded, and options can be set to true to fetch the corresponding
   * collections when necessary.
   * 
   * @param  aIdCode                the primary key in the database of ArtListing
   * @param  alsoArtPieces          also fetch associated ArtPieces
   * @param  alsoTags               also fetch associated Tags
   * @param  alsoFavoritedCustomers also fetch associated Customers
   * @return                        a persisted instance of ArtListing from
   *                                database
   */
  @Transactional
  public ArtListing getArtListing(String aIdCode, boolean alsoArtPieces, boolean alsoTags,
      boolean alsoFavoritedCustomers) {
    ArtListing entity = entityManager.find(ArtListing.class, aIdCode);
    if (alsoArtPieces)
      entity.getPieces().size();
    if (alsoTags)
      entity.getTags().size();
    if (alsoFavoritedCustomers)
      entity.getFavoritedCustomer();
    return entity;
  }

  /**
   * updateArtListing method updates an ArtListing instance's properties in the
   * database. Changes made specific to the attributes of this ArtListing and does
   * not cascade to collections.
   * 
   * @param al the ArtListing to write to the database
   */
  @Transactional
  public void updateArtListing(ArtListing al) {
    entityManager.merge(al);
  }

  /**
   * deleteArtListing method removes a specified ArtListing by its primary key
   * from the database. This also deletes the associated Tags and ArtPieces that
   * referenced in this ArtListing.
   * 
   * @param  id the primary key of the entry to remove
   * @return    true if successful
   */
  @Transactional
  public boolean deleteArtListing(String id) {
    ArtListing entity = entityManager.find(ArtListing.class, id);
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

  /**
   * Overloaded deleteArtListing method, that removes the specified ArtListing
   * instance from the database, by its idCode.
   * 
   * @param  al the ArtListing to remove from the database
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteArtListing(ArtListing al) {
    return deleteArtListing(al.getIdCode());
  }
}
