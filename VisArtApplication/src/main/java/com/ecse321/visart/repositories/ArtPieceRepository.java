/** 

 */

package com.ecse321.visart.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.ArtListing;

/**
 * CRUD Repository operations for an ArtPiece.
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 *
 */
@Repository
public class ArtPieceRepository {

  @Autowired
  EntityManager entityManager;
  ArtOrderRepository aoRepository;

  /**
   * getArtPiece retrieves a persisted instance of ArtPiece, based on the primary
   * key from the database.
   * 
   * @param  idCode the primary key of the ArtPiece
   * @return        a persisted ArtPiece instance loaded from database
   */
  @Transactional
  public ArtPiece getArtPiece(String idCode) {
    return entityManager.find(ArtPiece.class, idCode);
  }

  /**
   * createArtPiece method creates an ArtPiece object, given an ArtListing to
   * attach to and basic information.
   * 
   * @param  aBasicLocation   the simple location, whether at gallery or elsewhere
   * @param  aAddressLocation the address of piece, if elsewhere
   * @param  aIdCode          the primary key of the new ArtPiece instance
   * @param  aArtListing      the persisted ArtListing instance to attach to
   * @return                  a persisted ArtPiece instance loaded into the
   *                          database
   */
  @Transactional
  public ArtPiece createArtPiece(PieceLocation aBasicLocation, String aAddressLocation,
      String aIdCode, ArtListing aArtListing) {
    ArtPiece apiece = new ArtPiece(aBasicLocation, aAddressLocation, aIdCode, aArtListing);
    entityManager.persist(apiece);
    entityManager.merge(aArtListing);
    return apiece;
  }

  /**
   * updateArtPiece method updates an ArtPiece instance's properties in the
   * database.
   * 
   * @param ap the ArtPiece whose changes will be written to the database
   */
  @Transactional
  public void updateArtPiece(ArtPiece ap) {
    entityManager.merge(ap);
  }

  /**
   * Overloaded deleteArtPiece method that removes the given ArtPiece instance
   * from the database. Also deletes its associated ArtOrder.
   * 
   * @param  ap the ArtPiece to be removed from the database
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteArtPiece(ArtPiece ap) {
    return deleteArtPiece(ap.getIdCode());
  }

  /**
   * deleteArtPiece method removes the ArtPiece data at the given primary key.
   * Also deletes its associated ArtOrder.
   * 
   * @param  id the primary key of the ArtPiece to be removed from database
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteArtPiece(String id) {
    ArtPiece ape = entityManager.find(ArtPiece.class, id);
    if (ape == null) {
      return true;
    }
    if (ape.getArtOrder() != null) {
      aoRepository.deleteArtOrder(ape.getArtOrder());
    }
    entityManager.remove(entityManager.merge(ape));
    return !entityManager.contains(ape);
  }

  /**
   * getAllKeys queries the database for all of the primary keys of the ArtPieces
   * instances.
   * 
   * @return list of primary keys for ArtPieces
   */
  @Transactional
  public List<String> getAllKeys() {
    return entityManager.createQuery("SELECT idCode FROM ArtPiece", String.class).getResultList();
  }
}
