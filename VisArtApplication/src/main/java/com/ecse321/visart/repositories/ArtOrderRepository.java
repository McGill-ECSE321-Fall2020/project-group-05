/** 

 */

package com.ecse321.visart.repositories;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ecse321.visart.model.ArtOrder;
import com.ecse321.visart.model.ArtPiece;
import com.ecse321.visart.model.ArtPiece.PieceLocation;

/**
 * CRUD Repository operations for an ArtOrder
 * 
 * @author Nikola Milekic
 * @author Daniel Bucci
 *
 */
@Repository
public class ArtOrderRepository {

  @Autowired
  EntityManager entityManager;
  TicketRepository tRepository;

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

    ArtOrder ao = new ArtOrder(aIsDelivered, aTargetLocation, aTargetAddress, aDeliveryTracker,
        aIdCode, aArtPiece);
    entityManager.persist(ao);
    entityManager.merge(aArtPiece);
    return ao;
  }

  /**
   * getArtOrder method retrieves a persisted ArtOrder instance from the database.
   * 
   * @param  aIdCode database Id for a specific order
   * @return
   */
  @Transactional
  public ArtOrder getArtOrder(String aIdCode) {
    return entityManager.find(ArtOrder.class, aIdCode);
  }

  /**
   * updateArtOrder method updates an ArtOrder instance's properties in the
   * database.
   * 
   * @param ao the ArtOrder instance to write to the database
   */
  @Transactional
  public void updateArtOrder(ArtOrder ao) {
    entityManager.merge(ao);
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

    return deleteArtOrder(ao.getIdCode());
  }

  /**
   * deleteArtOrder method deletes the ArtOrder instance from the database, given
   * its primary key.
   * 
   * @param  id the primary key of the ArtOrder to remove
   * @return    true if successful delete
   */
  @Transactional
  public boolean deleteArtOrder(String id) {
    ArtOrder entity = entityManager.find(ArtOrder.class, id);
    if (entity == null) {
      return true;
    }
    if (entity.getArtPiece() != null) { // Remove ArtPiece association
      ArtPiece p = entityManager.find(ArtPiece.class, entity.getArtPiece().getIdCode());
      if (p != null && p.getArtOrder() != null)
        p.getArtOrder().delete(); // delete if associated
      entityManager.merge(p);
    }
    entityManager.remove(entityManager.merge(entity));
    if (entity.getTicket() != null) {
      tRepository.deleteTicket(entity.getTicket());
    }
    return (!entityManager.contains(entity) && !entityManager.contains(entity.getTicket()));
  }

  /**
   * getAllKeys queries the database for all of the primary keys of the ArtOrders
   * instances.
   * 
   * @return list of primary keys for ArtOrders
   */
  @Transactional
  public List<String> getAllKeys() {
    return entityManager.createQuery("SELECT idCode FROM ArtOrder", String.class).getResultList();
  }
}
