package com.ecse321.visart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtListing;
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Manager;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtListingRepository;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.EntityRepository;

@Service
public class ArtPieceService {

  @Autowired
  ArtPieceRepository artPieceRepo;
  
  @Autowired
  ArtOrderService artOrderService;

  @Autowired
  ArtListingRepository artListingRepo;

  @Autowired
  EntityRepository entityRepo;

  @Transactional
  public ArtPiece createArtPiece(PieceLocation aBasicLocation, String aAddressLocation,
      String aArtListingId) {
    String aIdCode = EntityRepository.getUniqueKey();

    if (aIdCode == null || aIdCode.length() < 1) {
      throw new IllegalArgumentException("User id code cannot be empty!");
    }

    if (aBasicLocation == null) {
      throw new IllegalArgumentException("Piece Location cannot be empty!");
    }

    if (aAddressLocation == null || aAddressLocation.length() < 1) {
      throw new IllegalArgumentException("Address Location cannot be empty!");
    }

    ArtListing aArtListing = artListingRepo.getArtListing(aArtListingId);
    if (aArtListing == null) {
      throw new IllegalArgumentException("Art Listing id must be valid!");
    }

    return artPieceRepo.createArtPiece(aBasicLocation, aAddressLocation,
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
    return artPieceRepo.getArtPiece(idCode);
  }

  /**
   * updateArtPiece method updates an ArtPiece instance's properties in the
   * database.
   * 
   * @param ap the ArtPiece whose changes will be written to the database
   */
  @Transactional
  public ArtPiece updateArtPiece(PieceLocation aBasicLocation, String aAddressLocation,
      String aIdCode, String aArtListingId) {

    if (aIdCode == null || aIdCode.length() < 1) {
      throw new IllegalArgumentException("User id code cannot be empty!");
    }

    ArtPiece artPiece = artPieceRepo.getArtPiece(aIdCode);

    if (aBasicLocation != null) {
      artPiece.setBasicLocation(aBasicLocation);
    }

    if (aAddressLocation != null && aAddressLocation.length() > 0) {
      artPiece.setAddressLocation(aAddressLocation);
    }

    if (aArtListingId != null && aArtListingId.length() > 0) {
      ArtListing aArtListing = artListingRepo.getArtListing(aArtListingId);
      if (aArtListing == null) {
        throw new IllegalArgumentException("Art Listing id must be valid!");
      }
      artPiece.setArtListing(aArtListing);
    }

    artPieceRepo.updateArtPiece(artPiece);

    return artPiece;
  }
  
  @Transactional
  public void addArtOrder(String aIdCode ,String aArtOrderIdCode) {
    if (aIdCode == null || aIdCode.equals("")) {
      throw new IllegalArgumentException("Cannot find artPiece");
    }
    if (aArtOrderIdCode == null || aArtOrderIdCode.equals("")) {
      throw new IllegalArgumentException("Cannot find artOrder");
    }
    ArtPiece artPiece = getArtPiece(aIdCode);
    artPiece.setArtOrder(artOrderService.getArtOrder(aArtOrderIdCode));
    artPieceRepo.updateArtPiece(artPiece);
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
    return artPieceRepo.deleteArtPiece(ap.getIdCode());
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
    return artPieceRepo.deleteArtPiece(id);
  }

  @Transactional
  public List<ArtPiece> getAllArtPieces() {
    return entityRepo.getAllEntities(ArtPiece.class);
  }

}