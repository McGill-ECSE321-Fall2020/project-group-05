package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.EntityRepository;


@Service
public class ArtPieceService {

  @Autowired
  ArtPieceRepository ArtPieceRepo;

  @Autowired
  EntityRepository entityRepo;

  @Transactional
  public ArtPiece createArtPiece(PieceLocation aBasicLocation, String aAddressLocation,
      String aIdCode, ArtListing aArtListing) {

    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("User id code cannot be empty!");
    }

    if (aBasicLocation == null) {
      throw new IllegalArgumentException("Basic Location cannot be empty!");
    }

    if (aAddressLocation == null || aAddressLocation == "") {
      throw new IllegalArgumentException("Address Location cannot be empty!");
    }

    if (aArtListing == null) {
      throw new IllegalArgumentException("Art Listing cannot be empty!");
    }

    return ArtPieceRepo.createArtPiece(aBasicLocation, aAddressLocation,
        aIdCode, aArtListing);
  }

  /**
   * getArtPiece retrieves a persisted instance of ArtPiece, based on the primary
   * key from the database.
   * 
   * @param  idCode the primary key of the ArtPiece
   * @return        a persisted ArtPiece instance loaded from database
   */
  @Transactional
  public ArtPiece getArtPiece(String idCode) {
    return ArtPieceRepo.getArtPiece(idCode);
  }

  /**
   * updateArtPiece method updates an ArtPiece instance's properties in the
   * database.
   * 
   * @param ap the ArtPiece whose changes will be written to the database
   */
  @Transactional
  public void updateArtPiece(ArtPiece ap) {
    ArtPieceRepo.updateArtPiece(ap);
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
    return ArtPieceRepo.deleteArtPiece(ap.getIdCode());
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
    return ArtPieceRepo.deleteArtPiece(id);
  }
  
  @Transactional
  public List<ArtPiece> getAllArtPiece() {
    return entityRepo.getAllEntities(ArtPiece.class);
  }
  

}