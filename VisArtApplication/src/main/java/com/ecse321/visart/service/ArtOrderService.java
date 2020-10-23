package com.ecse321.visart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.repositories.ArtOrderRepository;
import com.ecse321.visart.repositories.ArtPieceRepository;
import com.ecse321.visart.repositories.EntityRepository;

@Service
public class ArtOrderService {

  @Autowired
  ArtOrderRepository ArtOrderRepo;

  @Autowired
  EntityRepository entityRepo;

  /**
   * createOrder method creates an ArtOrder instance that is persisted in the
   * database
   * 
   * @param  aIsDelivered     status of delivered ArtPiece
   * @param  aTargetLocation  whether the ArtPiece is at gallery, or elsewhere
   * @param  aTargetAddress   address of the ArtPiece's location, if elsewhere
   * @param  aDeliveryTracker a link to external delivery tacker site
   * @param  aIdCode          database Id for this art order
   * @param  aArtPiece        ArtPiece to track by this order
   * @return                  a persisted art order instance
   */
  @Transactional
  public ArtOrder createArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation,
      String aTargetAddress, String aDeliveryTracker, String aIdCode, ArtPiece aArtPiece) {

    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("User id code cannot be empty!");
    }

    if (aIsDelivered != true && aIsDelivered != false) {
      throw new IllegalArgumentException("Delivery status must be true or false");
    }

    if (aTargetLocation == null) {
      throw new IllegalArgumentException("Target Location cannot be empty!");
    }

    if (aTargetAddress == null) {
      throw new IllegalArgumentException("Target Address cannot be empty!");
    }

    if (aDeliveryTracker == null || aDeliveryTracker == "") {
      throw new IllegalArgumentException("Delivery Tracker cannot be empty!");
    }

    if (aDeliveryTracker.length() != 12) {
      throw new IllegalArgumentException("Delivery Tracker must contain 12 digits");
    }

    if (aArtPiece == null) {
      throw new IllegalArgumentException("Art Piece cannot be empty!");
    }

    return ArtOrderRepo.createArtOrder(aIsDelivered, aTargetLocation, aTargetAddress,
        aDeliveryTracker, aIdCode, aArtPiece);
  }

  /**
   * getArtOrder method retrieves a persisted ArtOrder instance from the database.
   * 
   * @param  aIdCode database Id for a specific order
   * @return
   */
  @Transactional
  public ArtOrder getArtOrder(String aIdCode) {
    return ArtOrderRepo.getArtOrder(aIdCode);
  }

  /**
   * updateArtOrder method updates an ArtOrder instance's properties in the
   * database.
   * 
   * @param ao the ArtOrder instance to write to the database
   */
  @Transactional
  public void updateArtOrder(ArtOrder ao) {
    ArtOrderRepo.updateArtOrder(ao);
  }

  /**
   * Overloaded deleteArtOrder method removes the specified ArtOrder instance from
   * the database.
   * 
   * @param  ao the ArtOrder instance to remove
   * @return    true if successful removal
   */
  @Transactional
  public boolean deleteArtOrder(ArtOrder ao) {

    return ArtOrderRepo.deleteArtOrder(ao.getIdCode());
  }

  /**
   * deleteArtOrder method deletes the ArtOrder instance from the database, given
   * its primary key.
   * 
   * @param  id the primary key of the ArtOrder to remove
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteArtOrder(String Id) {

    return ArtOrderRepo.deleteArtOrder(Id);
  }

}
