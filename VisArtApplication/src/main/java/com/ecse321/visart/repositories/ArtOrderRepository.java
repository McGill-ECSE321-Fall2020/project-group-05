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
import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.Artist;
import com.ecse321.visart.model.Customer;
import com.ecse321.visart.model.ArtPiece.PieceLocation;
import com.ecse321.visart.model.Ticket;

/**
 * 
 * @author anwar
 *
 */
@Repository
public class ArtOrderRepository {

  @Autowired
  EntityManager entityManager;
  TicketRepository tRepository;

  /**
   * createOrder
   * 
   * This method creates an ArtOrder instance that is persisted in the database
   * 
   * @param  aIsDelivered     status of delivered ArtPiece
   * @param  aTargetLocation  whether the ArtPiece is at gallery, or elsewhere
   * @param  aTargetAddress   address of the ArtPiece's location, if elsewhere
   * @param  aDeliveryTracker a link to external delivery tacker site
   * @param  aIdCode          database Id for this art order
   * @param  aArtPiece        ArtPiece to track by this order
   * @return                  persisted art order instance
   */
  @Transactional
  public ArtOrder createArtOrder(boolean aIsDelivered, PieceLocation aTargetLocation,
      String aTargetAddress, String aDeliveryTracker, String aIdCode, ArtPiece aArtPiece) {

    ArtOrder ao = new ArtOrder(aIsDelivered, aTargetLocation, aTargetAddress, aDeliveryTracker,
        aIdCode, aArtPiece);
    entityManager.persist(ao);
    return ao;
  }

  /**
   * getArtOrder
   * 
   * This method retrieves an order instance from the database
   * 
   * @param  aIdCode database Id for a specific order
   * @return
   */
  @Transactional
  public ArtOrder getArtOrder(String aIdCode) {
    return entityManager.find(ArtOrder.class, aIdCode);
  }

  /**
   * updateArtOrder
   * 
   * This method updates an order when there are changes
   * 
   * @param ao specific art order
   */
  @Transactional
  public void updateArtOrder(ArtOrder ao) {
    entityManager.merge(ao);
  }

  /**
   * This method removes an art order stance from the database
   * 
   * @param  ao specific art order
   * @return
   */
  @Transactional
  public boolean deleteArtOrder(ArtOrder ao) {
    ArtOrder entity = entityManager.find(ArtOrder.class, ao.getIdCode());
    if (entityManager.contains(entity)) {
      entityManager.remove(entityManager.merge(entity));
    } else {
      entityManager.remove(entity);
    }
    if (ao.getTicket() != null) {
      tRepository.deleteTicket(ao.getTicket());
    }
    return (!entityManager.contains(entity) && !entityManager.contains(ao.getTicket()));
  }
}
