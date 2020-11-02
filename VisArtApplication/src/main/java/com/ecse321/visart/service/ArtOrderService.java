package com.ecse321.visart.service;

import java.util.List;

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
  ArtOrderRepository artOrderRepo;

  @Autowired
  ArtPieceRepository artPieceRepo;
  
  @Autowired
  TicketService ticketService;

  @Autowired
  EntityRepository entityRepo;

  /**
   * createOrder method creates an ArtOrder instance that is persisted in the
   * database.
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
  public ArtOrder createArtOrder(Boolean aIsDelivered, PieceLocation aTargetLocation,
      String aTargetAddress, String aDeliveryTracker, String aArtPieceId) {
    String aIdCode = EntityRepository.getUniqueKey();
    if (aIdCode == null || aIdCode.length() < 1) {
      throw new IllegalArgumentException("ArtOrder id code cannot be empty!");
    }

    if (aIsDelivered == null) {
      throw new IllegalArgumentException("aIsDelivered must be set to true/false!");
    }

    if (aTargetLocation == null) {
      throw new IllegalArgumentException("Target Location cannot be empty!");
    }

    if (aTargetAddress == null || aTargetAddress.length() < 1) { // target address is also a string
      throw new IllegalArgumentException("Target Address cannot be empty!");
    }

    if (aDeliveryTracker == null || aDeliveryTracker.length() < 1) {
      throw new IllegalArgumentException("Delivery Tracker cannot be empty!");
    }

    if (aDeliveryTracker.length() > 1000) {
      throw new IllegalArgumentException("Delivery Tracker is too long!");
    }

    ArtPiece aArtPiece = artPieceRepo.getArtPiece(aArtPieceId);
    if (aArtPiece == null) {
      throw new IllegalArgumentException("Art Piece id must be valid!");
    }

    return artOrderRepo.createArtOrder(aIsDelivered, aTargetLocation, aTargetAddress,
        aDeliveryTracker, aIdCode, aArtPiece);
  }

  /**
   * getArtOrder method retrieves a persisted ArtOrder instance from the database.
   * 
   * @param  aIdCode database Id for a specific order
   * @return         the ArtOrder from the database or null if not present
   */
  @Transactional
  public ArtOrder getArtOrder(String aIdCode) {
    return artOrderRepo.getArtOrder(aIdCode);
  }

  /**
   * updateArtOrder method updates an ArtOrder instance's properties in the
   * database. If a parameter is null, then the attribute is unchanged in
   * database.
   * 
   * @param  aIdCode          the id of the artOrder to update
   * @param  aIsDelivered     can be null for unchange, true/false to change
   * @param  aTargetLocation  null to unchange, new value to change
   * @param  aTargetAddress   null or empty string to unchange, new value changes
   * @param  aDeliveryTracker null or empty string unchange, new value changes
   * @param  aArtPieceId      null or empty string to uncahnge, valid artpiece id
   *                          changes
   * 
   * @return                  the updated ArtOrder object, persisted in database
   */
  @Transactional
  public ArtOrder updateArtOrder(String aIdCode, Boolean aIsDelivered,
      PieceLocation aTargetLocation, String aTargetAddress, String aDeliveryTracker,
      String aArtPieceId) {

    if (aIdCode == null || aIdCode == "") {
      throw new IllegalArgumentException("ArtOrder id code cannot be empty!");
    }

    ArtOrder artOrder = artOrderRepo.getArtOrder(aIdCode);

    if (aIsDelivered != null) {
      artOrder.setIsDelivered(aIsDelivered);
    }

    if (aTargetLocation != null) {
      artOrder.setTargetLocation(aTargetLocation);
    }

    if (aTargetAddress != null && aTargetAddress.length() > 0) {
      artOrder.setTargetAddress(aTargetAddress);
    }

    if (aDeliveryTracker != null && aDeliveryTracker.length() > 1000) {
      throw new IllegalArgumentException("Delivery Tracker is too long!");
    }

    if (aDeliveryTracker != null && aDeliveryTracker.length() > 0) {
      artOrder.setDeliveryTracker(aDeliveryTracker);
    }

    if (aArtPieceId != null && aArtPieceId.length() > 0) {
      ArtPiece aArtPiece = artPieceRepo.getArtPiece(aArtPieceId);
      if (aArtPiece == null) {
        throw new IllegalArgumentException("Art Piece id must be valid!");
      }
      artOrder.setArtPiece(aArtPiece);
    }

    artOrderRepo.updateArtOrder(artOrder);

    return artOrder;
  }
  
  
  @Transactional
  public void addTicket(String aIdCode ,String aTicketIdCode) {
    if (aIdCode == null || aIdCode.equals("")) {
      throw new IllegalArgumentException("Cannot find artOrder");
    }
    if (aTicketIdCode == null || aTicketIdCode.equals("")) {
      throw new IllegalArgumentException("Cannot find ticket");
    }
    ArtOrder artOrder = getArtOrder(aIdCode);
    artOrder.setTicket(ticketService.getTicket(aTicketIdCode));
    artOrderRepo.updateArtOrder(artOrder);
  }

  /**
   * deleteArtOrder method deletes the ArtOrder instance from the database, given
   * its primary key.
   * 
   * @param  id the primary key of the ArtOrder to remove
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteArtOrder(String idCode) {
    return artOrderRepo.deleteArtOrder(idCode);
  }

  /**
   * getAllArtOrders retrieves all art orders from the database
   * 
   * @return list of all ArtOrders in database
   */
  @Transactional
  public List<ArtOrder> getAllArtOrders() {
    return entityRepo.getAllEntities(ArtOrder.class);
  }

}
